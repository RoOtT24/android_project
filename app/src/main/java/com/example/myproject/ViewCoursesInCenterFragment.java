package com.example.myproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class ViewCoursesInCenterFragment extends Fragment {
    TextView history ;
    DataBaseHelper dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);
    public ViewCoursesInCenterFragment() {
        // Required empty public constructor
    }

    public static ViewCoursesInCenterFragment newInstance() {
        ViewCoursesInCenterFragment fragment = new ViewCoursesInCenterFragment();
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
        history = view.findViewById(R.id.coursesSechedule);
        displayCourse();
        return view;
    }
    private void displayCourse() {
        List<Courses> courses = dbHelper.getOfferedCourses();

        if (courses != null && !courses.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();

            for (Courses course : courses) {
                stringBuilder.append("Title: ").append(course.getTitle()).append("\n");
            }

            history.setText(stringBuilder.toString());
        } else {
            history.setText("No courses found");
        }
    }
}