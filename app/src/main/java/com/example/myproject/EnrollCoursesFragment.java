package com.example.myproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class EnrollCoursesFragment extends Fragment {
TextView Enrolled ;
EditText email ;
    DataBaseHelper dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);

    public EnrollCoursesFragment() {
        // Required empty public constructor
    }

    public static EnrollCoursesFragment newInstance() {
        EnrollCoursesFragment fragment = new EnrollCoursesFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enroll_courses, container, false);
        LinearLayout enrolledLayout = view.findViewById(R.id.courses);
        email = view.findViewById(R.id.Email);
        String e = email.getText().toString().trim();
        List<String> courses = dbHelper.enrollIN(e);

        if (courses != null) {
            for (String course : courses) {
                String title = course;

                // Create a new TextView for each course title
                TextView courseTitleTextView = new TextView(getActivity());
                courseTitleTextView.setText(title);

                // Create a new Withdraw button for each course title
                Button withdrawButton = new Button(getActivity());
                withdrawButton.setText("Withdraw");

                // Declare final courseLayout variable
                final LinearLayout courseLayout = new LinearLayout(getActivity());
                courseLayout.setOrientation(LinearLayout.HORIZONTAL);
                courseLayout.addView(courseTitleTextView);
                courseLayout.addView(withdrawButton);

                withdrawButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle the withdraw button click event
                        dbHelper.deleteEnrollCource(e, title);
                        enrolledLayout.removeView(courseLayout); // Remove the course layout from the enrolledLayout

                        Toast.makeText(getActivity(), "Enrollment for course " + title + " deleted.", Toast.LENGTH_SHORT).show();
                    }
                });

                // Add the course layout to the enrolledLayout LinearLayout
                enrolledLayout.addView(courseLayout);
            }
        }

        return view;
    }


}