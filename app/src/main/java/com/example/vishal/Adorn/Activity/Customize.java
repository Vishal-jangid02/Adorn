package com.example.vishal.Adorn.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishal.Adorn.MyPojo.CustomPojo;
import com.example.vishal.Adorn.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

public class Customize extends AppCompatActivity {

    Spinner categorySpinner, woodSpinner, polishSpinner, metalSpinner, colorSpinner;

    EditText txtHeight, txtWidth, txtLength;

    ArrayList<String> errors;

    ImageView uploadImage;
    Uri uri;
    Bitmap bitmap;
    Button btnSubmit;


    CustomPojo customPojo;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        firebaseDatabase = FirebaseDatabase.getInstance();


        customPojo = new CustomPojo();
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        txtHeight = (EditText) findViewById(R.id.txtHeight);
        txtLength = (EditText) findViewById(R.id.txtLength);
        txtWidth = (EditText) findViewById(R.id.txtWidth);

        uploadImage = (ImageView) findViewById(R.id.uploadImage);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        woodSpinner = (Spinner) findViewById(R.id.woodSpinner);
        polishSpinner = (Spinner) findViewById(R.id.polishSpinner);
        metalSpinner = (Spinner) findViewById(R.id.metalSpinner);
        colorSpinner = (Spinner) findViewById(R.id.colorSpinner);


        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0);
            }
        });//uploadImage() close...........


        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(Customize.this, R.array.category, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(arrayAdapter);

        ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(Customize.this, R.array.wood1, android.R.layout.simple_spinner_item);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        woodSpinner.setAdapter(arrayAdapter1);

        ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(Customize.this, R.array.metal, android.R.layout.simple_spinner_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        metalSpinner.setAdapter(arrayAdapter2);

        ArrayAdapter<CharSequence> arrayAdapter3 = ArrayAdapter.createFromResource(Customize.this, R.array.Polish1, android.R.layout.simple_spinner_item);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        polishSpinner.setAdapter(arrayAdapter3);

        ArrayAdapter<CharSequence> arrayAdapter4 = ArrayAdapter.createFromResource(Customize.this, R.array.Color, android.R.layout.simple_spinner_item);
        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(arrayAdapter4);


        SharedPreferences sp = getSharedPreferences("user", 0);
        boolean flag = sp.getBoolean("flag", false);
        final String email = sp.getString("email", "");
        final String phone = sp.getString("phone", "");

        if (flag) {
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (txtHeight == null) {
                        errors.add("height");
                        txtHeight.requestFocus();
                        txtHeight.setError("FIELD CANNOT BE EMPTY");
                    }

                    databaseReference = firebaseDatabase.getReference("Customize");

                    customPojo.setCatespinner(categorySpinner.getSelectedItem().toString());
                    customPojo.setWoodSpinner(woodSpinner.getSelectedItem().toString());
                    customPojo.setColorspinner(colorSpinner.getSelectedItem().toString());
                    customPojo.setMetalspinner(metalSpinner.getSelectedItem().toString());
                    customPojo.setPolishspinner(polishSpinner.getSelectedItem().toString());
                    customPojo.setTxtheight(txtHeight.getText().toString());
                    customPojo.setTxtlength(txtLength.getText().toString());
                    customPojo.setTxtwidth(txtWidth.getText().toString());
                    customPojo.setEmail(email);
                    customPojo.setPhone(phone);


                    storageReference = FirebaseStorage.getInstance().getReference("Customize/"+System.currentTimeMillis());
                    storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.e("12345...", "onSuccess: method");

                            final String img = taskSnapshot.getDownloadUrl().toString();
                            customPojo.setUploadimage(img);

                        }
                    });

                    final ProgressDialog progressDialog = new ProgressDialog(Customize.this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                     databaseReference.push().setValue(customPojo);
                            Toast.makeText(Customize.this, "Your request is submitted", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Customize.this,HomeActivity.class));
                            finish();

                    progressDialog.dismiss();

                }
            });
        }
        else
        {
            Toast.makeText(Customize.this,"Please login",Toast.LENGTH_LONG).show();
            startActivity(new Intent(Customize.this,LoginActivity.class));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0 || requestCode==RESULT_OK && data != null && data.getData() != null){
            uri=data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                uploadImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }//end of If...........
    }//end of onActivityResult().......
}//end of class....
