package com.example.myproject;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class viewPreviousCoursesInstructor extends Fragment {
 EditText email ;
 TextView courses;
    DataBaseHelper dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);

    public viewPreviousCoursesInstructor() {
        // Required empty public constructor
    }
    public static viewPreviousCoursesInstructor newInstance() {
        viewPreviousCoursesInstructor fragment = new viewPreviousCoursesInstructor();
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
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        TextView coursesTextView = view.findViewById(R.id.courses);
        email = view.findViewById(R.id.email);
        String e = email.getText().toString().trim();
        Cursor cursor = dbHelper.getPreviousCoursesByInstructor(e);

        StringBuilder coursesBuilder = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int courseId = 0;
                int index = cursor.getColumnIndex(dbHelper.COLUMN_USER_COURSEID);
                if (index >0 )
                    courseId=  cursor.getInt(index);
                Courses course = dbHelper.getCourseById(courseId);
                String courseTitle = course.getTitle();
                coursesBuilder.append(courseTitle).append("\n");
            } while (cursor.moveToNext());
        }

        cursor.close();

        // Set the course titles in the TextView
        coursesTextView.setText(coursesBuilder.toString());

        return view;
    }

}