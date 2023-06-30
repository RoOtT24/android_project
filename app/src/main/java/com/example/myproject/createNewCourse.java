package com.example.myproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class createNewCourse extends Fragment {

//    private DataBaseHelper dbHelper = new DataBaseHelper(createNewCourse.this, "DATABASE", null, 1);

    private EditText editTextTitle;
    private EditText editTextMainTopics;
    private EditText editTextPrerequisites;
    private ImageView courseImage;
    ActivityResultLauncher<Intent> activityResultLauncher;

    Bitmap bitmap;

    public createNewCourse() {
        // Required empty public constructor
    }

    public static createNewCourse newInstance() {
        createNewCourse fragment = new createNewCourse();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_new_course, container, false);
    }
    private void createCourse() {
        // Retrieve the input values from the EditText fields
        String title = editTextTitle.getText().toString();
        String mainTopics = editTextMainTopics.getText().toString();
        String prerequisites = editTextPrerequisites.getText().toString();
//        String photoUrl = editTextPhotoUrl.getText().toString();
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

        // Create a new course object using the input values
        Courses newCourse = new Courses(title, convertStringToArray(mainTopics), convertStringToArray(prerequisites), bytes);
        DataBaseHelper db = new DataBaseHelper( getActivity().getBaseContext(), "DATABASE", null , 1);
        Toast.makeText(getActivity(), newCourse.gettitle(), Toast.LENGTH_SHORT).show();
        db.insertCourse(newCourse);

        db.close();
        Toast.makeText(getActivity(), "Course is inserted!", Toast.LENGTH_SHORT).show();

    }
    private String[] convertStringToArray(String string) {
        return string.split(", ");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        // Find the EditText fields
        editTextTitle = getActivity().findViewById(R.id.editTextCourseId);
        editTextMainTopics = getActivity().findViewById(R.id.topices);
        editTextPrerequisites = getActivity().findViewById(R.id.Prerequisites);
        courseImage = getActivity().findViewById(R.id.course_photo);




        courseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });

        // Find the Create Course button
        Button buttonCreateCourse = (Button)getActivity().findViewById(R.id.create_course_button_admin);
        buttonCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextTitle = getActivity().findViewById(R.id.editTextCourseId);
                editTextMainTopics = getActivity().findViewById(R.id.topices);
                editTextPrerequisites = getActivity().findViewById(R.id.Prerequisites);
                courseImage = getActivity().findViewById(R.id.course_photo);
                createCourse();
                Toast.makeText(getContext(), "ALL DONE", Toast.LENGTH_SHORT).show();

            }
        });
    }
}