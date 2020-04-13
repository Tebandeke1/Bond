package com.example.phonebond.Buyer;

import android.content.Intent;
import android.os.Bundle;

import com.example.phonebond.Model.products;
import com.example.phonebond.OneTime.OneTime;
import com.example.phonebond.PackageViewHolder.ViewHolder;
import com.example.phonebond.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference productsRef;

    private RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;

    private String phoid;

    private String messo;

    private String BondNameCars = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        phoid = intent.getStringExtra("phoneid");
        BondNameCars = intent.getStringExtra("ValueItem");


        productsRef = FirebaseDatabase.getInstance().getReference().child("Product");

        //tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //floating Action bar
       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ChartActivity.class);
                intent.putExtra("phone1",phoid);
                startActivity(intent);

            }
        });

        //Drawer layout
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        //setting user profile name in case some one has logged in just in case
        View headerView = navigationView.getHeaderView(0);
        TextView userProfileName = headerView.findViewById(R.id.user_profile_name);
        CircleImageView userProfileImage = headerView.findViewById(R.id.user_profile_image);

            //current user to keep him logged in
            userProfileName.setText(OneTime.OnlineUser.getName());

            Picasso.get().load(OneTime.OnlineUser.getImage()).placeholder(R.drawable.pro).into(userProfileImage);



        recyclerView = findViewById(R.id.recycle_menu);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }


    @Override
    protected void onStart() {
        super.onStart();

        if (BondNameCars != ""){


            FirebaseRecyclerOptions<products> options = new FirebaseRecyclerOptions.Builder<products>()
                    .setQuery(productsRef.orderByChild("bond").equalTo(BondNameCars), products.class).build();


            FirebaseRecyclerAdapter<products, ViewHolder> adapter = new FirebaseRecyclerAdapter<products, ViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i, @NonNull final products products) {

                    viewHolder.proPrice.setText("Price = "+products.getPrice()+"$");
                    viewHolder.proDesc.setText(products.getDescription());
                    viewHolder.proName.setText(products.getName());
                    viewHolder.proColour.setText(products.getColour());
                    viewHolder.proBond.setText(products.getBond());
                    Picasso.get().load(products.getImage()).into(viewHolder.proImage);

                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                            intent.putExtra("pid", products.getPid());
                            startActivity(intent);
                        }

                    });


                }

                @NonNull
                @Override
                public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout,parent,false);

                    ViewHolder viewHolder = new ViewHolder(view);
                    return viewHolder;
                }
            };

            recyclerView.setAdapter(adapter);
            adapter.startListening();


        }else{


            FirebaseRecyclerOptions<products> options = new FirebaseRecyclerOptions.Builder<products>()
                    .setQuery(productsRef.orderByChild("productstate").equalTo("Approved"), products.class).build();


            FirebaseRecyclerAdapter<products, ViewHolder> adapter = new FirebaseRecyclerAdapter<products, ViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i, @NonNull final products products) {

                    viewHolder.proPrice.setText("Price = "+products.getPrice()+"$");
                    viewHolder.proDesc.setText(products.getDescription());
                    viewHolder.proName.setText(products.getName());
                    viewHolder.proColour.setText(products.getColour());
                    viewHolder.proBond.setText(products.getBond());
                    Picasso.get().load(products.getImage()).into(viewHolder.proImage);

                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                            intent.putExtra("pid", products.getPid());
                            startActivity(intent);
                        }

                    });


                }

                @NonNull
                @Override
                public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout,parent,false);

                    ViewHolder viewHolder = new ViewHolder(view);
                    return viewHolder;
                }
            };

            recyclerView.setAdapter(adapter);
            adapter.startListening();


        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Chart) {

            Intent intent = new Intent(HomeActivity.this,ChartActivity.class);
            intent.putExtra("phone1",phoid);
            startActivity(intent);

        } else if (id == R.id.search) {

            Intent intent = new Intent(HomeActivity.this, SearchProductActivity.class);
            startActivity(intent);

        } else if (id == R.id.categories) {

            Intent intent = new Intent(HomeActivity.this, OneMoreActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_settings) {


            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.BondTypes) {

            Intent intent = new Intent(HomeActivity.this, BondNamesActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String share = "Share Your Subject here";
            intent.putExtra(Intent.EXTRA_SUBJECT,share);
            startActivity(Intent.createChooser(intent,"Share Using..."));

        } else if (id == R.id.nav_logout) {

            Paper.book().destroy();

            Intent intent = new Intent(HomeActivity.this, loginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
