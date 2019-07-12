package com.example.vishal.Adorn.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishal.Adorn.Common.Common;
import com.example.vishal.Adorn.MyPojo.Request;
import com.example.vishal.Adorn.MyPojo.orderpojo;
import com.example.vishal.Adorn.R;
import com.example.vishal.Adorn.ViewHolder.cartadaptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> keylist ;
    FirebaseDatabase database;
    DatabaseReference referencerequest;

    public static TextView totalprice;
    FirebaseDatabase firebaseDatabase ;
    DatabaseReference reference,referencedelete,referencedelete1;
    FButton btnplace;
    boolean flag1 = false;
   ArrayList<orderpojo> cart1,retriveCart;
    cartadaptor cd;
    double comp_price;
    SharedPreferences sharedPreferences;

    @Override
    public void onBackPressed() {

        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        sharedPreferences = getSharedPreferences("user",0);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("cartitem");
        referencedelete = firebaseDatabase.getReference("cartitem");
        referencedelete1 = firebaseDatabase.getReference("cartitem");

        database=FirebaseDatabase.getInstance();
        referencerequest= database.getReference("Request");

        retriveCart = new ArrayList<>();


        cart1 = new ArrayList<>();
        //Init
        recyclerView=(RecyclerView)findViewById(R.id.listcart);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        cd = new cartadaptor(cart1, cart.this);
        recyclerView.setAdapter(cd);



        totalprice=(TextView)findViewById(R.id.total);
        btnplace=(FButton)findViewById(R.id.placeorder);

        totalprice.setText("0");
        //List<orderpojo> olist = new Database(getApplicationContext()).getcarts();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comp_price = 0;
                Log.e("123456", "onDataChange: 1" );
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    orderpojo pojo = new orderpojo();
                    pojo = d.getValue(orderpojo.class);
                    if(pojo.getEmail().equals(getSharedPreferences("user",0).getString("email",""))){
                        Log.e("123456", "onDataChange: 1" );
                        comp_price = comp_price + Double.valueOf(pojo.getPrice());
                        cart1.add(pojo);
                        flag1=true;
                    }//end of if......
                }//end of for loop......

                Log.e("123456", "onDataChange: size"+ cart1.size() );
                totalprice.setText("" + comp_price);
                cd.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        btnplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //create new request
                referencedelete.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        keylist = new ArrayList<>();
                        for(DataSnapshot d: dataSnapshot.getChildren()){
                            orderpojo pojo2 = new orderpojo();
                            pojo2 = d.getValue(orderpojo.class);
                            if(sharedPreferences.getString("email","").equals(pojo2.getEmail())){
                                retriveCart.add(pojo2);

                              String key = d.getKey();
                              keylist.add(key);
                            }//end of if..
                        }//end of for..........
                        if(retriveCart.size()>0)
                            showAlertDialog();

                        else
                        {
                            Toast.makeText(cart.this,"No Product selectd by user",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        //loadlistfurniture();

    }

    private void showAlertDialog() {

        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(cart.this);
        alertDialog.setTitle("One more step");
        alertDialog.setMessage("Enter our address");
        final EditText edtAddress=new EditText(cart.this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress);//Add edit text to alert dalog
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               /* Request request=new Request(Common.currentUser.getEmail(),Common.currentUser.getName(),edtAddress.getText().toString(),
                totalprice.getText().toString(),cart1);
*/
               Request request = new Request();
                request.setEmail(sharedPreferences.getString("email"," "));
                request.setName(sharedPreferences.getString("name"," "));
                request.setStatus("Order Placed");
                request.setTotal(totalprice.getText().toString());
                request.setAddress(edtAddress.getText().toString());
                request.setPhone(sharedPreferences.getString("phone",""));
                request.setFurnitures(retriveCart);
                referencerequest.push().setValue(request);
                for (int i = 0 ; i<keylist.size();i++)
                {
                    referencedelete1.child(keylist.get(i)).removeValue();
                }
                Toast.makeText(cart.this,"Thank You  , Order is placed",Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();

            }
        });
        alertDialog.show();
    }

   /* private void loadlistfurniture() {
        cart1= (ArrayList<orderpojo>) new Database(this).getcarts();
        adapter=new cartadaptor(cart1,this);
        recyclerView.setAdapter(adapter);

        //calcuate total price
        int total=0;
        for(orderpojo order:cart1)
        {
            total+=(Double.valueOf(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
            totalprice.setText(total);


        }


    }*/
}
