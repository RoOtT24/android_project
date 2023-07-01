package com.example.myproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;


public class ViewHistoryCourses extends Fragment {
    TextView history ;
    DataBaseHelper dbHelper ;

    public ViewHistoryCourses() {
        // Required empty public constructor
    }

    public static ViewHistoryCourses newInstance() {
        ViewHistoryCourses fragment = new ViewHistoryCourses();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);

        return view;
    }
    private void displayCourseDetails() {
        dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);
        List<Courses> courses = dbHelper.getOfferedCoursesHistory();

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        history = getActivity().findViewById(R.id.coursesEnrolled);
        displayCourseDetails();
    }
}