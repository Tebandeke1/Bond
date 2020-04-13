package com.example.phonebond.Bond;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.phonebond.R;

public class BondCatgoryActivity extends AppCompatActivity {

    private ImageView offroad4x4 ,smallcars;
    private ImageView box_body;
    private ImageView transport_lory;
    private ImageView tax_van,luxurycar,mini_bus,ambulance;
    private ImageView compatible,bus_coach,pickUp,truck_vectors;

    private String Admin ="one";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bond_catgory);

        smallcars = findViewById(R.id.small_cars);
        offroad4x4 = findViewById(R.id.offroad4x4);
        box_body = findViewById(R.id.box_body);
        transport_lory = findViewById(R.id.transport_lory);
        tax_van = findViewById(R.id.taxi_vans);
        luxurycar = findViewById(R.id.luxury);
        mini_bus = findViewById(R.id.mini_bus);
        ambulance = findViewById(R.id.ambulance);
        compatible = findViewById(R.id.compatible);
        bus_coach = findViewById(R.id.bus_coach);
        pickUp = findViewById(R.id.pick_up);
        truck_vectors = findViewById(R.id.truck_vectors);

        smallcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(BondCatgoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","smallcars");
                startActivity(intent);

            }
        });

        offroad4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(BondCatgoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","OffRoad 4x4");
                startActivity(intent);

            }
        });
        box_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(BondCatgoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","Box Body");
                startActivity(intent);

            }
        });
        transport_lory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(BondCatgoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","Transport Lory");
                startActivity(intent);

            }
        });
        tax_van.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(BondCatgoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","Tax Van");
                startActivity(intent);

            }
        });
        luxurycar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(BondCatgoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","Luxury Car");
                startActivity(intent);

            }
        });

        mini_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(BondCatgoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","Mini Bus");
                startActivity(intent);

            }
        });
        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(BondCatgoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","Ambulance");
                startActivity(intent);

            }
        });
        compatible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(BondCatgoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","Compatible");
                startActivity(intent);

            }
        });
        bus_coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(BondCatgoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","Bus Coach");
                startActivity(intent);

            }
        });
        pickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(BondCatgoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","Pick Up");
                startActivity(intent);

            }
        });

        truck_vectors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(BondCatgoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","Truck vectors");
                startActivity(intent);

            }
        });


    }
}
