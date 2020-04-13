package com.example.phonebond.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonebond.OneTime.OneTime;
import com.example.phonebond.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SetSecurityActivity extends AppCompatActivity {

    private String check = "";

    private TextView pageTitle,TextAnswer;

    private EditText question1, question2,phoneNumber;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_security);

        check = getIntent().getStringExtra("check");

        question1 = findViewById(R.id.question1);
        question2 = findViewById(R.id.question2);
        phoneNumber = findViewById(R.id.phone_reset);
        pageTitle = findViewById(R.id.reset_password_view);
        TextAnswer = findViewById(R.id.text_answer);

        submit = findViewById(R.id.submit_btn);


    }

    @Override
    protected void onStart() {
        super.onStart();

        phoneNumber.setVisibility(View.GONE);

        if (check.equals("settings")){

            pageTitle.setText("Set Questions");
            TextAnswer.setText("Please set Answers for the following questions");

            submit.setText("Set Answers..");

            seeAnswers();

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    setAnswers();

                }
            });


        }else if(check.equals("login")){

            phoneNumber.setVisibility(View.VISIBLE);


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    verifyUser();

                }
            });

        }
    }

    private void verifyUser() {

        final String phone = phoneNumber.getText().toString();
        final String answer1 = question1.getText().toString().toLowerCase();
        final String answer2 = question2.getText().toString().toLowerCase();

        if(!phone.equals("") && !answer1.equals("") && !answer2.equals("")){

            final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(phone);

            dataRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()){

                        String mphone = dataSnapshot.child("phone").getValue().toString();

                        if (dataSnapshot.hasChild("Security Questions")){
                            String ans1 = dataSnapshot.child("Security Questions").child("question1").getValue().toString();
                            String ans2 = dataSnapshot.child("Security Questions").child("question2").getValue().toString();
                            if(!ans1.equals(answer1)){

                                Toast.makeText(SetSecurityActivity.this, "Your Favourite food answer is incorrect...", Toast.LENGTH_SHORT).show();

                            }else if(!ans2.equals(answer2)){

                                Toast.makeText(SetSecurityActivity.this, "The Name of your home Village is wrong...", Toast.LENGTH_SHORT).show();
                            }else{

                                AlertDialog.Builder builder = new AlertDialog.Builder(SetSecurityActivity.this);

                                builder.setTitle("New Password Setting Dialog");

                                final EditText new_pass = new EditText(SetSecurityActivity.this);

                                new_pass.setHint("Type Your new Password here.....");

                                builder.setView(new_pass);

                                builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        if(!new_pass.getText().toString().equals("")){

                                            dataRef.child("password").setValue(new_pass.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if(task.isSuccessful()){
                                                                Toast.makeText(SetSecurityActivity.this, "Password has been Updated Successfully...", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(SetSecurityActivity.this, loginActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }

                                                        }
                                                    });



                                        }else {
                                            Toast.makeText(SetSecurityActivity.this, "Please provide new password..", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        dialogInterface.cancel();

                                    }
                                });

                                builder.show();

                            }


                        }else

                        {
                            Toast.makeText(SetSecurityActivity.this, "You Did not set recover password questions..", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SetSecurityActivity.this, "Phone Number does not exist..", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }else{
            Toast.makeText(this, " Make sure all the fields are not Empty", Toast.LENGTH_SHORT).show();
        }

    }

    private void setAnswers() {

        String answer1 = question1.getText().toString().toLowerCase();
        String answer2 = question2.getText().toString().toLowerCase();

        if(answer1.equals("")|| answer2.equals("")){
            Toast.makeText(SetSecurityActivity.this, "Please Answer both questions...", Toast.LENGTH_SHORT).show();
        }else{

            DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("Users").child(OneTime.OnlineUser.getPhone());

            HashMap<String, Object> UserData = new HashMap<>();

            UserData.put("question1",answer1);
            UserData.put("question2",answer2);

            dataRef.child("Security Questions").updateChildren(UserData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(SetSecurityActivity.this, "Security Answers have been submitted...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SetSecurityActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }

                }
            });

        }

    }

    public void seeAnswers(){

        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("Users").child(OneTime.OnlineUser.getPhone());

        dataRef.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String ans1 = dataSnapshot.child("question1").getValue().toString();
                    String ans2 = dataSnapshot.child("question2").getValue().toString();

                    question1.setText(ans1);
                    question2.setText(ans2);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
