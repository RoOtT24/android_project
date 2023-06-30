package com.example.myproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
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
        Cursor cursor = dbHelper.getOfferingStudents(courseId);
//        studentAdapter.changeCursor(cursor);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        dbHelper = new DataBaseHelper( getActivity().getBaseContext(), "DATABASE", null , 1);
        courseSpinner = getActivity().findViewById(R.id.courseSpinner);
        studentListView = getActivity().findViewById(R.id.studentListView);

        // Set up the course spinner
        Cursor cursor = dbHelper.getAllCourses();
        ArrayList<Courses> courses = new ArrayList<>();
        ArrayList<String> courses_names = new ArrayList<>();


        int COLUMN_ID_INDEX = cursor.getColumnIndex("courseId");
        int COLUMN_TITLE_INDEX = cursor.getColumnIndex("TITLE");
        int COLUMN_MAINTOPICES_INDEX = cursor.getColumnIndex("mainTopics");
        int COLUMN_PREEQUISITES_INDEX = cursor.getColumnIndex("prerequisites");
        int COLUMN_IMAGE_INDEX = cursor.getColumnIndex("Image");
        while(cursor.moveToNext()){
            int id = cursor.getInt(COLUMN_ID_INDEX);
            String title = cursor.getString(COLUMN_TITLE_INDEX);
            String []mainTopecs = dbHelper.convertStringToArray(cursor.getString(COLUMN_MAINTOPICES_INDEX));
            String []prequisities = dbHelper.convertStringToArray(cursor.getString(COLUMN_PREEQUISITES_INDEX));
            byte []image = cursor.getBlob(COLUMN_IMAGE_INDEX);
            courses.add(new Courses(id, title, mainTopecs, prequisities,image));
            courses_names.add(new String(title));
        }
        cursor.close();

        courseAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, courses_names);
        courseSpinner.setAdapter(courseAdapter);
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedCourse = parent.getItemAtPosition(position).toString();
                int courseId = courses.get(position).getId();
                updateStudentList(courseId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

    }
}