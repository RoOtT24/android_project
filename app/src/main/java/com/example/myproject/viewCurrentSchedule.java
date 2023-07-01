package com.example.myproject;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class viewCurrentSchedule extends Fragment {
    EditText email ;
    TextView courses;
    DataBaseHelper dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);

    public viewCurrentSchedule() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        TextView coursesTextView = view.findViewById(R.id.emailSechedule);
        email = view.findViewById(R.id.coursesSechedule);
        String instructorEmail = email.getText().toString().trim();
        Cursor cursor = dbHelper.getCurrentScheduleByInstructor(instructorEmail);

        StringBuilder coursesBuilder = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int courseId = cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_USER_COURSEID));
                @SuppressLint("Range")String courseSchedule = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_COURSE_SCHEDULE));

                Courses course = dbHelper.getCourseById(courseId);

                if (course != null) {
                    String courseTitle = course.getTitle();

                    coursesBuilder.append(courseTitle).append(": ").append(courseSchedule).append("\n");
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        // Set the course titles and schedules in the TextView
        coursesTextView.setText(coursesBuilder.toString());

        return view;
    }}