package com.example.phonebond.Buyer;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button register;

    private EditText inputName,inputPhone,inputPassword;

    private ProgressDialog loadibg_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.register_button);
        inputName = findViewById(R.id.name_register);
        inputPhone = findViewById(R.id.number_register);
        inputPassword = findViewById(R.id.register_password);

        loadibg_bar =  new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAcount();
            }
        });
    }

    private void createAcount() {

        String Name = inputName.getText().toString().trim();
        String Number = inputPhone.getText().toString().trim();
        String Password = inputPassword.getText().toString().trim();

        if(TextUtils.isEmpty(Name)){
            Toast.makeText(this, "Please Provide Your name....", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Number)){
            Toast.makeText(this, "Please Provide  Phone Number....", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(Password)){
            Toast.makeText(this, "Please Provide Your Password...", Toast.LENGTH_SHORT).show();
        }else{

            loadibg_bar.setTitle("Creating account....");
            loadibg_bar.setCancelable(false);
            loadibg_bar.setMessage("Wait while we verify Credentials!!!");
            loadibg_bar.show();
            VeifyNumber(Name,Number,Password);
        }


    }

    private void VeifyNumber(final String name, final String number, final String password) {

        final DatabaseReference rootref;
        rootref = FirebaseDatabase.getInstance().getReference();

        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(number).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone",number);
                    userdataMap.put("password",password);
                    userdataMap.put("name",name);
                    rootref.child("Users").child(number).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Congraculations You have Successfully registered.... ", Toast.LENGTH_SHORT).show();
                                        loadibg_bar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, loginActivity.class);
                                        startActivity(intent);
                                    }else{
                                        loadibg_bar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Network Error: Please try again!!!", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }else{
                    Toast.makeText(RegisterActivity.this, "This "+number+" Already Exists....", Toast.LENGTH_SHORT).show();

                    loadibg_bar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Sign Up Using another Number...", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
