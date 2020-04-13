package com.example.phonebond.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.phonebond.Interface.ItemClickListener;
import com.example.phonebond.Model.products;
import com.example.phonebond.PackageViewHolder.ViewHolder;
import com.example.phonebond.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ApproveNewCarsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;

    private DatabaseReference appRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_new_cars);

        appRef = FirebaseDatabase.getInstance().getReference().child("Product");

        recyclerView = findViewById(R.id.Approve_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<products> options = new FirebaseRecyclerOptions.Builder<products>()
                .setQuery(appRef.orderByChild("productstate").equalTo("Not Approved"), products.class).build();


        FirebaseRecyclerAdapter<products, ViewHolder> adapter = new FirebaseRecyclerAdapter<products, ViewHolder>(options) {
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
                        final String productId = products.getPid();

                        CharSequence options[] = new CharSequence[]
                                {

                                        "Yes",
                                        "No"
                                };

                        AlertDialog.Builder alert = new AlertDialog.Builder(ApproveNewCarsActivity.this);

                        alert.setTitle("Would You like to Approve this Product??");
                        alert.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(i == 0){
                                    changeProductState(productId);
                                }

                                if(i == 1)
                                {

                                }

                            }
                        });
                        alert.show();
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

    private void changeProductState(String productId) {

        appRef.child(productId).child("productstate").setValue("Approved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ApproveNewCarsActivity.this, "Product Approved, Now its Available for Sell..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
