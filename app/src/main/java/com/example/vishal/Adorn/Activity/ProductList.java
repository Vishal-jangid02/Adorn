package com.example.vishal.Adorn.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.vishal.Adorn.Interface.ItemClickListener;
import com.example.vishal.Adorn.MyPojo.ProductPojo;
import com.example.vishal.Adorn.R;
import com.example.vishal.Adorn.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference productList;

    String categoryId;

    FirebaseRecyclerAdapter<ProductPojo,ProductViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Product List");
        setSupportActionBar(toolbar);

        //Connection to Firebase
        firebaseDatabase =FirebaseDatabase.getInstance();
        productList=firebaseDatabase.getReference("furniture");

        //add recycler view from productlist
        recyclerView=(RecyclerView) findViewById(R.id.recyclerProduct);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Get Intent here from HomeActivity
        if(getIntent() !=null)
            categoryId=getIntent().getStringExtra("CategoryId");
        if(!categoryId.isEmpty() && categoryId !=null){
            loadListProduct(categoryId);
        }
    }

    private void loadListProduct(String categoryId) {
        adapter=new FirebaseRecyclerAdapter<ProductPojo, ProductViewHolder>
                (ProductPojo.class,R.layout.product_item,ProductViewHolder.class,productList.orderByChild("mainid").equalTo(categoryId))
            //This is like to :Select * from furniture where MenuId
        {

            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, ProductPojo model, int position) {

                viewHolder.product_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.product_image);

                final ProductPojo local=model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        //Go to new Acticity ProductDetail
                        Intent productDetail=new Intent(ProductList.this,ProductDetail.class);
                        productDetail.putExtra("ProductId",adapter.getRef(position).getKey());
                        startActivity(productDetail);
                        //Toast.makeText(ProductList.this,""+local.getName(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        //Set Adapter
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.cart_menu) {
            SharedPreferences sp = getSharedPreferences("user", 0);
            boolean flag = sp.getBoolean("flag", false);
            if (flag) {
                Intent intent = new Intent(ProductList.this, cart.class);
                startActivity(intent);
            } else {
                Toast.makeText(ProductList.this, "Please login or register", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ProductList.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

        if(id == R.id.user_menu){

            SharedPreferences sp = getSharedPreferences("user",0);
            boolean flag = sp.getBoolean("flag",false);
            if(flag){
                Toast.makeText(ProductList.this,"You are allready registered",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(ProductList.this,"Please Registration",Toast.LENGTH_LONG).show();
                Intent regIntent = new Intent(ProductList.this,RegistrationActivity.class);
                startActivity(regIntent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
    }
