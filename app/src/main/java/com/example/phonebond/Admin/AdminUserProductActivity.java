package com.example.phonebond.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phonebond.Model.chart;
import com.example.phonebond.PackageViewHolder.chartViewHolder;
import com.example.phonebond.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminUserProductActivity extends AppCompatActivity {

    private RecyclerView productRecy;

    RecyclerView.LayoutManager layoutManager;

    private DatabaseReference proRef;

    private String userId ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_product);

        userId = getIntent().getStringExtra("uid");
        productRecy = findViewById(R.id.orders_name_list);
        productRecy.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productRecy.setLayoutManager(layoutManager);

        proRef = FirebaseDatabase.getInstance().getReference().child("chart list").child("Admin View").child(userId).child("Product");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<chart> options = new FirebaseRecyclerOptions.Builder<chart>()
                .setQuery(proRef,chart.class).build();

        FirebaseRecyclerAdapter<chart, chartViewHolder> adapter = new FirebaseRecyclerAdapter<chart, chartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull chartViewHolder chartViewHolder, int i, @NonNull chart chart) {

                chartViewHolder.productName.setText(chart.getProName());
                chartViewHolder.productQuantity.setText("Quantity = "+chart.getQuantity());
                chartViewHolder.productPrice.setText("Price = "+chart.getProPrice()+"$");


            }

            @NonNull
            @Override
            public chartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chart_layout,parent,false);
                chartViewHolder holder = new chartViewHolder(view);
                return holder;
            }
        };

        productRecy.setAdapter(adapter);
        adapter.startListening();
    }
}
