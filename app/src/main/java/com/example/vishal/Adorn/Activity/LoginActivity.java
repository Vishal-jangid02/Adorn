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

import com.example.vishal.Adorn.Common.Common;
import com.example.vishal.Adorn.MyPojo.MyPojo;
import com.example.vishal.Adorn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button btnSkip,btnSignUp,btnLogin,btnForgetPassword;
    EditText editEmail,editPassword;

    ProgressDialog progressDialog;
   // SharedPreferences sp;
    String email=" ",password=" ";
    FirebaseAuth mAuth;

    //SharedPreferences.Editor editor;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
        MyPojo myPojo,pojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Registration");

        myPojo=new MyPojo();

        editEmail=(EditText) findViewById(R.id.editEmail);
        editPassword=(EditText) findViewById(R.id.editPassword);

        btnSignUp=(Button) findViewById(R.id.singUp);
        btnSkip=(Button) findViewById(R.id.btnSkip);
        btnLogin=findViewById(R.id.btnLogin);
        btnForgetPassword=(Button) findViewById(R.id.btnForgetPassword);


        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForgetPassActivity.class);
                startActivity(intent);
            }
        });
       btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=editEmail.getText().toString();
                password=editPassword.getText().toString();
                int flag1=0,flag2=0;

                if(email.length()==0){
                    flag1=1;
                    editEmail.requestFocus();
                    editEmail.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!email.matches("[0-9a-zA-Z.]+" + "@gmail.com")){
                    flag1=1;
                    editEmail.requestFocus();
                    editEmail.setError("ENTER CORRECT EMAIL");
                }
                else {
                    flag1=0;
                }

                if(password.length()==0){
                    flag2=1;
                    editPassword.requestFocus();
                    editPassword.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!password.matches("[0-9a-zA-Z@.]+")){
                    flag2=1;
                    editPassword.requestFocus();
                    editPassword.setError("ENTER CORRECT PASSWORD");
                }
                else if(password.length()<8){
                    flag2=1;
                    editPassword.requestFocus();
                    editPassword.setError("ENTER MiN 8 CHARACTER");
                }
                else {
                    flag2=0;
                }

                if(flag1==0 && flag2==0){
                    login();
                }
            }
        });


    }

    private void login() {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(   new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    progressDialog=new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Login");
                    progressDialog.setMessage("Please wait...");

                    progressDialog.show();

                    Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
      //              sp = getSharedPreferences("user",0);
        //            editor= sp.edit();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot d : dataSnapshot.getChildren()){
                                pojo = d.getValue(MyPojo.class);
                                if(email.equals(pojo.getEmail()) && password.equals(pojo.getPassword())){
                                   // Log.e("1234", "onDataChange: "+email+"..."+pojo.getMobileNumber()+"..."+pojo.getName() );

                                    SharedPreferences sp=getSharedPreferences("user",0);
                                    SharedPreferences.Editor editor=sp.edit();
                                    editor.putString("email",email);
                                    editor.putString("phone",pojo.getMobileNumber());
                                    editor.putString("address",pojo.getAddress());
                                    editor.putString("name",pojo.getName());
                                    editor.putBoolean("flag",true);
                                    editor.commit();
                                   // Log.e("1234", "Vaues...: "+ sp.getAll() );
                                }//end of if....
                            }//end of for loop.....
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    /*databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot d : dataSnapshot.getChildren()){
                                MyPojo pojo = new MyPojo();
                                pojo = d.getValue(MyPojo.class);
                                if(email.equals(pojo.getEmail()) && password.equals(pojo.getPassword())){
                                    editor.putString("email",email);
                                    editor.putString("phone",pojo.getMobileNumber());
                                    editor.putString("address",pojo.getAddress());
                                    editor.putString("name",pojo.getName());
                                    editor.putBoolean("flag",true);
                                    editor.commit();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/

                    Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                    Common.currentUser=myPojo;
                    startActivity(intent);
                    finish();
                    }
                    else {

                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
