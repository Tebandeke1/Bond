package com.example.phonebond.Bond;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phonebond.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SellerAddNewProductActivity extends AppCompatActivity {

    private String Colour,Name,Description,Price,SaveCurrentdate,SaveCurrentTime,productRandomKey,downloadImageUrl;

    private EditText carColour,carName,carDescription,carPrice;
    private ImageView carImage;
    private Button submitButton;
    private ProgressDialog loadibg_bar;
    private String categoryname;
    private EditText carBond;
    private StorageReference productImageRef;

    private DatabaseReference databaseReference,bondRef;

    private static final int gallery  = 1;

    private Uri imageUri;

    private String bName,bAddress,bPhone,bEmail,bId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bond_add_product);
        productImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Product");
        bondRef = FirebaseDatabase.getInstance().getReference().child("Sellers");

        loadibg_bar =  new ProgressDialog(this);
        carColour = (EditText)findViewById(R.id.car_colour);
        carName = (EditText)findViewById(R.id.car_name);
        carDescription = (EditText)findViewById(R.id.car_description);
        carPrice = (EditText)findViewById(R.id.car_price);
        carImage = (ImageView)findViewById(R.id.car_image);
        submitButton = (Button)findViewById(R.id.car_submit_button);
        categoryname = getIntent().getExtras().get("category").toString();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SubmitProduct();
            }
        });

        carImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galarryIntent = new Intent();
                galarryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galarryIntent.setType("image/*");
                startActivityForResult(galarryIntent,gallery);
            }
        });

        //Retriving Bond information

        bondRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    bName = dataSnapshot.child("bond").getValue().toString();
                    bEmail = dataSnapshot.child("email").getValue().toString();
                    bPhone = dataSnapshot.child("phone").getValue().toString();
                    bAddress = dataSnapshot.child("Address").getValue().toString();
                    bId = dataSnapshot.child("Sid").getValue().toString();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
        //image activity on for storing it
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == gallery && resultCode == RESULT_OK && data!=null){
            imageUri = data.getData();
            carImage.setImageURI(imageUri);

        }

    }
//submit button for the products to the database starting point
    private void SubmitProduct() {
        Colour = carColour.getText().toString();
        Name = carName.getText().toString();
        Description = carDescription.getText().toString();
        Price = carPrice.getText().toString();

        if(imageUri == null){
            Toast.makeText(this, "Car Image is Mandatory for car Image view...", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(Colour)){

            Toast.makeText(this, "Car Colour is needed please", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(Name)) {

            Toast.makeText(this, "Car Name is needed please", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(Description)) {

            Toast.makeText(this, "Car Description is needed please", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(Price)) {

            Toast.makeText(this, "Car price is needed please", Toast.LENGTH_SHORT).show();

        }else {
                storeProductInformation();
        }

    }
        //storing product information
    private void storeProductInformation() {


        loadibg_bar.setTitle("Adding New Car");
        loadibg_bar.setCancelable(false);
        loadibg_bar.setMessage("Wait while we add car to the database!!!");
        loadibg_bar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM dd,yyyy");

        SaveCurrentdate = simpleDateFormat.format(calendar.getTime());

        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss a");
        SaveCurrentTime = simpleTimeFormat.format(calendar.getTime());

        productRandomKey = SaveCurrentdate  + SaveCurrentTime;


        final StorageReference imagePath = productImageRef.child(imageUri.getLastPathSegment() + productRandomKey +".jpg");

        final UploadTask uploadTask = imagePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                loadibg_bar.dismiss();
                Toast.makeText(SellerAddNewProductActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(SellerAddNewProductActivity.this, "Image Uploaded Successfully..", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }

                        downloadImageUrl = imagePath.getDownloadUrl().toString();
                        return imagePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){

                            downloadImageUrl = task.getResult().toString();

                            saveproductInformation();
                        }
                    }
                });

            }
        });


    }

    private void saveproductInformation() {

        HashMap<String, Object> products = new HashMap<>();
        products.put("pid",productRandomKey);
        products.put("time",SaveCurrentTime);
        products.put("image",downloadImageUrl);
        products.put("date",SaveCurrentdate);
        products.put("category",categoryname);
        products.put("name",Name);
        products.put("colour",Colour);
        products.put("description",Description);
        products.put("price",Price);
        products.put("bond",bName);
        products.put("email",bEmail);
        products.put("phone",bPhone);
        products.put("Sid",bId);
        products.put("SellerAddress",bAddress);
        products.put("productstate","Not Approved");

        databaseReference.child(productRandomKey).updateChildren(products)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            loadibg_bar.dismiss();
                            Toast.makeText(SellerAddNewProductActivity.this, "Product added Successfully..", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SellerAddNewProductActivity.this, BondCatgoryActivity.class);
                            startActivity(intent);

                        }else {
                            loadibg_bar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(SellerAddNewProductActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
