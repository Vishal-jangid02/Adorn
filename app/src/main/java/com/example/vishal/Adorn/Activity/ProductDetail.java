
package com.example.vishal.Adorn.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.vishal.Adorn.MyPojo.ProductPojo;
import com.example.vishal.Adorn.MyPojo.orderpojo;
import com.example.vishal.Adorn.R;
import com.example.vishal.Adorn.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.example.vishal.Adorn.R.layout.activity_product_detail;


public class ProductDetail extends AppCompatActivity {

    TextView product_name,product_price,product_description;
    ImageView product_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Button btnCart;
    ProductPojo productPojo;
    String product_id=" ";
    Spinner product_wood,polish;
    String polish1;
    String woodItem;
    String price;
    String email;
    SharedPreferences sharedPreferences ;
    boolean flag2 = false;
    FirebaseDatabase database,database1;
    DatabaseReference products,reference;

    ProgressDialog progressDialog;

    FirebaseRecyclerAdapter<ProductPojo,ProductViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_product_detail);
        sharedPreferences = getSharedPreferences("user",0);
        email = sharedPreferences.getString("email","");

       // Connect Firebase to furniture table
        database=FirebaseDatabase.getInstance();
        products=database.getReference("furniture");
        database1 = FirebaseDatabase.getInstance();
        reference = database1.getReference("cartitem");


        //Connect to product_detail.xml file


       btnCart=(Button) findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sharedPreferences.getBoolean("flag",false)) {
                    final orderpojo pojo = new orderpojo(
                            product_id,
                            productPojo.getName(),
                            "1",
                            product_price.getText().toString(),
                            productPojo.getDiscount(),
                            product_wood.getSelectedItem().toString(),
                            polish.getSelectedItem().toString(), email);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot d : dataSnapshot.getChildren())
                            {
                                orderpojo pojo1 = new orderpojo();
                                pojo1 = d.getValue(orderpojo.class);
                                if(pojo1.getEmail().equals(email) && pojo1.getProductid().equals(product_id)){
                                    flag2 = true;
                                    break;
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    if(flag2){
                        Toast.makeText(ProductDetail.this, "Item Already present in cart", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        progressDialog=new ProgressDialog(ProductDetail.this);
                        progressDialog.setTitle("Login");
                        progressDialog.setMessage("Please wait...");

                        progressDialog.show();

                        reference.push().setValue(pojo);
                        Toast.makeText(ProductDetail.this,"Added to cart",Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
                else
                {
                    Toast.makeText(ProductDetail.this, "Login First", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProductDetail.this,LoginActivity.class));
                }

            /*new Database(getBaseContext()).addTocart(new orderpojo(
                        product_id,
                        productPojo.getName(),
                        "1",
                        product_price.getText().toString(),
                        productPojo.getDiscount(),
                    product_wood.getSelectedItem().toString(),
                    polish.getSelectedItem().toString(),
                    "asdf"
            ));*/

            }
        });



        product_description=(TextView) findViewById(R.id.product_description);
        product_name=(TextView) findViewById(R.id.product_name);
        product_price=(TextView) findViewById(R.id.product_price);
        product_image=(ImageView)findViewById(R.id.product_image);
        product_wood=(Spinner)findViewById(R.id.woodselectspinner);
        polish = (Spinner)findViewById(R.id.polishselectspinner);

        collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collapse_layout);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        //Get ProductId from intent of productList
        if(getIntent() !=null)
            product_id=getIntent().getStringExtra("ProductId");
        if(!product_id.isEmpty()){
            getDetailProduct(product_id);
        }

    }

    private void getDetailProduct(final String product_id) {
        products.child(product_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                productPojo=dataSnapshot.getValue(ProductPojo.class);

                Picasso.with(getBaseContext()).load(productPojo.getImage()).into(product_image);

                collapsingToolbarLayout.setTitle(productPojo.getName());
                product_name.setText(productPojo.getName());
                product_description.setText(productPojo.getDescription());
                product_price.setText(productPojo.getPrice());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
       });


        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.wood, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product_wood.setAdapter(arrayAdapter);
        product_wood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                woodItem = product_wood.getSelectedItem().toString();
                if (woodItem.equals("Walnut")) {
                    price = productPojo.getPrice();
                    ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(ProductDetail.this, R.array.Polish, android.R.layout.simple_spinner_item);
                    arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    polish.setAdapter(arrayAdapter1);
                    polish.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            polish1 = polish.getSelectedItem().toString();
                            if (polish1.equals("Acetone")) {
                                product_price.setText(price);
                            }
                            else if (polish1.equals("Linwood Oil")) {
                                double p = Double.valueOf(price);
                                p = p + 200;
                                product_price.setText("" + p);
                            }
                            else {
                                double p = Double.valueOf(price);
                                p = p + 400;
                                product_price.setText("" + p);
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
/*
                    Toast.makeText(ProductDetail.this, "Successful" + woodItem, Toast.LENGTH_LONG).show();
                    *//*btnFetchPrize.append("1000");*/
                }
                else if (woodItem.equals("Maple")) {
                    double p = Double.valueOf(productPojo.getPrice());
                    p = p * 1.5;
                    price = ""+p;
                    product_price.setText(" "+p);
                    ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(ProductDetail.this, R.array.Polish, android.R.layout.simple_spinner_item);
                    arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    polish.setAdapter(arrayAdapter1);
                    polish.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            polish1 = polish.getSelectedItem().toString();
                            if (polish1.equals("Acetone")) {
                                product_price.setText(price);
                                /*  Toast.makeText(ProductDetail.this, "Successful" + woodItem, Toast.LENGTH_LONG).show();
                                 *//*btnFetchPrize.append("1000");*/
                            }
                            else if (polish1.equals("Linwood Oil")) {
                                double p = Double.valueOf(price);
                                p = p + 200;
                                product_price.setText("" + p);
                            }
                            else {
                                double p = Double.valueOf(price);
                                p = p + 400;
                                product_price.setText("" + p);
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
                else {
                    double p = Double.valueOf(productPojo.getPrice());
                    p = p * 3;
                    price = ""+p;
                    product_price.setText("" + p);
                    ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(ProductDetail.this, R.array.Polish, android.R.layout.simple_spinner_item);
                    arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    polish.setAdapter(arrayAdapter1);
                    polish.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            polish1 = polish.getSelectedItem().toString();
                            if (polish1.equals("Acetone")) {
                                product_price.setText(price);
                                //Toast.makeText(ProductDetail.this, "Successful" + woodItem, Toast.LENGTH_LONG).show();
                                /*btnFetchPrize.append("1000");*/
                            }
                            else if (polish1.equals("Linwood Oil")) {
                                double p = Double.valueOf(price);
                                p = p + 200;
                                product_price.setText("" + p);
                            }
                            else {
                                double p = Double.valueOf(price);
                                p = p + 400;
                                product_price.setText("" + p);
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
    }

}
