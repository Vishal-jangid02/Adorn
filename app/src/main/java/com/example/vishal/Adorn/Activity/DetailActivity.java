package com.example.vishal.Adorn.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vishal.Adorn.MyPojo.RetrivePojo;
import com.example.vishal.Adorn.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends AppCompatActivity {

    GridLayout gridLayout;
    CardView card1;
    ImageView pro1;
    TextView itemName,itemPrize;

    FirebaseDatabase database;
    DatabaseReference reference;
    RetrivePojo retrivePojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        gridLayout=(GridLayout) findViewById(R.id.gridLayout);

        pro1=(ImageView) findViewById(R.id.pro1);
        itemName=(TextView) findViewById(R.id.itemName);
        itemPrize=(TextView) findViewById(R.id.itemPrize);
        database=FirebaseDatabase.getInstance();
       reference=database.getReference("chair");
        retrivePojo=new RetrivePojo();




        pro1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(DetailActivity.this,ProductActivity.class);
                startActivity(intent);
            }
        });
   }
}
