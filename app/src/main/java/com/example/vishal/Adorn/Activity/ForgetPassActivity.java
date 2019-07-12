package com.example.vishal.Adorn.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.vishal.Adorn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassActivity extends AppCompatActivity {

    EditText editEmail;
    Button btnEmail;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        editEmail=(EditText) findViewById(R.id.editEmail);
        btnEmail=(Button) findViewById(R.id.btnEmail);
        mAuth=FirebaseAuth.getInstance();

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resetEmail=editEmail.getText().toString();

                if(TextUtils.isEmpty(resetEmail)){
                    Toast.makeText(ForgetPassActivity.this,"Please enter valid email address",Toast.LENGTH_LONG).show();
                }
                else {
                    mAuth.sendPasswordResetEmail(resetEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(ForgetPassActivity.this,"Please check your email account",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgetPassActivity.this,LoginActivity.class));
                            }
                            else {
                                String message=task.getException().getMessage();
                                Toast.makeText(ForgetPassActivity.this,"Something Error"+message,Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
