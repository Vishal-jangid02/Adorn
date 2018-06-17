package com.example.vishal.Adorn.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vishal.Adorn.MyPojo.MyPojo;
import com.example.vishal.Adorn.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity {

    EditText editName,editMobileNumber,editEmail,editPassword,editConfirmPassword;
    Button btnRegistration;
    MyPojo myPojo;
    ArrayList<String> errors;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Registration");


        editName=(EditText) findViewById(R.id.editName);
        editMobileNumber=(EditText) findViewById(R.id.editMobileNumber);
        editEmail=(EditText) findViewById(R.id.editEmail);
        editPassword=(EditText)findViewById(R.id.editPassword);
        editConfirmPassword=(EditText) findViewById(R.id.editConfirmPassword);
        btnRegistration=(Button) findViewById(R.id.btnRegistration);
        errors=new ArrayList<>();
        myPojo=new MyPojo();

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=editName.getText().toString();
                String moblienumber=editMobileNumber.getText().toString();
                String email=editEmail.getText().toString();
                String password=editPassword.getText().toString();
                String confirmPassword=editConfirmPassword.getText().toString();

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

                else if(moblienumber.length()==9){
                    errors.add("name");
                    editMobileNumber.requestFocus();
                    editMobileNumber.setError("ENTER 10 NUMBER");
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
                else if(!password.matches("[0-9a-zA-Z.]+")){
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
                    Toast.makeText(RegistrationActivity.this,"sucessful",Toast.LENGTH_LONG).show();
                    myPojo.setName(name);
                    myPojo.setMobileNumber(moblienumber);
                    myPojo.setEmail(email);
                    myPojo.setPassword(password);
                    databaseReference.setValue(myPojo);
                    Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}
