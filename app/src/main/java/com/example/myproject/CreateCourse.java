package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateCourse extends AppCompatActivity {
    private DataBaseHelper dbHelper = new DataBaseHelper(CreateCourse.this, "DATABASE", null, 1);
    private EditText editTextTitle;
    private EditText editTextMainTopics;
    private EditText editTextPrerequisites;
    private EditText editTextPhotoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        // Find the EditText fields
        editTextTitle = findViewById(R.id.Title);
        editTextMainTopics = findViewById(R.id.topices);
        editTextPrerequisites = findViewById(R.id.Prerequisites);
        editTextPhotoUrl = findViewById(R.id.Photo);

        // Find the Create Course button
        Button buttonCreateCourse = findViewById(R.id.ceate_course);
        buttonCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCourse();
            }
        });
    }

    private void createCourse() {
        // Retrieve the input values from the EditText fields
        String title = editTextTitle.getText().toString();
        String mainTopics = editTextMainTopics.getText().toString();
        String prerequisites = editTextPrerequisites.getText().toString();
        String photoUrl = editTextPhotoUrl.getText().toString();

        // Create a new course object using the input values
        Courses newCourse = new Courses(title, convertStringToArray(mainTopics), convertStringToArray(prerequisites), photoUrl);

        dbHelper.insertCourse(newCourse);
        Toast.makeText(this, "Course created successfully", Toast.LENGTH_SHORT).show();
        dbHelper.close();

    }
    private String[] convertStringToArray(String string) {
        return string.split(", ");
    }
}