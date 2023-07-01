package com.example.myproject;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class viewStudentAtInstroctorCourses extends Fragment {

    private Button searchStudent;
    private Spinner spinnerCourses2;

    private EditText emailInstructor ;
    private TextView Student ;

    List<String> courseTitles;
    DataBaseHelper dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);

    public viewStudentAtInstroctorCourses() {
        // Required empty public constructor
    }

    public static SearchCourse newInstance() {
        SearchCourse fragment = new SearchCourse();
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
        View view = inflater.inflate(R.layout.fragment_search_course, container, false);

        searchStudent = view.findViewById(R.id.searchStudent);
        Student= view.findViewById(R.id.Student);
        emailInstructor = view.findViewById(R.id.emailInstructor);
        courseTitles = new ArrayList<>();
        spinnerCourses2 = view.findViewById(R.id.spinnerCourses2);
       // refreshSpinner();
        spinnerCourses2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String e = emailInstructor.getText().toString().trim();
                Cursor cursor = dbHelper.getAllCoursesByInstructor(e);
                int courseId = 0;
                int index = cursor.getColumnIndex(dbHelper.COLUMN_USER_COURSEID);

                if (index >= 0 && cursor.moveToFirst()) {
                    courseId = cursor.getInt(index);
                    Courses course = dbHelper.getCourseById(courseId);
                    String courseTitle = course.getTitle();
                    courseTitles.add(courseTitle);
                    String selectedCourse = courseTitles.get(position);


                    displayCourseStudent(courseId);


                }
                cursor.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Student.setText("");
            }
    });


        return view;

        }


        private void displayCourseStudent(int courseId) {
        Cursor cursor = dbHelper.getStudentsByCourseId(courseId);

                if (cursor != null ) {
                    StringBuilder studentBuilder = new StringBuilder("Enrolled Students:\n");
                    int FirstName = cursor.getColumnIndex(dbHelper.COLUMN_USER_FIRST_NAME);
                    int LastName = cursor.getColumnIndex(dbHelper.COLUMN_USER_FIRST_NAME);

                    do {
                        String studentFirstName = cursor.getString(FirstName);
                        String studentLastName = cursor.getString(LastName);

                        studentBuilder.append(studentFirstName).append("").append(studentLastName).append("\n");
                    } while (cursor.moveToNext());

                    // Display or store the student information as needed
                    // For example:
                    Student.setText(studentBuilder.toString());
                } else {
                    Student.setText("No students enrolled for this course");
                }
            }



//        private void refreshSpinner() {
//        courseTitles.clear();
//        List<Courses> courses = dbHelper.getOfferedCourses();
//        courseTitles = offeredCourses(courses);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, courseTitles);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//    }
   /* private String convertArrayToString(String[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            stringBuilder.append(array[i]);
            if (i < array.length - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }*/

//    private ArrayList<String> offeredCourses(List<Courses> courses){
//        ArrayList<String> courseTitles = new ArrayList<>();
//
//        for (Courses course : courses) {
//            String title = course.getTitle();
//            courseTitles.add(title);
//        }
//
//        return courseTitles;
//    }
}