package com.example.phonebond.Bond;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phonebond.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SellerLoginActivity extends AppCompatActivity {

    private EditText sellerLoginEmail,sellerPassword;

    private Button SellerbtnSign;
    private ProgressDialog loadibg_bar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        sellerLoginEmail = findViewById(R.id.email_login);
        sellerPassword = findViewById(R.id.seller_Password_login);
        loadibg_bar =  new ProgressDialog(this);
        SellerbtnSign  = findViewById(R.id.seller_login_button);
        mAuth = FirebaseAuth.getInstance();

        SellerbtnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sellerLoginMethod();

            }
        });
    }

    private void sellerLoginMethod() {

        String email = sellerLoginEmail.getText().toString();
        String password = sellerPassword.getText().toString();

        if(!email.equals("") || password.equals("")){

            loadibg_bar.setTitle("Bond Representative signing in....");
            loadibg_bar.setCancelable(false);
            loadibg_bar.setMessage("Wait while we verify Credentials!!!");
            loadibg_bar.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(SellerLoginActivity.this, "Welcome Bond Representative..", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SellerLoginActivity.this,SellerHomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }else{
                        loadibg_bar.dismiss();
                        Toast.makeText(SellerLoginActivity.this, "Could not sign you in..", Toast.LENGTH_SHORT).show();
                    }

                }
            });



        }else{
            Toast.makeText(this, "Make sure no field is Empty..", Toast.LENGTH_SHORT).show();
        }
    }
}
