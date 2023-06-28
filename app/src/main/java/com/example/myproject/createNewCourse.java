package com.example.myproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link createNewCourse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class createNewCourse extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private DataBaseHelper dbHelper = new DataBaseHelper(createNewCourse.this, "DATABASE", null, 1);
    private EditText editTextTitle;
    private EditText editTextMainTopics;
    private EditText editTextPrerequisites;
    private EditText editTextPhotoUrl;

    public createNewCourse() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment createNewCourse.
     */
    // TODO: Rename and change types and number of parameters
    public static createNewCourse newInstance(String param1, String param2) {
        createNewCourse fragment = new createNewCourse();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Find the EditText fields
        editTextTitle = getActivity().findViewById(R.id.Title);
        editTextMainTopics = getActivity().findViewById(R.id.topices);
        editTextPrerequisites = getActivity().findViewById(R.id.Prerequisites);
        editTextPhotoUrl = getActivity().findViewById(R.id.Photo);

        // Find the Create Course button
        Button buttonCreateCourse = getActivity().findViewById(R.id.Update);
        buttonCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCourse();
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
        String photoUrl = editTextPhotoUrl.getText().toString();

        // Create a new course object using the input values
        Courses newCourse = new Courses(title, convertStringToArray(mainTopics), convertStringToArray(prerequisites), photoUrl);

//        dbHelper.insertCourse(newCourse);
//        Toast.makeText(this, "Course created successfully", Toast.LENGTH_SHORT).show();
//        dbHelper.close();

    }
    private String[] convertStringToArray(String string) {
        return string.split(", ");
    }
}