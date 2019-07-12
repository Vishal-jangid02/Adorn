package com.example.vishal.Adorn.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishal.Adorn.R;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity{

    Spinner spinnerWood,spinnerMaterial,spinnerColor;

    String woodItem=" ";
    ImageView proImage,offerImage;
    TextView proName,proPrize;
    Button btnFeature,btnQuaity,btnWarranty,btnReturn,btnFetchPrize;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        proImage=(ImageView) findViewById(R.id.proImage);
        offerImage=(ImageView) findViewById(R.id.offerImage);
        proName=(TextView) findViewById(R.id.proName);
        proPrize=(TextView) findViewById(R.id.proPrize);
        btnFeature=(Button) findViewById(R.id.btnFeature);
        btnQuaity=(Button) findViewById(R.id.btnQuality);
        btnReturn=(Button) findViewById(R.id.btnReturn);
        btnFetchPrize=(Button) findViewById(R.id.btnFetchPrize);
        btnWarranty=(Button) findViewById(R.id.btnWarranty);
        spinnerWood=(Spinner) findViewById(R.id.spinnerWood);
        spinnerColor=(Spinner) findViewById(R.id.spinnerColor);
        spinnerMaterial=(Spinner) findViewById(R.id.spinnerMaterial);

        Intent intent=getIntent();
        String uploadImage=intent.getStringExtra("uploadImage");
        ImageView post_image=(ImageView)findViewById(R.id.proImage);
        Picasso.with(ProductActivity.this).load(uploadImage).into(post_image);

        String name=intent.getStringExtra("name");
        String price=intent.getStringExtra("Price");
        TextView post_name=(TextView) findViewById(R.id.proName);
        Toast.makeText(ProductActivity.this,""+name,Toast.LENGTH_LONG).show();


        //databaseReference= FirebaseDatabase.getInstance().getReference();

         ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(this,R.array.wood,android.R.layout.simple_spinner_item);
         arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWood.setAdapter(arrayAdapter);


        spinnerWood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ArrayAdapter<CharSequence> arrayAdapter1=ArrayAdapter.createFromResource(ProductActivity.this,R.array.metal,android.R.layout.simple_spinner_item);
                arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMaterial.setAdapter(arrayAdapter1);

                spinnerMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                ArrayAdapter<CharSequence> arrayAdapter2=ArrayAdapter.createFromResource(ProductActivity.this,R.array.Color,android.R.layout.simple_spinner_item);
                arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerColor.setAdapter(arrayAdapter2);


                woodItem=spinnerWood.getSelectedItem().toString();
                if(woodItem.equals("walnut")){

                    Toast.makeText(ProductActivity.this,"Successful"+woodItem,Toast.LENGTH_LONG).show();
                    /*btnFetchPrize.append("1000");*/
                    btnFetchPrize.setText("Pirze-1000");
                }
                else if(woodItem.equals("Maple")){
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
