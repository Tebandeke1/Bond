package com.example.phonebond.Buyer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonebond.OneTime.OneTime;
import com.example.phonebond.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    TextView closeSettings, UpdateSettings,ChangeImage;
    CircleImageView ImageChange;
    EditText ChangeNames, ChangeNumber,Changeadress;

    private Button security;

    private Uri imageUri;
    private String MyUrl = "";
    private StorageReference storageProfilePictureReference;
    private StorageTask uploadTask;
    private String Checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        storageProfilePictureReference = FirebaseStorage.getInstance().getReference().child("Profile Pictures");

        closeSettings = (TextView) findViewById(R.id.close_settings_btn);
        security = findViewById(R.id.set_security_question);
        ChangeImage = (TextView)findViewById(R.id.change_image_profile_btn);
        UpdateSettings = (TextView)findViewById(R.id.update_settings_btn);

        ImageChange = (CircleImageView) findViewById(R.id.profile_image_change);

        ChangeNames = (EditText)findViewById(R.id.change_profile_names);
        ChangeNumber = (EditText)findViewById(R.id.change_profile_phone_number);
        Changeadress  = (EditText)findViewById(R.id.change_address);

        closeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(SettingsActivity.this);

            }
        });

        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SettingsActivity.this, SetSecurityActivity.class);
                intent.putExtra("check","settings");
                startActivity(intent);
            }
        });

        UpdateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Checker.equals("clicked")){

                    UserInforSaved();

                }else {

                    UpdateUserInfor();

                }

            }
        });

        UserInfoDisplay(ImageChange,ChangeNames,ChangeNumber,Changeadress);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            imageUri = result.getUri();
            ImageChange.setImageURI(imageUri);
        }else {

            Toast.makeText(this, "Error.... Please try again.", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(SettingsActivity.this,SettingsActivity.class));
            finish();
        }


    }

    private void UpdateUserInfor() {


        if(TextUtils.isEmpty(ChangeNames.getText().toString())){

            Toast.makeText(this, "Name is Mandatory....", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(ChangeNumber.getText().toString())){

            Toast.makeText(this, "Number is Mandatory....", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(Changeadress.getText().toString())){

            Toast.makeText(this, "Address is Mandatory....", Toast.LENGTH_SHORT).show();
        }else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

            HashMap<String, Object> userMap = new HashMap<>();

            userMap.put("name", ChangeNames.getText().toString());
            userMap.put("phone", ChangeNumber.getText().toString());
            userMap.put("address", Changeadress.getText().toString());
            ref.child(OneTime.OnlineUser.getPhone()).updateChildren(userMap);

            startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
            Toast.makeText(SettingsActivity.this, "Profile Updated....", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void UserInforSaved() {

       if(Checker.equals("clicked")){

            UploadImage();

        }

    }

    private void UploadImage() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating Profile...");
        progressDialog.setMessage("Wait while we update your profile...");
        progressDialog.setCancelable(false);
        progressDialog.show();

    if( imageUri != null){

            final StorageReference fileref = storageProfilePictureReference.child(OneTime.OnlineUser.getPhone() + ".jpg");

            uploadTask = fileref.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileref.getDownloadUrl() ;
                }
            })
            .addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if(task.isSuccessful())
                    {

                        try {
                            Uri downloadUrl = task.getResult();
                            MyUrl = downloadUrl.toString();

                        }catch (Exception e){
                            Toast.makeText(SettingsActivity.this, "Error" + e, Toast.LENGTH_SHORT).show();
                        }

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                        HashMap<String, Object> userMap = new HashMap<>();

                        userMap.put("name", ChangeNames.getText().toString());
                        userMap.put("phone", ChangeNumber.getText().toString());
                        userMap.put("address", Changeadress.getText().toString());
                        userMap.put("image", MyUrl);

                        ref.child(OneTime.OnlineUser.getPhone()).updateChildren(userMap);

                        progressDialog.dismiss();

                        startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
                        Toast.makeText(SettingsActivity.this, "Profile Updated....", Toast.LENGTH_SHORT).show();
                        finish();

                    }else {

                        progressDialog.dismiss();

                        Toast.makeText(SettingsActivity.this, "Error...", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
        Toast.makeText(this, "Image is not Selected...", Toast.LENGTH_SHORT).show();
    }

    }

    private void UserInfoDisplay(final CircleImageView imageChange, final EditText changeNames, final EditText changeNumber,final EditText changeadress) {

        DatabaseReference UserRaf = FirebaseDatabase.getInstance().getReference().child("Users").child(OneTime.OnlineUser.getPhone());

        UserRaf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    if(dataSnapshot.child("image").exists()){
                        String image = dataSnapshot.child("image").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String number = dataSnapshot.child("phone").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();

                        Picasso.get().load(image).into(imageChange);
                        changeNames.setText(name);
                        changeNumber.setText(number);
                        changeadress.setText(address);


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }
}
