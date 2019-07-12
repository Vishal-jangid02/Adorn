package com.example.vishal.Adorn.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vishal.Adorn.MyPojo.RetrivePojo;
import com.example.vishal.Adorn.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecycleActivity extends AppCompatActivity {

    String temp="";
    static Intent intent;
    RecyclerView myrecyclerView;
    DatabaseReference databaseReference;
    static RetrivePojo mPojo ;
    static String name1,price1;
    static ArrayList<String> arrayListName = new ArrayList<>();
    static ArrayList<String> arrayListPrice = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        Intent intent=getIntent();
        String cate=intent.getStringExtra("categoryIntent");

        databaseReference = FirebaseDatabase.getInstance().getReference().child(cate);
        databaseReference.keepSynced(true);

        myrecyclerView = (RecyclerView) findViewById(R.id.myrecyclerView);
        myrecyclerView.setHasFixedSize(true);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<RetrivePojo, PojoViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RetrivePojo, PojoViewHolder>
                (RetrivePojo.class, R.layout.activity_cart, PojoViewHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(PojoViewHolder viewHolder, RetrivePojo model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setPrize(model.getPrice());
                viewHolder.setUplaodImage(getApplicationContext(),model.getUploadImage());
                mPojo = model;

            }
        };
        myrecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public static class PojoViewHolder extends RecyclerView.ViewHolder{

        View view;
        Context context;
        public PojoViewHolder(View itemView) {

            super(itemView);

            view=itemView;
            context=itemView.getContext();
                  }

        public void setName(final String name){
            TextView post_name=(TextView)view.findViewById(R.id.itemName);
            post_name.setText(name);
            arrayListName.add(name);
            name1 = name;

        }
        public void setPrize(final String prize){
            TextView post_prize=(TextView)view.findViewById(R.id.itemPrize);
            post_prize.setText(prize);
            price1 = prize;
            arrayListPrice.add(prize);
        }

        public void setUplaodImage(Context cxt, final String uplaodImage){


            ImageView post_image=(ImageView)view.findViewById(R.id.pro1);
            Picasso.with(cxt).load(uplaodImage).into(post_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(v.getContext(),ProductActivity.class);
                    intent.putExtra("uploadImage",uplaodImage);
                    intent.putExtra("name",name1);
                    intent.putExtra("Price",price1);
                    context.startActivity(intent);
                }
            });

        }

    }

}