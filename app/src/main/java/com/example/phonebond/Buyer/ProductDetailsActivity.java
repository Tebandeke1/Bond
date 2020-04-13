package com.example.phonebond.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.phonebond.Model.products;
import com.example.phonebond.OneTime.OneTime;
import com.example.phonebond.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private ElegantNumberButton numberButton;
    private TextView proNameDetails,proColourDetails,proDescripDetails,proPriceDetails,proBondDetails;
    private ImageView imageView;
    private Button addButton;

    private String ProductPid = "" ,state = "Normal";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        ProductPid  = getIntent().getStringExtra("pid");
        addButton = (Button)findViewById(R.id.add_to_chart_btn);
        numberButton = (ElegantNumberButton)findViewById(R.id.elegant_number);
        imageView = (ImageView)findViewById(R.id.car_image_details);
        proNameDetails = (TextView)findViewById(R.id.product_name_details);
        proColourDetails = (TextView)findViewById(R.id.product_colour_details);
        proDescripDetails = (TextView)findViewById(R.id.product_description_details);
        proBondDetails = (TextView) findViewById(R.id.Bond_Name_address_details);
        proPriceDetails = (TextView)findViewById(R.id.product_price_details);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(state.equals("Ready") || state.equals("not")){

                    Toast.makeText(ProductDetailsActivity.this, "You will be able to place another order after picking your car(s) from the store..", Toast.LENGTH_LONG).show();

                }else {

                    addToChartList();
                }
            }
        });

        getProducts(ProductPid);

    }

    @Override
    protected void onStart() {
        super.onStart();
        send();
    }

    //method adding items to chat list
    private void addToChartList() {

        String saveCurrentDate , saveCurentTime;

        Calendar calForchart = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM,dd,yyyy");

        saveCurrentDate = currentDate.format(calForchart.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");

        saveCurentTime = currentTime.format(calForchart.getTime());


        final DatabaseReference databaseOne = FirebaseDatabase.getInstance().getReference().child("chart list");

        final HashMap<String,Object> chartHashmap = new HashMap<>();

        chartHashmap.put("pid",ProductPid);
        chartHashmap.put("proName",proNameDetails.getText().toString());
        chartHashmap.put("proPrice",proPriceDetails.getText().toString());
        chartHashmap.put("proDesc",proDescripDetails.getText().toString());
        chartHashmap.put("proColour",proColourDetails.getText().toString());
        chartHashmap.put("bondName",proBondDetails.getText().toString());
        chartHashmap.put("descount","");
        chartHashmap.put("quantity",numberButton.getNumber());
        chartHashmap.put("date",saveCurrentDate);
        chartHashmap.put("time",saveCurentTime);

         databaseOne.child("User View").child(OneTime.OnlineUser.getPhone()).child("Product").child(ProductPid)
                .updateChildren(chartHashmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            databaseOne.child("Admin View").child(OneTime.OnlineUser.getPhone()).child("Product").child(ProductPid)
                                    .updateChildren(chartHashmap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){

                                                Toast.makeText(ProductDetailsActivity.this, "Product added to chart.", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                                finish();

                                            }else {
                                                Toast.makeText(ProductDetailsActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });


                        }

                    }
                });

        
    }

    private void getProducts(String productPid) {

        DatabaseReference fireBase = FirebaseDatabase.getInstance().getReference().child("Product");

        fireBase.child(ProductPid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    products products =  dataSnapshot.getValue(com.example.phonebond.Model.products.class);

                    proNameDetails.setText(products.getName());
                    proColourDetails.setText(products.getColour());
                    proDescripDetails.setText(products.getDescription());
                    proPriceDetails.setText(products.getPrice());
                    proBondDetails.setText(products.getBond());

                    Picasso.get().load(products.getImage()).into(imageView);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void send(){
        DatabaseReference baseref;

        baseref = FirebaseDatabase.getInstance().getReference().child("Orders").child(OneTime.OnlineUser.getPhone());

        baseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String ShippingState = dataSnapshot.child("state").getValue().toString();

                    if(ShippingState.equals("not yet ready")){

                        state = "not";


                    }else if(ShippingState.equals("ready")) {

                        state = "Ready";
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
