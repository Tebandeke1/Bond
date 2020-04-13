package com.example.phonebond.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phonebond.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class EditProductActivity extends AppCompatActivity {

    private ImageView ChangeImage;
    private EditText ChangeName,ChangeBond,ChangeColour,ChangePrice,ChangeDesc;
    private Button ApplyChanges,deleteBtn;

    private DatabaseReference productRef;
    private String ProductId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        ChangeImage = findViewById(R.id.change_product_Image);
        ChangeName = findViewById(R.id.change_product_name);
        ChangeBond = findViewById(R.id.change_product_bond_name);
        ChangeColour = findViewById(R.id.change_product_colour);
        ChangePrice = findViewById(R.id.change_product_price);
        ChangeDesc = findViewById(R.id.change_product_description);

        deleteBtn = findViewById(R.id.delete_btn);

        ApplyChanges = findViewById(R.id.apply_btn);

        ProductId = getIntent().getStringExtra("pid");
        productRef = FirebaseDatabase.getInstance().getReference().child("Product").child(ProductId);

        ApplyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyChange();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct();
            }
        });

        SaveChanges();
    }

    private void deleteProduct() {

        productRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(EditProductActivity.this, "Product has been deleted Successfully..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditProductActivity.this, AdminHomeActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void applyChange() {

        String pname = ChangeName.getText().toString();
        String pcolour = ChangeColour.getText().toString();
        String pprice = ChangePrice.getText().toString();
        String pbond = ChangeBond.getText().toString();
        String pdesc = ChangeDesc.getText().toString();


        if(pname.equals("")){
            Toast.makeText(this, "please provide Product name...!!1", Toast.LENGTH_SHORT).show();
        }else if(pcolour.equals("")){
            Toast.makeText(this, "Please provide product colour", Toast.LENGTH_SHORT).show();
        }else if(pbond.equals("")){
            Toast.makeText(this, "Please provide product bond", Toast.LENGTH_SHORT).show();
        }else if(pprice.equals("")){
            Toast.makeText(this, "Please provide product price", Toast.LENGTH_SHORT).show();
        }else if(pdesc.equals("")){
            Toast.makeText(this, "Please provide product description..", Toast.LENGTH_SHORT).show();
        }else {
            HashMap<String, Object> products = new HashMap<>();
            products.put("name", pname);
            products.put("colour", pcolour);
            products.put("description", pdesc);
            products.put("bond", pbond);
            products.put("price", pprice);

            productRef.updateChildren(products).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(EditProductActivity.this, "Product Updated....", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(EditProductActivity.this, AdminHomeActivity.class);
                        startActivity(intent);

                    }

                }
            });
        }
    }


    private void SaveChanges() {
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String PName = dataSnapshot.child("name").getValue().toString();
                    String PBond = dataSnapshot.child("bond").getValue().toString();
                    String PColour = dataSnapshot.child("colour").getValue().toString();
                    String PPrice = dataSnapshot.child("price").getValue().toString();
                    String PDescription = dataSnapshot.child("description").getValue().toString();
                    String PImage = dataSnapshot.child("image").getValue().toString();

                    ChangeName.setText(PName);
                    ChangeBond.setText(PBond);
                    ChangePrice.setText(PPrice);
                    ChangeColour.setText(PColour);
                    ChangeDesc.setText(PDescription);
                    Picasso.get().load(PImage).into(ChangeImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
