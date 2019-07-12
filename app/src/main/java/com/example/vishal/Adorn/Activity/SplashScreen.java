package com.example.vishal.Adorn.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.vishal.Adorn.R;

public class SplashScreen extends AppCompatActivity {

    ImageView imageLogo;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageLogo=(ImageView) findViewById(R.id.imageLogo);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp=getSharedPreferences("user",0);
                boolean flag=sp.getBoolean("flag",false);
                if(flag) {
                    Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }//end of if......
                else
                {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }//end of else......
            }
        },3000);
    }
}
