package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView logo = (ImageView) findViewById(R.id.logo);


        Animation a = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim);
        logo.startAnimation(a);

        Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                logo.setVisibility(View.VISIBLE);
                forLogin();
            }
        },3000);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setIcon(R.mipmap.ic_launcher_round);
    }


    public void forLogin() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void forSignup(View view) {
        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
    }
}