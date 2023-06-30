package com.example.myproject;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
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

    private void addOffer() {
        int courseId = Integer.parseInt(courseIdEditText.getText().toString());
        String instructorEmail = instructorEmailEditText.getText().toString();
        String registrationDeadlineString = registrationDeadlineEditText.getText().toString();
        String courseStartDateString = courseStartDateEditText.getText().toString();
        String courseSchedule = courseScheduleEditText.getText().toString();
        String venue = venueEditText.getText().toString();
        try {
            Date registrationDeadline = dateFormat.parse(registrationDeadlineString);
            Date courseStartDate = dateFormat.parse(courseStartDateString);

            Offer offer = new Offer(courseId, instructorEmail, registrationDeadline, courseStartDate, courseSchedule, venue);
            addOfferToDatabase(offer);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Offer offer = new Offer(courseId, instructorEmail, registrationDeadline, courseStartDate, courseSchedule, venue);
//        addOfferToDatabase(offer);
    }

    private void addOfferToDatabase(Offer offer) {
        DataBaseHelper dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);
        dbHelper.addOffer(offer);
        Toast.makeText(getContext(), "Offer added successfully", Toast.LENGTH_SHORT).show();
        clearFields();
    }

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
                addOffer();
            }
        });
    }
}