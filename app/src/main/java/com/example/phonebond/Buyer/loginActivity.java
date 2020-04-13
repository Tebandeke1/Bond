package com.example.phonebond.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonebond.Admin.LastAdminActivity;
import com.example.phonebond.Bond.BondCatgoryActivity;
import com.example.phonebond.Model.Users;
import com.example.phonebond.OneTime.OneTime;
import com.example.phonebond.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class loginActivity extends AppCompatActivity {

    private TextView admin_text, Not_admin, forgot_password;

    private Button login;

    private EditText inputPhone,inputPassword;

    private ProgressDialog loadibg_bar;

    public String phonedata = "Users";

    private CheckBox check_remember;

    private String Number1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login_button);
        inputPhone = findViewById(R.id.Phone_number_login);
        inputPassword = findViewById(R.id.Password_login);

        loadibg_bar =  new ProgressDialog(this);

        check_remember = findViewById(R.id.check_remember);
        Paper.init(this);


        admin_text = findViewById(R.id.admin_panel_link);
        Not_admin = findViewById(R.id.not_admin_panel_link);
        forgot_password = findViewById(R.id.forgot_password_link);

        Not_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login.setText("Login");
                admin_text.setVisibility(View.VISIBLE);
                Not_admin.setVisibility(View.INVISIBLE);
                phonedata = "Users";

            }
        });

        admin_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login.setText("Login Admin");
                admin_text.setVisibility(View.INVISIBLE);
                Not_admin.setVisibility(View.VISIBLE);
                phonedata = "Admin";

            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(loginActivity.this, SetSecurityActivity.class);
                intent.putExtra("check","login");
                startActivity(intent);

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loging();
            }
        });
    }


    private void loging() {

        Number1 = inputPhone.getText().toString().trim();
        String Password1 = inputPassword.getText().toString().trim();

        if(TextUtils.isEmpty(Number1)){
            Toast.makeText(this, "Please Provide  Phone Number....", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(Password1)){
            Toast.makeText(this, "Please Provide Your Password...", Toast.LENGTH_SHORT).show();

        }
        else
        {
            loadibg_bar.setTitle("Signing in....");
            loadibg_bar.setCancelable(false);
            loadibg_bar.setMessage("Wait while we verify Credentials!!!");
            loadibg_bar.show();

            AllowAccess(Number1,Password1);

            inputPassword.setText("");
            inputPhone.setText("");
        }

    }

    private void AllowAccess(final String number, final String password) {

        //this is to keep me logged in after signing in

        if(check_remember.isChecked()){

            Paper.book().write(OneTime.phoneSotrage,number);
            Paper.book().write(OneTime.passwordStorage,password);
        }

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
                            //if the phone owner is an admin!!!
                            if(phonedata.equals("Admin")){

                                Toast.makeText(loginActivity.this, "Admin Logged in successfully..", Toast.LENGTH_SHORT).show();
                                loadibg_bar.dismiss();

                                Intent intent = new Intent(loginActivity.this, LastAdminActivity.class);
                                startActivity(intent);

                            //if the phone owner is not an admin
                            }else if(phonedata.equals("Users")) {
                                Toast.makeText(loginActivity.this, "Logged in successfully..", Toast.LENGTH_SHORT).show();
                                loadibg_bar.dismiss();

                                Intent intent = new Intent(loginActivity.this, HomeActivity.class);
                                OneTime.OnlineUser = users;
                                intent.putExtra("phoneid",Number1);
                                startActivity(intent);
                            }


                        }else
                        {
                            loadibg_bar.dismiss();
                            Toast.makeText(loginActivity.this, "Wrong password..", Toast.LENGTH_SHORT).show();
                        }
                    }


                }else{
                    Toast.makeText(loginActivity.this, "Account with "+ number +" does not exist!!", Toast.LENGTH_SHORT).show();
                    loadibg_bar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
