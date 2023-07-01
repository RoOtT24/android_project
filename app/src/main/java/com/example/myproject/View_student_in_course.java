package com.example.myproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    private Cursor updateStudentList(String courseName) {
        return dbHelper.getOfferingStudents(courseName);
//        studentAdapter.changeCursor(cursor);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showAll();

    }
    public void showAll(){
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
        LinearLayout linearLayout = getActivity().findViewById(R.id.student_linear_layout_ViewInCourse);
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
//                int courseId = courses.get(position).getId();
//
                showAll();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        if(courseSpinner.getSelectedItem() != null) {
        cursor = updateStudentList(courseSpinner.getSelectedItem().toString());
        while(cursor.moveToNext()){
            LinearLayout vertical = new LinearLayout(getActivity());
            vertical.setOrientation(LinearLayout.VERTICAL);
            LinearLayout ly = new LinearLayout(getActivity());
            ly.setOrientation(LinearLayout.HORIZONTAL);

            TextView name_text = new TextView(getActivity());
            TextView email_text = new TextView(getActivity());
            TextView phone_text = new TextView(getActivity());
            TextView address_text = new TextView(getActivity());
            ImageView iv = new ImageView(getActivity());
            Toast.makeText(getActivity(),cursor.getString(0) , Toast.LENGTH_SHORT).show();
//            name_text.setText(cursor.getString(0)+" "+cursor.getString(1));
//            email_text.setText(cursor.getString(2));
//            phone_text.setText(cursor.getString(3));
//            address_text.setText(cursor.getString(4));
//            iv.setImageBitmap(BitmapFactory.decodeByteArray(cursor.getBlob(6), 0 , cursor.getBlob(6).length));
            iv.getLayoutParams().height = 300;
            iv.getLayoutParams().width = 300;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(100, 0 , 50, 50);
            name_text.setLayoutParams(params);
            ly.addView(name_text);
            ly.addView(iv);
            vertical.addView(ly);
            LinearLayout ly2 = new LinearLayout(getActivity());
            params.setMargins(100, 100, 50, 50);
            email_text.setLayoutParams(params);
            ly2.addView(email_text);
            ly2.addView(phone_text);
            ly2.addView(address_text);
            vertical.addView(ly2);

        }
    }}
}