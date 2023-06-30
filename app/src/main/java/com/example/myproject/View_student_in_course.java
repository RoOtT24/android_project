package com.example.myproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;


public class View_student_in_course extends Fragment {

    private DataBaseHelper dbHelper;
    private Spinner courseSpinner;
    private ListView studentListView;
    private ArrayAdapter<String> courseAdapter;
    private SimpleCursorAdapter studentAdapter;

    public View_student_in_course() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static View_student_in_course newInstance(String param1, String param2) {
        View_student_in_course fragment = new View_student_in_course();
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
        View view = inflater.inflate(R.layout.fragment_view_student_in_course, container, false);


        dbHelper = new DataBaseHelper( getActivity().getBaseContext(), "DATABASE", null , 1);
        courseSpinner = view.findViewById(R.id.courseSpinner);
        studentListView = view.findViewById(R.id.studentListView);

        // Set up the course spinner
        List<String> courseList = dbHelper.getAllCourses();
        courseAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, courseList);
        courseSpinner.setAdapter(courseAdapter);
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCourse = parent.getItemAtPosition(position).toString();
                int courseId = dbHelper.getCourseId(selectedCourse);
                updateStudentList(courseId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        return view;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_view_student_in_course, container, false);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (studentAdapter != null) {
            studentAdapter.getCursor().close();
        }
    }

    private void updateStudentList(int courseId) {
        Cursor cursor = dbHelper.getStudentsByCourseId(courseId);
        studentAdapter.changeCursor(cursor);
    }

}