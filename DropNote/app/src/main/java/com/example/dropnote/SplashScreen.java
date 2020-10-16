package com.example.dropnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private TextView txtWebsite;
    private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Initialize
        txtWebsite = (TextView) findViewById(R.id.txtWebsite);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);

        Animation splashTransition = AnimationUtils.loadAnimation(this, R.anim.splashtransition);
        txtWebsite.setAnimation(splashTransition);
        imgLogo.setAnimation(splashTransition);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }, 800);
    }
}