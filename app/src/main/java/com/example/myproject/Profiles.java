package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Profiles extends AppCompatActivity {

    private ImageView personalImg;
    private TextView nameTextView;
    private TextView typeTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView addressTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        personalImg = findViewById(R.id.img);
        nameTextView = findViewById(R.id.name);
        typeTextView = findViewById(R.id.type);
        emailTextView = findViewById(R.id.emailtext);
        phoneTextView = findViewById(R.id.phonetext);
        addressTextView = findViewById(R.id.addresstext);

        emailTextView.setBackgroundResource(R.drawable.border);
        phoneTextView.setBackgroundResource(R.drawable.border);
        addressTextView.setBackgroundResource(R.drawable.border);
    }
}
