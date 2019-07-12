package com.example.vishal.Adorn.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.vishal.Adorn.MyPojo.FeedbackPojo;
import com.example.vishal.Adorn.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackActivity extends AppCompatActivity {

    RadioGroup radio1,radio2,radio3,radio4;
    RadioButton b1,n1,g1,v1;
    RadioButton b2,n2,g2,v2;
    RadioButton b3,n3,g3,v3;
    RadioButton yes,no;
    EditText txtfeedback;
    Button submit;
   FeedbackPojo feedbackPojo;
    SharedPreferences sp;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        sp=getSharedPreferences("user",0);
        final String email=sp.getString("email",null);


        radio1 = (RadioGroup)findViewById(R.id.radio1);
        radio2 = (RadioGroup)findViewById(R.id.radio2);
        radio3 = (RadioGroup)findViewById(R.id.radio3);
        radio4 = (RadioGroup)findViewById(R.id.radio4);
        b1=(RadioButton) findViewById(R.id.b1);
        n1=(RadioButton)findViewById(R.id.n1);
        g1=(RadioButton)findViewById(R.id.g1);
        v1=(RadioButton)findViewById(R.id.v1);

        b2=(RadioButton) findViewById(R.id.b2);
        n2=(RadioButton)findViewById(R.id.n2);
        g2=(RadioButton)findViewById(R.id.g2);
        v2=(RadioButton)findViewById(R.id.v2);

        b3=(RadioButton) findViewById(R.id.b3);
        n3=(RadioButton)findViewById(R.id.n3);
        g3=(RadioButton)findViewById(R.id.g3);
        v3=(RadioButton)findViewById(R.id.v3);

        yes=(RadioButton)findViewById(R.id.yes);
        no=(RadioButton)findViewById(R.id.no);
        txtfeedback = (EditText)findViewById(R.id.feedback);
        submit = (Button)findViewById(R.id.submit);

        feedbackPojo = new FeedbackPojo();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Feedback");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exp, step, know,help,feedback;

                feedback=txtfeedback.getText().toString().trim();

                int id = radio1.getCheckedRadioButtonId();
                b1 = findViewById(id);

                if (b1 != null) {
                    if (b1.getText().toString().equals("Bad")) {
                        exp = "Bad";
                       feedbackPojo.setExp(exp);
                    } else if (n1.getText().toString().equals("Normal")) {
                        exp = "Normal";
                        feedbackPojo.setExp(exp);
                    } else if (g1.getText().toString().equals("Good")) {
                        exp = "Good";
                        feedbackPojo.setExp(exp);
                    } else {
                        exp = "Very Good";
                        feedbackPojo.setExp(exp);
                       /* myKojo.setExp(exp);*/
                    }
                }

                int id2 = radio2.getCheckedRadioButtonId();
                b2 = findViewById(id2);

                if (b2 != null) {
                    if (b2.getText().toString().equals("Bad")) {
                        step = "Bad";
                        feedbackPojo.setStep(step);
                    } else if (n2.getText().toString().equals("Normal")) {
                        step = "Normal";
                        feedbackPojo.setStep(step);
                    } else if (g2.getText().toString().equals("Good")) {
                        step = "Good";
                        feedbackPojo.setStep(step);
                    } else {
                        step = "Very Good";
                        feedbackPojo.setStep(step);
                    }
                }

                int id3 = radio3.getCheckedRadioButtonId();
                b3 = findViewById(id3);

                if (b3 != null) {
                    if (b3.getText().toString().equals("Extremely")) {
                        know = "Extremely";
                        feedbackPojo.setKnow(know);
                    } else if (n3.getText().toString().equals("Quite")) {
                        know = "Quite";
                        feedbackPojo.setKnow(know);
                    } else if (g3.getText().toString().equals("Slightly")) {
                        know = "Slightly";
                        feedbackPojo.setKnow(know);
                    } else {
                        know = "Not At All";
                        feedbackPojo.setKnow(know);
                    }
                }

                int id4=radio4.getCheckedRadioButtonId();
                yes=findViewById(id4);
                if(yes!=null)
                {
                    if(yes.getText().toString().equals("YES")){
                        help="YES";
                        feedbackPojo.setHelp(help);
                    }
                    else {
                        help="NO";
                        feedbackPojo.setHelp(help);
                    }
                }

                feedbackPojo.setFeedback(feedback);
                feedbackPojo.setEmail(email);

                databaseReference.push().setValue(feedbackPojo);
                Toast.makeText(FeedbackActivity.this,"Feedback Submit",Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(FeedbackActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }//ent listner
        });//end submit

    }//end on create
}//end of class


/*
package com.example.vishal.Adorn.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vishal.Adorn.R;

public class FeedbackActivity extends AppCompatActivity {

    private EditText t1,t2,t3;
    Button btnn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        */
/*t1 = (EditText) findViewById(R.id.emailFeedback);
        t2 = (EditText) findViewById(R.id.subject);
        t3 = (EditText) findViewById(R.id.message);
        btnn = (Button) findViewById(R.id.b1);
*//*

        */
/*btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = t1.getText().toString();
                String subject = t2.getText().toString();
                String message = t3.getText().toString();
   *//*
*/
/*
                String[] email = ouremail.split(",");
                Intent Send = new Intent(Intent.ACTION_SENDTO);
                Send.putExtra(Intent.EXTRA_EMAIL, feedback);
                Send.putExtra(Intent.EXTRA_SUBJECT,message );
                Send.putExtra(Intent.EXTRA_TEXT,ouremail );
                Send.setType("message/rfc822");
                Send.setPackage("com.google.android.gm");
                startActivity(Send);
  *//*
*/
/*
                SharedPreferences sp = getSharedPreferences("user",0);
                String getemail = sp.getString("email",null);

                if(getemail != null){
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",email, null));
                    Log.e("123456", "onClick: "+getemail );
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }

            }
        });*//*

    }//end of onCreate()....
}//end of class....
*/
