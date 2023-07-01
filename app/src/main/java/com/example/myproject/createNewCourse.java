package com.example.myproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class createNewCourse extends Fragment {

//    private DataBaseHelper dbHelper = new DataBaseHelper(createNewCourse.this, "DATABASE", null, 1);

    private EditText editTextTitle;
    private EditText editTextMainTopics;
    private EditText editTextPrerequisites;
    private EditText editTextPhotoUrl;

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
        String photoUrl = editTextPhotoUrl.getText().toString();

        // Create a new course object using the input values
        Courses newCourse = new Courses(title, convertStringToArray(mainTopics), convertStringToArray(prerequisites), photoUrl);
        DataBaseHelper db = new DataBaseHelper( getActivity().getBaseContext(), "DATABASE", null , 1);

        db.insertCourse(newCourse);

        db.close();

    }
    private String[] convertStringToArray(String string) {
        return string.split(", ");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        // Find the EditText fields
        editTextTitle = getActivity().findViewById(R.id.Title);
        editTextMainTopics = getActivity().findViewById(R.id.Schedule);
        editTextPrerequisites = getActivity().findViewById(R.id.Prerequisites);
        editTextPhotoUrl = getActivity().findViewById(R.id.Photo);

        // Find the Create Course button
        Button buttonCreateCourse = (Button)getActivity().findViewById(R.id.search_Button);
        buttonCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCourse();
                Toast.makeText(getContext(), "ALL DONE", Toast.LENGTH_SHORT).show();

            }
        });
    }
}