package com.example.vishal.Adorn.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vishal.Adorn.MyPojo.MyPojo;
import com.example.vishal.Adorn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity {

    EditText editName,editMobileNumber,editEmail,editPassword,editConfirmPassword,editAddress;
    Button btnRegistration;
    MyPojo myPojo;
    ArrayList<String> errors;
    FirebaseAuth mAuth;

    String name=" ",moblienumber=" ",address=" ",email=" ",password=" ",confirmPassword=" ";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth=FirebaseAuth.getInstance();
       firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Registration");


        editName=(EditText) findViewById(R.id.editName);
        editMobileNumber=(EditText) findViewById(R.id.editMobileNumber);
        editAddress=(EditText) findViewById(R.id.editAddress);
        editEmail=(EditText) findViewById(R.id.editEmail);
        editPassword=(EditText)findViewById(R.id.editPassword);
        editConfirmPassword=(EditText) findViewById(R.id.editConfirmPassword);
        btnRegistration=(Button) findViewById(R.id.btnRegistration);
        errors=new ArrayList<>();
        myPojo=new MyPojo();

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name=editName.getText().toString();
                moblienumber=editMobileNumber.getText().toString();
                address=editAddress.getText().toString();
                email=editEmail.getText().toString();
                password=editPassword.getText().toString();
                confirmPassword=editConfirmPassword.getText().toString();

                /*Name validation*/
                if(name.length()==0){
                    errors.add("name");
                    editName.requestFocus();
                    editName.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!name.matches("[a-zA-Z ]+")){
                    errors.add("name");
                    editName.requestFocus();
                    editName.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                }
                else if(name.length()<5 && name.length()<20){
                    errors.add("name");
                    editName.requestFocus();
                    editName.setError("ENTER MIN. 10 CHARACTER");
                }


                /*MobileNumber validation*/
                else  if(moblienumber.length()==0){
                    errors.add("name");
                    editMobileNumber.requestFocus();
                    editMobileNumber.setError("FIELD CANNOT BE EMPTY");
                }

                else if(moblienumber.length()<=9  && moblienumber.length()>=12){
                    errors.add("name");
                    editMobileNumber.requestFocus();
                    editMobileNumber.setError("ENTER 10 NUMBER");
                }

                else if(address.length()==0){
                    errors.add("address");
                    editAddress.requestFocus();
                    editAddress.setError("FIELD CANNOT BE EMPTY");
                }
                /*Email Validation*/
                 else if(email.length()==0){
                    errors.add("name");
                    editEmail.requestFocus();
                    editEmail.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!email.matches("[0-9a-zA-Z.]+" + "@gmail.com")){
                    errors.add("name");
                    editEmail.requestFocus();
                    editEmail.setError("ENTER CORRECT EMAIL");
                }


                /*Password Validation*/
                else if(password.length()==0){
                    errors.add("name");
                    editPassword.requestFocus();
                    editPassword.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!password.matches("[0-9a-zA-Z@.]+")){
                    errors.add("name");
                    editPassword.requestFocus();
                    editPassword.setError("ENTER CORRECT PASSWORD");
                }
                else if(password.length()<8){
                    errors.add("name");
                    editPassword.requestFocus();
                    editPassword.setError("ENTER MiN 8 CHARACTER");
                }

                else if(!confirmPassword.equals(password)){
                    errors.add("name");
                    editConfirmPassword.requestFocus();
                    editConfirmPassword.setError("CHECK YOUR PASSWORD");
                }


                if(errors.isEmpty()){
                    Log.e("123456", "onClick: else" );
                    auto();/*function for Authentication*/
                    /*Toast.makeText(RegistrationActivity.this,"sucessful",Toast.LENGTH_LONG).show();*/
                }

            }
        });
    }

    public void auto(){

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful()){


                    progressDialog=new ProgressDialog(RegistrationActivity.this);
                    progressDialog.setTitle("Registration.....");
                    progressDialog.setMessage("Please wait...");

                    progressDialog.show();
                    Toast.makeText(RegistrationActivity.this,"Successful Registration",Toast.LENGTH_LONG).show();
                    myPojo.setName(name);
                    myPojo.setMobileNumber(moblienumber);
                    myPojo.setAddress(address);
                    myPojo.setEmail(email);
                    myPojo.setPassword(password);
                    databaseReference.push().setValue(myPojo);

                    Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    if(task.getException()instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(RegistrationActivity.this,"Allready Registered User",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(RegistrationActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
