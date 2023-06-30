package com.example.myproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;




public class edit_delete_course extends Fragment {

    Spinner courseSpinner;
    ArrayList<String> courseTitles = new ArrayList<>();
    DataBaseHelper dbHelper;
    EditText editTextTitle;
    EditText editTextMainTopics;
    EditText editTextPrerequisites;
    ImageView courseImage;
    Button update;
    Button delete;

    Bitmap bitmap;

    ActivityResultLauncher<Intent> activityResultLauncher;

    public edit_delete_course() {
        // Required empty public constructor
    }

    public static edit_delete_course newInstance(String param1, String param2) {
        edit_delete_course fragment = new edit_delete_course();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);


        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK ){
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                        courseImage.setImageBitmap(bitmap);
//                        imageChanged[0] = true;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, courseTitles);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_edit_delete_course, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialize the Spinner and ArrayList
        courseSpinner = getActivity().findViewById(R.id.courseSpinner);
        courseTitles = new ArrayList<>();

        editTextTitle = getActivity().findViewById(R.id.editTextCourseId);
        editTextMainTopics = getActivity().findViewById(R.id.topices);
        editTextPrerequisites = getActivity().findViewById(R.id.Prerequisites);
        courseImage = getActivity().findViewById(R.id.course_Image_on_update_fragment);
        update = getActivity().findViewById(R.id.ceate_course);
        delete = getActivity().findViewById(R.id.Delete);



        courseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, courseTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(adapter);


        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCourse = courseTitles.get(position);
                // Retrieve course data from the database based on the selected course title
                Courses courseData = dbHelper.getCourseData(selectedCourse);
                if (courseData != null) {
                    String topics = convertArrayToString(courseData.getMainTopics());
                    String Prerequisites = convertArrayToString(courseData.getPrerequisites());
                    // Set the retrieved data to the EditText fields
                    editTextTitle.setText(courseData.gettitle());
                    editTextMainTopics.setText(topics);
                    editTextPrerequisites.setText(Prerequisites);
                    courseImage.setImageBitmap(BitmapFactory.decodeByteArray(courseData.getImage(), 0, courseData.getImage().length));
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                editTextTitle.setText(" ");
                editTextMainTopics.setText(" ");
                editTextPrerequisites.setText(" ");
                courseImage.setImageDrawable(null);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the updated values from the EditText fields
                String title = editTextTitle.getText().toString();
                String mainTopics = editTextMainTopics.getText().toString();
                String prerequisites = editTextPrerequisites.getText().toString();
//                String photoUrl = editTextPhotoUrl.getText().toString();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                byte []bytes;
                if(bitmap != null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100,byteArrayOutputStream);
                    bytes = byteArrayOutputStream.toByteArray();
                }
                else{
                    Toast.makeText(getActivity(), "Photo is not inserted, try again!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Update the course data in the database
                Courses courseData = new Courses(title, convertStringToArray(mainTopics), convertStringToArray(prerequisites), bytes);
                dbHelper.updateCourseData(courseData);

                // Show a success message or perform any other necessary actions
                Toast.makeText(getActivity(), "Course updated successfully", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Course deleted successfully", Toast.LENGTH_SHORT).show();

                // Optionally, you can refresh the spinner or navigate back to a different activity
                // Refresh the spinner
                refreshSpinner();

                // Navigate back to a different activity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}