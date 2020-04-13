package com.example.phonebond.Buyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.phonebond.Bond.BondCatgoryActivity;
import com.example.phonebond.Bond.SellerAddNewProductActivity;
import com.example.phonebond.R;

public class OneMoreActivity extends AppCompatActivity {

    private ImageView smallCars,offload,boxbody,transportLory;
    private ImageView taxVan,luxury,ambulance,minibus;
    private ImageView compatible,buscoach,pickup,truckVectors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_more_category);

        smallCars = findViewById(R.id.small_cars_cat);
        offload = findViewById(R.id.offroad4x4_cat);
        boxbody = findViewById(R.id.box_body_cat);
        transportLory = findViewById(R.id.transport_lory_cat);

        taxVan = findViewById(R.id.taxi_vans_cat);
        luxury = findViewById(R.id.luxury_cat);
        ambulance = findViewById(R.id.ambulance_cat);
        minibus = findViewById(R.id.mini_bus_cat);

        compatible = findViewById(R.id.compatible_cat);
        buscoach = findViewById(R.id.bus_coach_cat);
        pickup = findViewById(R.id.pick_up_cat);
        truckVectors = findViewById(R.id.truck_vectors_cat);

        OnClickMetods();
    }

    private void OnClickMetods() {

        smallCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(OneMoreActivity.this, DisplayCategoryCarActivity.class);
                intent.putExtra("category","smallcars");
                startActivity(intent);

            }
        });

        offload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(OneMoreActivity.this, DisplayCategoryCarActivity.class);
                intent.putExtra("category","OffRoad 4x4");
                startActivity(intent);

            }
        });

        boxbody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(OneMoreActivity.this, DisplayCategoryCarActivity.class);
                intent.putExtra("category","Box Body");
                startActivity(intent);

            }
        });

        transportLory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(OneMoreActivity.this, DisplayCategoryCarActivity.class);
                intent.putExtra("category","Transport Lory");
                startActivity(intent);

            }
        });

        taxVan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(OneMoreActivity.this, DisplayCategoryCarActivity.class);
                intent.putExtra("category","Tax Van");
                startActivity(intent);

            }
        });

        luxury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(OneMoreActivity.this, DisplayCategoryCarActivity.class);
                intent.putExtra("category","Luxury Car");
                startActivity(intent);

            }
        });

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent  = new Intent(OneMoreActivity.this, DisplayCategoryCarActivity.class);
                intent.putExtra("category","Ambulance");
                startActivity(intent);

            }
        });

        minibus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(OneMoreActivity.this, DisplayCategoryCarActivity.class);
                intent.putExtra("category","Mini Bus");
                startActivity(intent);

            }
        });

        compatible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(OneMoreActivity.this, DisplayCategoryCarActivity.class);
                intent.putExtra("category","Compatible");
                startActivity(intent);

            }
        });

        buscoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(OneMoreActivity.this, DisplayCategoryCarActivity.class);
                intent.putExtra("category","Bus Coach");
                startActivity(intent);

            }
        });

        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(OneMoreActivity.this, DisplayCategoryCarActivity.class);
                intent.putExtra("category","Pick Up");
                startActivity(intent);

            }
        });

        truckVectors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(OneMoreActivity.this, DisplayCategoryCarActivity.class);
                intent.putExtra("category","Truck vectors");
                startActivity(intent);

            }
        });

    }
}
