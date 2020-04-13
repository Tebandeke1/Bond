package com.example.phonebond.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phonebond.Model.products;
import com.example.phonebond.PackageViewHolder.ViewHolder;
import com.example.phonebond.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DisplayCategoryCarActivity extends AppCompatActivity {

    private DatabaseReference productsRef;

    private RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;

    private String CatGory = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_category_car);


        Intent intent = getIntent();
        CatGory = intent.getStringExtra("category");


        productsRef = FirebaseDatabase.getInstance().getReference().child("Product");


        recyclerView = findViewById(R.id.recycle_menu);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<products> options = new FirebaseRecyclerOptions.Builder<products>()
                .setQuery(productsRef.orderByChild("category").equalTo(CatGory), products.class).build();


        FirebaseRecyclerAdapter<products, ViewHolder> adapter = new FirebaseRecyclerAdapter<products, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i, @NonNull final products products) {

                viewHolder.proPrice.setText("Price = "+products.getPrice()+"$");
                viewHolder.proDesc.setText(products.getDescription());
                viewHolder.proName.setText(products.getName());
                viewHolder.proColour.setText(products.getColour());
                viewHolder.proBond.setText(products.getBond());
                Picasso.get().load(products.getImage()).into(viewHolder.proImage);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(DisplayCategoryCarActivity.this, ProductDetailsActivity.class);
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

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
