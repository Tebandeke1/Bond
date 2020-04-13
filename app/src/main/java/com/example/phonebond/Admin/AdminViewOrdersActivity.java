package com.example.phonebond.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.phonebond.Model.AdminOrders;
import com.example.phonebond.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminViewOrdersActivity extends AppCompatActivity {

    private RecyclerView orderListone;
    RecyclerView.LayoutManager layoutManager;

    private DatabaseReference odersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_orders);

        odersRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        orderListone = findViewById(R.id.orders_list);
        orderListone.setHasFixedSize(true);
        orderListone.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options = new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(odersRef,AdminOrders.class).build();

        FirebaseRecyclerAdapter<AdminOrders,AdminViewOrders> adapter = new FirebaseRecyclerAdapter<AdminOrders, AdminViewOrders>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminViewOrders adminViewOrders, final int i, @NonNull final AdminOrders adminOrders) {

                adminViewOrders.textorderPhone.setText("Phone: "+adminOrders.getNumber());
                adminViewOrders.textorderTotalAmount.setText("Total Amount: "+adminOrders.getTotalAmount());
                adminViewOrders.textorderDate.setText("Ordered at: "+adminOrders.getDate()+" "+adminOrders.getTime());

                adminViewOrders.orderbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //getting phone number from the database
                        String UID = getRef(i).getKey();

                        Intent intent = new Intent(AdminViewOrdersActivity.this, AdminUserProductActivity.class);
                        intent.putExtra("uid",UID);
                        startActivity(intent);
                    }
                });

                adminViewOrders.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{

                                "Delete",
                                "Cancel"
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminViewOrdersActivity.this);
                        builder.setTitle("Delete after contacting the client");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(i == 0){

                                    String UID = getRef(i).getKey();

                                    DeleteOrder(UID);

                                }else
                                {
                                    Intent intent = new Intent(AdminViewOrdersActivity.this,AdminViewOrdersActivity.class);
                                    startActivity(intent);
                                    finish();

                                }

                            }
                        });
                        builder.show();
                    }
                });

            }

            @NonNull
            @Override
            public AdminViewOrders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_view_layout,parent,false);

                return new AdminViewOrders(view);
            }
        };

        orderListone.setAdapter(adapter);
        adapter.startListening();
    }

    private void DeleteOrder(String uid) {

        odersRef.child(uid).removeValue();
    }

    public static class AdminViewOrders extends RecyclerView.ViewHolder{

        public TextView textorderPhone,textorderDate,textorderTotalAmount,shippingState;

        public Button orderbtn;

        public AdminViewOrders(@NonNull View itemView) {
            super(itemView);

            textorderPhone = itemView.findViewById(R.id.order_phone_number);
            textorderDate = itemView.findViewById(R.id.order_date);
            textorderTotalAmount = itemView.findViewById(R.id.order_Total_price);

            orderbtn = itemView.findViewById(R.id.show_all_order_prods);


        }
    }
}
