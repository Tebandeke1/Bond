package com.example.phonebond.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.phonebond.Buyer.MainActivity;
import com.example.phonebond.R;

public class LastAdminActivity extends AppCompatActivity {

    private Button logOut ,viewOrders,editProduct,ApproveProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_admin);

        editProduct = (Button)findViewById(R.id.Edit_products);
        logOut = (Button)findViewById(R.id.Admin_logout_btn);
        viewOrders = (Button)findViewById(R.id.Check_orders);

        ApproveProducts = findViewById(R.id.Approve_Cars);

        ApproveProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LastAdminActivity.this, ApproveNewCarsActivity.class);
                startActivity(intent);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LastAdminActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

                Toast.makeText(LastAdminActivity.this, "Logged out Successfully..", Toast.LENGTH_SHORT).show();

            }
        });

        viewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LastAdminActivity.this, AdminViewOrdersActivity.class);
                startActivity(intent);

            }
        });

        editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LastAdminActivity.this, AdminHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
