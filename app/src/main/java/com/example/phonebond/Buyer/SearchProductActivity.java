package com.example.phonebond.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.phonebond.Model.products;
import com.example.phonebond.PackageViewHolder.ViewHolder;
import com.example.phonebond.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SearchProductActivity extends AppCompatActivity {

    private Button searchbtn;

    private EditText searchtext;

    private RecyclerView searchList;

    private String searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        searchbtn = findViewById(R.id.search_btn);
        searchtext = findViewById(R.id.search_product);

        searchList = findViewById(R.id.search_list);

        searchList.setLayoutManager(new LinearLayoutManager(this));

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchInput = searchtext.getText().toString();
                onStart();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Product");

        FirebaseRecyclerOptions<products> options = new FirebaseRecyclerOptions.Builder<products>()
                .setQuery(reference.orderByChild("name").startAt(searchInput),products.class)
                .build();

        FirebaseRecyclerAdapter<products,ViewHolder> adapter =new FirebaseRecyclerAdapter<products, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull final products products) {

                viewHolder.proPrice.setText("Price = "+products.getPrice()+"$");
                viewHolder.proDesc.setText(products.getDescription());
                viewHolder.proName.setText(products.getName());
                viewHolder.proColour.setText(products.getColour());
                viewHolder.proBond.setText(products.getBond());
                Picasso.get().load(products.getImage()).into(viewHolder.proImage);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SearchProductActivity.this, ProductDetailsActivity.class);

                        intent.putExtra("pid", products.getPid());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout,parent,false);
                ViewHolder viewHolder = new ViewHolder(view);
                return viewHolder;
            }
        };

        searchList.setAdapter(adapter);
        adapter.startListening();

    }
}
