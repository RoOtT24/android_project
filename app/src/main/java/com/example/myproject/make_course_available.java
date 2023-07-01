package com.example.myproject;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class make_course_available extends Fragment {

    public make_course_available() {
        // Required empty public constructor
    }

    private EditText courseIdEditText;
    private EditText instructorEmailEditText;
    private EditText registrationDeadlineEditText;
    private EditText courseStartDateEditText;
    private EditText courseScheduleEditText;
    private EditText venueEditText;
    private Button addButton;

    private Calendar calendar;
    private DateFormat dateFormat;

    Spinner course_spinner;

    Spinner instructor_spinner;

    ArrayList<Courses> courses = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_course_available, container, false);
        return view;
    }

    private void showDatePicker(final EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String date = dateFormat.format(calendar.getTime());
                        editText.setText(date);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

//    private void addOffer() {
//        int courseId = Integer.parseInt(courseIdEditText.getText().toString());
//        String instructorEmail = instructorEmailEditText.getText().toString();
//        String registrationDeadlineString = registrationDeadlineEditText.getText().toString();
//        String courseStartDateString = courseStartDateEditText.getText().toString();
//        String courseSchedule = courseScheduleEditText.getText().toString();
//        String venue = venueEditText.getText().toString();
//        try {
//            Date registrationDeadline = dateFormat.parse(registrationDeadlineString);
//            Date courseStartDate = dateFormat.parse(courseStartDateString);
//
//            Offer offer = new Offer(courseId, instructorEmail, registrationDeadline, courseStartDate, courseSchedule, venue);
//            addOfferToDatabase(offer);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        Offer offer = new Offer(courseId, instructorEmail, registrationDeadline, courseStartDate, courseSchedule, venue);
////        addOfferToDatabase(offer);
//    }
//
//    private void addOfferToDatabase(Offer offer) {
//        DataBaseHelper dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);
//        dbHelper.addOffer(offer);
//        Toast.makeText(getContext(), "Offer added successfully", Toast.LENGTH_SHORT).show();
//        clearFields();
//    }

    private void clearFields() {
        courseIdEditText.setText("");
        instructorEmailEditText.setText("");
        registrationDeadlineEditText.setText("");
        courseStartDateEditText.setText("");
        courseScheduleEditText.setText("");
        venueEditText.setText("");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        courseIdEditText = getActivity().findViewById(R.id.editTextCourseId);
        instructorEmailEditText = getActivity().findViewById(R.id.editTextInstructorEmail);
        registrationDeadlineEditText = getActivity().findViewById(R.id.editTextRegistrationDeadline);
        courseStartDateEditText = getActivity().findViewById(R.id.editTextCourseStartDate);
        courseScheduleEditText = getActivity().findViewById(R.id.editTextCourseSchedule);
        venueEditText = getActivity().findViewById(R.id.editTextVenue);
        addButton = getActivity().findViewById(R.id.buttonAdd);
        course_spinner = getActivity().findViewById(R.id.courses_spinner_available);
        instructor_spinner = getActivity().findViewById(R.id.instructor_spinner_available);


        ///////////////////////////////////////////////////////////
        // filling instructors spinner
        DataBaseHelper db = new DataBaseHelper( getActivity().getBaseContext(), "DATABASE", null , 1);
        ArrayList<Instructor> instructors = (ArrayList<Instructor>) db.getAllInstructors();

        ArrayList<String> instructorEmails = new ArrayList<>();
        for (int i = 0; i < instructors.size(); ++i)
            instructorEmails.add(instructors.get(i).getEmail());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, instructorEmails);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instructor_spinner.setAdapter(adapter);
        ///////////////////////////////////////////////////////////////////////////////////////////
        courses.clear();

        ArrayList<String> courses_names = new ArrayList<>();
        Cursor cursor = db.getAllCourses();
        int COLUMN_ID_INDEX = cursor.getColumnIndex("courseId");
        int COLUMN_TITLE_INDEX = cursor.getColumnIndex("TITLE");
        int COLUMN_MAINTOPICES_INDEX = cursor.getColumnIndex("mainTopics");
        int COLUMN_PREEQUISITES_INDEX = cursor.getColumnIndex("prerequisites");
        int COLUMN_IMAGE_INDEX = cursor.getColumnIndex("Image");
        while(cursor.moveToNext()){
            int id = cursor.getInt(COLUMN_ID_INDEX);
            String title = cursor.getString(COLUMN_TITLE_INDEX);
            String []mainTopecs = db.convertStringToArray(cursor.getString(COLUMN_MAINTOPICES_INDEX));
            String []prequisities = db.convertStringToArray(cursor.getString(COLUMN_PREEQUISITES_INDEX));
            byte []image = cursor.getBlob(COLUMN_IMAGE_INDEX);
            courses.add(new Courses(id, title, mainTopecs, prequisities,image));
            courses_names.add(new String(title));
        }
        cursor.close();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, courses_names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course_spinner.setAdapter(adapter);

        ///////////////////////////////////////////

        instructor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                instructorEmailEditText = getActivity().findViewById(R.id.editTextInstructorEmail);
                instructorEmailEditText.setText(instructorEmails.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                instructorEmailEditText = getActivity().findViewById(R.id.editTextInstructorEmail);
                instructorEmailEditText.setText("");
            }
        });

        course_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                courseIdEditText = getActivity().findViewById(R.id.editTextCourseId);
//                Toast.makeText(getActivity(), Integer.toString(courses.get(i).getId()), Toast.LENGTH_SHORT).show();

                courseIdEditText.setText(Integer.toString(courses.get(i).getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                courseIdEditText = getActivity().findViewById(R.id.editTextCourseId);
                courseIdEditText.setText("");
            }
        });


        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        registrationDeadlineEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(registrationDeadlineEditText);
            }
        });

        courseStartDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(courseStartDateEditText);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseIdEditText = getActivity().findViewById(R.id.editTextCourseId);
                instructorEmailEditText = getActivity().findViewById(R.id.editTextInstructorEmail);
                registrationDeadlineEditText = getActivity().findViewById(R.id.editTextRegistrationDeadline);
                courseStartDateEditText = getActivity().findViewById(R.id.editTextCourseStartDate);
                courseScheduleEditText = getActivity().findViewById(R.id.editTextCourseSchedule);
                venueEditText = getActivity().findViewById(R.id.editTextVenue);
                addButton = getActivity().findViewById(R.id.buttonAdd);
                course_spinner = getActivity().findViewById(R.id.courses_spinner_available);
                instructor_spinner = getActivity().findViewById(R.id.instructor_spinner_available);

                int id = -1;
                for(int i = 0 ; i < courses.size(); ++i)
                    if(courses.get(i).getId() == Integer.parseInt(courseIdEditText.getText().toString())){
                        id = courses.get(i).getId();
                        break;
                    }

                    try {
                        Date deadline = dateFormat.parse(registrationDeadlineEditText.getText().toString()) ;
                        Date startDate = dateFormat.parse(courseStartDateEditText.getText().toString());


                    String schedule = courseScheduleEditText.getText().toString().trim();
                    String venue = venueEditText.getText().toString().trim();
                Offer offer = new Offer(id, instructorEmailEditText.getText().toString(), deadline, startDate, schedule, venue);


                db.addOffer(offer);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
            }
        });
    }
}