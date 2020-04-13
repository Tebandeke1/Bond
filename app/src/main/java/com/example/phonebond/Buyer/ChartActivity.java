package com.example.phonebond.Buyer;

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
import android.widget.Toast;

import com.example.phonebond.Model.chart;
import com.example.phonebond.OneTime.OneTime;
import com.example.phonebond.PackageViewHolder.chartViewHolder;
import com.example.phonebond.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ChartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private Button NextButton;

    private TextView priceText ,textmsg;

    private int overallTotalPrice = 0;

    private String pho;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Intent intent = getIntent();
        pho = intent.getStringExtra("phone1");


        recyclerView = findViewById(R.id.Recycle_next);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NextButton = findViewById(R.id.Next_btn);

        priceText = findViewById(R.id.chart_price_view);

        textmsg = findViewById(R.id.msg1);

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addToChart();
            }
        });

        String tabu = getString(R.string.price);



        priceText.setText(MessageFormat.format("{0}{1}", tabu, overallTotalPrice));
    }

    private void addToChart() {

            String saveCurrentDate , saveCurentTime;

            Calendar calForchart = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("MMM,dd,yyyy");

            saveCurrentDate = currentDate.format(calForchart.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");

            saveCurentTime = currentTime.format(calForchart.getTime());



            final DatabaseReference databaseOne = FirebaseDatabase.getInstance().getReference()
                    .child("Orders")
                    .child(OneTime.OnlineUser.getPhone());

            final HashMap<String,Object> chartHashmap = new HashMap<>();

            chartHashmap.put("totalAmount",String.valueOf(overallTotalPrice));
            chartHashmap.put("state","not yet ready");
            chartHashmap.put("date",saveCurrentDate);
            chartHashmap.put("time",saveCurentTime);
            chartHashmap.put("number",pho);

            databaseOne.updateChildren(chartHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){
                        FirebaseDatabase.getInstance().getReference()
                                .child("chart list")
                                .child("User View")
                                .child(OneTime.OnlineUser.getPhone())
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(ChartActivity.this, "Your car will be ready in a day..", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ChartActivity.this, HomeActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                    }

                }
            });

    }

    @Override
    protected void onStart() {
        super.onStart();

        send();

        final DatabaseReference chartReference = FirebaseDatabase.getInstance().getReference().child("chart list");


        FirebaseRecyclerOptions<chart> options = new FirebaseRecyclerOptions.Builder<chart>()
                .setQuery(chartReference.child("User View").child(OneTime.OnlineUser.getPhone())
                .child("Product"),chart.class).build();

        FirebaseRecyclerAdapter<chart, chartViewHolder> adapter = new FirebaseRecyclerAdapter<chart, chartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull chartViewHolder chartViewHolder, int i, @NonNull final chart chart) {

                chartViewHolder.productName.setText(chart.getProName());
                chartViewHolder.productQuantity.setText("Quantity = "+chart.getQuantity());
                chartViewHolder.productPrice.setText("Price = "+chart.getProPrice()+"$");

                int oneTypeProductTPrice = ((Integer.valueOf(chart.getProPrice()))) * Integer.valueOf(chart.getQuantity());

                overallTotalPrice = overallTotalPrice + oneTypeProductTPrice;

                chartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{

                                "Edit Product",
                                "Delete Product"

                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(ChartActivity.this);
                        builder.setTitle("Item Options");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (i == 0) {
                                    Intent intent = new Intent(ChartActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("pid",chart.getPid());
                                    startActivity(intent);
                                    finish();
                                }

                                if(i == 1){

                                    chartReference.child("User View")
                                            .child(OneTime.OnlineUser.getPhone())
                                            .child("Product")
                                            .child(chart.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful()){

                                                        Toast.makeText(ChartActivity.this, "Product deleted successfully", Toast.LENGTH_SHORT).show();

                                                    }

                                                }
                                            });
                                }

                            }
                        });
                        builder.show();

                    }
                });

            }

            @NonNull
            @Override
            public chartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chart_layout,parent,false);
                chartViewHolder holder = new chartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
//method for sending orders
    private void send(){
        DatabaseReference baseref;

        baseref = FirebaseDatabase.getInstance().getReference().child("Orders").child(OneTime.OnlineUser.getPhone());

        baseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String ShippingState = dataSnapshot.child("state").getValue().toString();

                    if(ShippingState.equals("not yet ready")){

                        priceText.setText("Car(s) pick state = not ready");

                        recyclerView.setVisibility(View.GONE);
                        NextButton.setVisibility(View.GONE);

                        textmsg.setVisibility(View.VISIBLE);
                        textmsg.setText("Your car is not yet ready. wait for a call to pick your car from the store.");

                        Toast.makeText(ChartActivity.this, "You will be able to place Another order after receiving this one...", Toast.LENGTH_SHORT).show();

                    }else if(ShippingState.equals("ready")) {


                        priceText.setText("Car(s) pick state = Ready");

                        recyclerView.setVisibility(View.GONE);
                        NextButton.setVisibility(View.GONE);

                        textmsg.setVisibility(View.VISIBLE);
                        textmsg.setText("Your car(s) are Ready.Come at our office and pick your car from the store.");
                        Toast.makeText(ChartActivity.this, "You will be able to place Another order after receiving this one...", Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
