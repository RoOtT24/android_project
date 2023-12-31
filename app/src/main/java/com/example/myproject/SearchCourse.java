package com.example.myproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
        spinner = getActivity().findViewById(R.id.spinnerCourseSearch);
        courseTitles.clear();
        List<Courses> courses = dbHelper.getOfferedCourses();
        courseTitles = offeredCourses(courses);
        if(courseTitles.size() == 0)
            courseTitles.add("no courses available");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, courseTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    private ArrayList<String> offeredCourses(List<Courses> courses){
        ArrayList<String> courseTitles = new ArrayList<>();

        for (Courses course : courses) {
            String title = course.getTitle();
            courseTitles.add(title);
            //Toast.makeText(getActivity(), course.getTitle(), Toast.LENGTH_SHORT).show();
        }

        return courseTitles;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = new DataBaseHelper(getActivity().getBaseContext(), "db", null, 1);

        textViewTitle = getActivity().findViewById(R.id.textViewTitle);
        searchButton = getActivity().findViewById(R.id.search_Button);
        courseOffried = getActivity().findViewById(R.id.courseOffried);
        courseTitles = new ArrayList<>();
        spinner = getActivity().findViewById(R.id.spinnerCourseSearch);
       refreshSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedCourse = courseTitles.get(position);
                // Retrieve course data from the database based on the selected course title
                Courses courseData = dbHelper.getCourseData(selectedCourse);
                if (courseData != null) {
                    int courseId = courseData.getId();
                    displayCourseDetails(courseId);
                }
                else {
                    Toast.makeText(getActivity(), "No course found", Toast.LENGTH_SHORT).show();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
                courseOffried.setText(" ");
            }
        });

    }
}