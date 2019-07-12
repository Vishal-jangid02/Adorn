package com.example.vishal.Adorn.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vishal.Adorn.MyPojo.Request;
import com.example.vishal.Adorn.MyPojo.RetrivePojo;
import com.example.vishal.Adorn.MyPojo.orderpojo;
import com.example.vishal.Adorn.R;
import com.example.vishal.Adorn.ViewHolder.CustomAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class YourOrder extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference1;
    Request requestPojo;
    ListView listView;
    CustomAdapter customAdapter;
    public static String status;

    Request request1;
    String key;
    Request request2;
    Button cancelOrder;
    ArrayList<orderpojo> itemList;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_order);



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Request");
        databaseReference1 = firebaseDatabase.getReference("Request");

        cancelOrder = (Button) findViewById(R.id.cancelOrder);
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        boolean flag =false;
                        for(DataSnapshot d:dataSnapshot.getChildren()){

                            request2 = new Request();
                            request2 =  d.getValue(Request.class);

                            if(sp.getString("email","").equals(request2.getEmail())){
                                flag =true;
                                requestPojo = request2;
                                requestPojo.setStatus("cancel");
                                key = d.getKey();
                                databaseReference.child(key).child("status").setValue("cancel");
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));

                                break;
                            }//end of if...
                        }//end of for.........
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //databaseReference.child(key).removeValue();
                //databaseReference1.push().setValue(requestPojo);
                //startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });

        itemList = new ArrayList<>();

        sp = getSharedPreferences("user",0);


        listView = (ListView) findViewById(R.id.listOrderStatus);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                boolean flag =false;
                for(DataSnapshot d:dataSnapshot.getChildren()){

                    Log.e("998", "onDataChange: "+d);
                     request1 = new Request();
                    request1 =  d.getValue(Request.class);

                    if(sp.getString("email","").equals(request1.getEmail()) && !(request1.getStatus().equals("cancel"))){
                        flag =true;
                        requestPojo = request1;
                        break;
                    }//end of if...
                }//end of for.....


                if(flag){
                    itemList = requestPojo.getFurnitures();
                    status = requestPojo.getStatus();
                    customAdapter = new CustomAdapter(YourOrder.this,R.layout.orderstatuslayout,itemList);
                    listView.setAdapter(customAdapter);
                }//end of if..
                else
                {
                    Toast.makeText(YourOrder.this,"No Order Place",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
/*
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                boolean flag =false;
                for(DataSnapshot d:dataSnapshot.getChildren()){

                    Request request1 = new Request();
                   request1 =  d.getValue(Request.class);

                   if(sp.getString("email","").equals(request1.getEmail()) && !(request1.getStatus().equals("cancel"))){
                       flag =true;
                       requestPojo = request1;
                       break;
                   }//end of if...
                }//end of for.....


                if(flag){
                    itemList = requestPojo.getFurnitures();
                    status = requestPojo.getStatus();
                    customAdapter = new CustomAdapter(YourOrder.this,R.layout.orderstatuslayout,itemList);
                    listView.setAdapter(customAdapter);
                }//end of if..
                else
                {
                    Toast.makeText(YourOrder.this,"No Order Place",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }
}
