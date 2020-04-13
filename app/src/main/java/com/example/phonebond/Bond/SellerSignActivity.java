package com.example.phonebond.Bond;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phonebond.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerSignActivity extends AppCompatActivity {

    private EditText sellerBond, sellerPhone,sellerAddress,sellerEmail,sellerPassword;

    private Button sellerRegister,AlreadyRegistered;

    private FirebaseAuth mAuth;

    private ProgressDialog loadibg_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_sign);

        sellerAddress = findViewById(R.id.Seller_Address);
        sellerBond = findViewById(R.id.Seller_bond_name);
        sellerEmail = findViewById(R.id.Seller_Email);
        sellerPhone = findViewById(R.id.Seller_phone);
        loadibg_bar =  new ProgressDialog(this);


        mAuth = FirebaseAuth.getInstance();

        sellerPassword = findViewById(R.id.Seller_password);

        sellerRegister = findViewById(R.id.seller_reg_btn);

        AlreadyRegistered = findViewById(R.id.seller_already_btn);

        AlreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerSignActivity.this,SellerLoginActivity.class);
                startActivity(intent);
            }
        });

        sellerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callRegister();
            }
        });

    }

    private void callRegister() {

        final String bond = sellerBond.getText().toString();
        final String email = sellerEmail.getText().toString();
        final String phone = sellerPhone.getText().toString();
        final String address = sellerAddress.getText().toString();
        final String password = sellerPassword.getText().toString();

        if(!bond.equals("") && !email.equals("") && !phone.equals("") && !address.equals("") && !password.equals("")){

            loadibg_bar.setTitle("Bond Representative signing up....");
            loadibg_bar.setCancelable(false);
            loadibg_bar.setMessage("Wait while we verify Credentials!!!");
            loadibg_bar.show();

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

                        String UsId = mAuth.getCurrentUser().getUid();

                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("Sid",UsId);
                        hashMap.put("bond",bond);
                        hashMap.put("email",email);
                        hashMap.put("phone",phone);
                        hashMap.put("Address",address);
                        hashMap.put("password",password);

                        rootRef.child("Sellers").child(UsId).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                loadibg_bar.dismiss();
                                Toast.makeText(SellerSignActivity.this, "This Bond has been Registered on Home bond...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SellerSignActivity.this,SellerHomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                        });

                    }else {
                        Toast.makeText(SellerSignActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();


                    }
                }
            });

        }else{

            Toast.makeText(this, "Make sure all fields are not empty...", Toast.LENGTH_SHORT).show();

        }

    }
}
