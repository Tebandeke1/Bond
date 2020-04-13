package com.example.phonebond.Buyer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phonebond.Admin.ApproveNewCarsActivity;
import com.example.phonebond.Model.products;
import com.example.phonebond.PackageViewHolder.ViewHolder;
import com.example.phonebond.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

public class BondNamesActivity extends AppCompatActivity {

    private DatabaseReference appRef;

    ListView listView;
    ArrayList<String> addProduct = new ArrayList<String>();

    ArrayAdapter<String> arrayAdapter;

    String value = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bond_names);

        appRef = FirebaseDatabase.getInstance().getReference().child("Sellers");

        listView = findViewById(R.id.BondList);

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,addProduct);
        listView.setAdapter(arrayAdapter);

        appRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                 value = dataSnapshot.getValue(products.class).getBond();

                if(addProduct.contains(value) && value.isEmpty()){

                }else{

                    addProduct.add(value);
                    arrayAdapter.notifyDataSetChanged();
                }

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        String Value_List = addProduct.get(i).toString();

                            Intent intent = new Intent(BondNamesActivity.this,HomeActivity.class);
                            intent.putExtra("ValueItem", Value_List);

                    if(value == ""){

                        addProduct.remove(value);
                    }          startActivity(intent);

                            Toast.makeText(BondNamesActivity.this, Value_List, Toast.LENGTH_SHORT).show();


                    }
                });

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        onStart();
    }


    @Override
    protected void onStart() {
        super.onStart();


    }
}
