package com.example.myproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class InstructorFragment extends Fragment {
    // هون عرفتهم نل كبدايه بس يجب التاكد ان صح او لا؟
    private Instructor instructor = new Instructor(null , null,null,null,null,null,null,null,null);
    public InstructorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instructor, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get references to the buttons in the layout
        Button btnCoursesTaught = view.findViewById(R.id.btnCoursesTaught);
        Button btnCurrentSchedule = view.findViewById(R.id.btnCurrentSchedule);
        Button btnStudentList = view.findViewById(R.id.btnStudentList);
        Button btnProfile = view.findViewById(R.id.btnProfile);

        // Set click listeners for the buttons
        btnCoursesTaught.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCoursesTaught();
            }
        });

        btnCurrentSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCurrentSchedule();
            }
        });

        btnStudentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewStudentList();
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewEditProfile();
            }
        });
    }

    // Function to view the courses previously taught
    private void viewCoursesTaught() {// Get the courses added by the instructor
        String[] courses = instructor.getCourses();

        // Convert the courses array to a single string
        String coursesText = "";
        for (String course : courses) {
            coursesText += course + "\n";
        }

        // Find the TextView in your layout file using its ID
       // TextView coursesTextView = findViewById(R.id.coursesTextView);

        // Set the courses text in the TextView
       // coursesTextView.setText(coursesText);
    }

    // Function to view the instructor's current schedule
    private void viewCurrentSchedule() {
        // Implement the logic to retrieve and display the instructor's current schedule
    }

    // Function to view the list of students in a course taught by the instructor
    private void viewStudentList() {
        // Implement the logic to retrieve and display the list of students in a specific course taught by the instructor
    }

    // Function to view and edit the instructor's profile
    private void viewEditProfile() {
        // Implement the logic to retrieve and display the instructor's profile information
        // Also, provide the functionality to edit and update the profile
    }
}
