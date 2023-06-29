package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Edit_DeleteCourse extends AppCompatActivity {
    Spinner courseSpinner;
    ArrayList<String> courseTitles = new ArrayList<>();
     DataBaseHelper dbHelper;
     EditText editTextTitle;
     EditText editTextMainTopics;
     EditText editTextPrerequisites;
     EditText editTextPhotoUrl;
     Button update;
     Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_course);

        dbHelper = new DataBaseHelper(Edit_DeleteCourse.this, "DATABASE", null, 1);

        // Initialize the Spinner and ArrayList
        courseSpinner = findViewById(R.id.courseSpinner);
        courseTitles = new ArrayList<>();

        editTextTitle = findViewById(R.id.Title);
        editTextMainTopics = findViewById(R.id.topices);
        editTextPrerequisites = findViewById(R.id.Prerequisites);
        editTextPhotoUrl = findViewById(R.id.Photo);
        update = findViewById(R.id.ceate_course);
        delete = findViewById(R.id.Delete);

        // Retrieve course titles from the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DataBaseHelper.COLUMN_USER_TITLE + " FROM " + DataBaseHelper.TABLE_COURSES, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_TITLE);
            if (columnIndex >= 0) {
                do {
                    String title = cursor.getString(columnIndex);
                    courseTitles.add(title);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();

        // Set the adapter for the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(adapter);


        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCourse = courseTitles.get(position);
                // Retrieve course data from the database based on the selected course title
                Courses courseData = dbHelper.getCourseData(selectedCourse);
                if (courseData != null) {
                    String topices = convertArrayToString(courseData.getMainTopics());
                    String Prerequisites = convertArrayToString(courseData.getPrerequisites());
                    // Set the retrieved data to the EditText fields
                    editTextTitle.setText(courseData.gettitle());
                    editTextMainTopics.setText(topices);
                    editTextPrerequisites.setText(Prerequisites);
                    editTextPhotoUrl.setText(courseData.getPhotoUrl());
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //handlig
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the updated values from the EditText fields
                String title = editTextTitle.getText().toString();
                String mainTopics = editTextMainTopics.getText().toString();
                String prerequisites = editTextPrerequisites.getText().toString();
                String photoUrl = editTextPhotoUrl.getText().toString();

                // Update the course data in the database
                Courses courseData = new Courses(title, convertStringToArray(mainTopics), convertStringToArray(prerequisites), photoUrl);
                dbHelper.updateCourseData(courseData);

                // Show a success message or perform any other necessary actions
                Toast.makeText(Edit_DeleteCourse.this, "Course updated successfully", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected course title
                String selectedCourse = courseSpinner.getSelectedItem().toString();

                // Delete the course from the database
                dbHelper.deleteCourse(selectedCourse);

                // Show a success message or perform any other necessary actions
                Toast.makeText(Edit_DeleteCourse.this, "Course deleted successfully", Toast.LENGTH_SHORT).show();

                // Optionally, you can refresh the spinner or navigate back to a different activity
                // Refresh the spinner
                refreshSpinner();

                // Navigate back to a different activity
                Intent intent = new Intent(Edit_DeleteCourse.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void refreshSpinner() {
        courseTitles.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DataBaseHelper.COLUMN_USER_TITLE + " FROM " + DataBaseHelper.TABLE_COURSES, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_TITLE);
            if (columnIndex >= 0) {
                do {
                    String title = cursor.getString(columnIndex);
                    courseTitles.add(title);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(adapter);
    }


    private String convertArrayToString(String[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            stringBuilder.append(array[i]);
            if (i < array.length - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    public String[] convertStringToArray(String str) {
        String[] array = str.split(",");
        return array;
    }
}