package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class WhoAreYou extends AppCompatActivity {

    Button Adminbtn ;
    Button Studentbtn ;
    Button Trainerbtn ;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_choosesignup);

          Adminbtn = findViewById(R.id.Adminbtn);
          Studentbtn =findViewById(R.id.Studentbtn);
          Trainerbtn=findViewById(R.id.Trainerbtn);

          Adminbtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  Intent intent = new Intent(WhoAreYou.this,SignUpAdmin.class);
                  startActivity(intent);
                  finish();

              }
          });

          Studentbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(WhoAreYou.this,SignupActivity.class);
                    startActivity(intent);
                    finish();

                }
            });

          Trainerbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(WhoAreYou.this,SignUpInstructor.class);
                    startActivity(intent);
                    finish();

                }
            });




        }
        }
