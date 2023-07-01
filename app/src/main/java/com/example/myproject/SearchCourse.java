package com.example.myproject;

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

public class SearchCourse extends Fragment {
    private TextView textViewTitle;
    private Button searchButton;
    private Spinner spinner;
    private TextView courseOffried;
    List<String> courseTitles;
    DataBaseHelper dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);

    public SearchCourse() {
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
        textViewTitle = view.findViewById(R.id.textViewTitle);
        searchButton = view.findViewById(R.id.search_Button);
        courseOffried = view.findViewById(R.id.courseOffried);
        courseTitles = new ArrayList<>();
        spinner = view.findViewById(R.id.spinner);
        refreshSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedCourse = courseTitles.get(position);
                // Retrieve course data from the database based on the selected course title
                Courses courseData = dbHelper.getCourseData(selectedCourse);
                if (courseData != null) {
                    int courseid = courseData.getId();
                    displayCourseDetails(courseid);
                }
                else {
                    Toast.makeText(getActivity(), "No course found", Toast.LENGTH_SHORT).show();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
                courseOffried.setText(" ");
            }
        });

       return view;
    }
    private void displayCourseDetails(int courseId) {
        Courses course = dbHelper.getCourseById(courseId);
        Offer offer = dbHelper.getCourseofferById(courseId);

        if (course != null && offer!=null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Course ID: ").append(course.getId()).append("\n");
            stringBuilder.append("Title: ").append(course.getTitle()).append("\n");
            stringBuilder.append("Main Topics: ").append(course.getMainTopics()).append("\n");
            stringBuilder.append("Prerequisites: ").append(course.getPrerequisites()).append("\n");
            stringBuilder.append("DeadLine: ").append(offer.getRegistrationDeadline()).append("\n");
            stringBuilder.append("Start Date: ").append(offer.getCourseStartDate()).append("\n");
            stringBuilder.append("Schedule: ").append(offer.getCourseSchedule()).append("\n");
            stringBuilder.append("Venue: ").append(offer.getVenue()).append("\n");
            courseOffried.setText(stringBuilder.toString());
        } else {
            courseOffried.setText("Course not found");
        }
    }

    private void refreshSpinner() {
        courseTitles.clear();
        List<Courses> courses = dbHelper.getOfferedCourses();
       courseTitles = offeredCourses(courses);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, courseTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
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

    private ArrayList<String> offeredCourses(List<Courses> courses){
        ArrayList<String> courseTitles = new ArrayList<>();

        for (Courses course : courses) {
            String title = course.getTitle();
            courseTitles.add(title);
        }

        return courseTitles;
    }
}