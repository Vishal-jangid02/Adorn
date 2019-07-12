package com.example.vishal.Adorn.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.vishal.Adorn.Interface.ItemClickListener;
import com.example.vishal.Adorn.MyPojo.CategoryPojo;
import com.example.vishal.Adorn.MyPojo.MyPojo;
import com.example.vishal.Adorn.R;
import com.example.vishal.Adorn.ViewHolder.CategoryViewHolder;
//import com.example.vishal.Adorn.ViewHolder.RecycleActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView nav_view;
    TextView UserName;

    FirebaseDatabase database;
    DatabaseReference categoryReference;

    Button btnCustomize;
    View hview ;
    String uName = " ";

    SharedPreferences sp ;

    RecyclerView recyclerHome;
    RecyclerView.LayoutManager layoutManager;

    ViewFlipper viewFlipper;
    int slider[]={ R.drawable.flipper1,
            R.drawable.flipper2,
            R.drawable.flipper3,
            R.drawable.flipper4,
            R.drawable.flipper5
    };

    FirebaseRecyclerAdapter<CategoryPojo,CategoryViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

      /*  mdrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mdrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        nav_view = findViewById(R.id.nav_view);
        UserName = findViewById(R.id.UserName);

        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(HomeActivity.this, mdrawerlayout, toolbar, R.string.open, R.string.close);
        mdrawerlayout.setDrawerListener(mToggle);
        mToggle.syncState();


        SharedPreferences sp = getSharedPreferences("user",0);
        uName= sp.getString("name",null);
        if(uName != null) {

          hview = nav_view.getHeaderView(0);
            UserName = hview.findViewById(R.id.UserName);
            UserName.setText(uName);
            Log.e("7877","name :"+uName);
        }

        btnCustomize = (Button) findViewById(R.id.btnCustomize);
        btnCustomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, Customize.class);
                startActivity(intent);
            }
        });

        //Firebase
        database=FirebaseDatabase.getInstance();
        categoryReference=database.getReference("category");

        recyclerHome=(RecyclerView) findViewById(R.id.recyclerHome);
        recyclerHome.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerHome.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2));

        loadCategory();

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        for (int i :slider)
        {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(i);
            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(3000);
            viewFlipper.setAutoStart(true);
            viewFlipper.setInAnimation(this,android.R.anim.fade_in);
            viewFlipper.setOutAnimation(this,android.R.anim.fade_out);
        }
        nav_view.setNavigationItemSelectedListener(this);
    }

    private void loadCategory() {

        adapter=new FirebaseRecyclerAdapter<CategoryPojo, CategoryViewHolder>
                (CategoryPojo.class,R.layout.category_item,CategoryViewHolder.class,categoryReference) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, CategoryPojo model, int position) {
                viewHolder.textCateName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageCateView);

              final CategoryPojo clickItem=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Get CategoryId and send to new activity
                        Intent productlist=new Intent(HomeActivity.this,ProductList.class);
                        //Categoryid is key,so we get key of this item
                        productlist.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(productlist);

                        //Toast.makeText(HomeActivity.this,""+clickItem.getName(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        recyclerHome.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.cart_menu)
        {
            SharedPreferences sp = getSharedPreferences("user",0);
            boolean flag = sp.getBoolean("flag",false);
            if(flag) {
                Intent intent = new Intent(HomeActivity.this, cart.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(HomeActivity.this,"Please login or register",Toast.LENGTH_LONG).show();
                Intent intent =new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

        if(id == R.id.user_menu){

            SharedPreferences sp = getSharedPreferences("user",0);
            boolean flag = sp.getBoolean("flag",false);
            if(flag){
                Toast.makeText(HomeActivity.this,"You are allready registered",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(HomeActivity.this,"Please Registration",Toast.LENGTH_LONG).show();
                Intent regIntent = new Intent(HomeActivity.this,RegistrationActivity.class);
                startActivity(regIntent);
            }
        }

        return super.onOptionsItemSelected(item);
    }//end of onOptionsItemSelected()......


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.home) {
             Toast.makeText(HomeActivity.this,"home",Toast.LENGTH_LONG).show();
        }

        if(id== R.id.yourorder)
        {
            Intent cartIntent=new Intent (HomeActivity.this,YourOrder.class);
            startActivity(cartIntent);
        }

        if (id == R.id.contactus) {

            Intent intent1 = new Intent(Intent.ACTION_DIAL);
            intent1.setData(Uri.parse("tel:7976324569"));
            startActivity(intent1);
        }
        if(id == R.id.faqs) {
            Intent intent5 = new Intent(HomeActivity.this, HelpActivity.class);
            startActivity(intent5);
        }

        if (id == R.id.share) {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBody="Your body is here";
            String shareSub="Your  subject";
            intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
            intent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(intent,"Share using"));
        }

        if (id == R.id.feedback) {

            Intent intent3 = new Intent(HomeActivity.this,FeedbackActivity.class);
            startActivity(intent3);
        }
        if(id==R.id.logout)
        {
            sp = getSharedPreferences("user" , 0);
            boolean flag = sp.getBoolean("flag",false);


            if(flag) {
                Intent signin = new Intent(HomeActivity.this, LoginActivity.class);
                SharedPreferences qs = getSharedPreferences("user", 0);
                SharedPreferences.Editor edt = qs.edit();
                edt.clear();
                edt.commit();
                startActivity(signin);
                finish();
            }
            else{
                Toast.makeText(HomeActivity.this, "Already Logged Out", Toast.LENGTH_LONG).show();
                Intent signin = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(signin);
            }

        }
        mdrawerlayout.closeDrawer(Gravity.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        }else{

            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HomeActivity.this.finish();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        //super.onBackPressed();

    }

}
