package com.example.phonebond.Bond;

import android.content.Intent;
import android.os.Bundle;

import com.example.phonebond.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class SellerHomeActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Toast.makeText(SellerHomeActivity.this, " This Home button has been clicked", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.navigation_add:

                        Toast.makeText(SellerHomeActivity.this, " This Add button has been clicked", Toast.LENGTH_SHORT).show();
                        Intent intente = new Intent(SellerHomeActivity.this, BondCatgoryActivity.class);
                        startActivity(intente);

                        return true;


                case R.id.navigation_log_Out:
                    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    Intent intent = new Intent(SellerHomeActivity.this,SellerLoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
