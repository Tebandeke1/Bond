package com.example.phonebond.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonebond.Bond.SellerHomeActivity;
import com.example.phonebond.Bond.SellerLoginActivity;
import com.example.phonebond.Bond.SellerSignActivity;
import com.example.phonebond.Model.Users;
import com.example.phonebond.OneTime.OneTime;
import com.example.phonebond.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogin , buttonJoinnow;

    private ProgressDialog loadibg_bar;

    public String phonedata = "Users";

    private TextView wants;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);


        buttonJoinnow = (Button)findViewById(R.id.main_join_now_button);

        loadibg_bar =  new ProgressDialog(this);

        buttonLogin = findViewById(R.id.main_login_button);

        wants = findViewById(R.id.Text_wants);

        wants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SellerSignActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        buttonJoinnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        String phoneStorage = Paper.book().read(OneTime.phoneSotrage);
        String phonePassword = Paper.book().read(OneTime.passwordStorage);


            if(phoneStorage != "" && phonePassword != ""){

                if(!TextUtils.isEmpty(phoneStorage)&& !TextUtils.isEmpty(phonePassword)){
                    AllowAccess(phoneStorage,phoneStorage);
                    loadibg_bar.setTitle("Already Logged In....");
                    loadibg_bar.setCancelable(false);
                    loadibg_bar.setMessage("Wait please...!!!");
                    loadibg_bar.show();


                }
            }
        }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser !=null){
            Intent intent = new Intent(MainActivity.this, SellerHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void AllowAccess(final String number, final String password) {


        final DatabaseReference rootref;
        rootref = FirebaseDatabase.getInstance().getReference();

        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(phonedata).child(number).exists()){

                    Users users = dataSnapshot.child(phonedata).child(number).getValue(Users.class);

                    if(users.getPhone().equals(number))
                    {

                        if(users.getPassword().equals(password)){

                            Toast.makeText(MainActivity.this, "Logged in Already..", Toast.LENGTH_SHORT).show();
                            loadibg_bar.dismiss();

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            OneTime.OnlineUser = users;
                            startActivity(intent);
                        } else {
                            loadibg_bar.dismiss();
                            Toast.makeText(MainActivity.this, "Password is Incorrect", Toast.LENGTH_SHORT).show();

                        }
                    }


                }else{
                    Toast.makeText(MainActivity.this, "Account with "+ number +" does not exist!!", Toast.LENGTH_SHORT).show();
                    loadibg_bar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    }
