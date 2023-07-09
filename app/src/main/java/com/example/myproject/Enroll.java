package com.example.myproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class Enroll extends Fragment {

//    private TextView textViewTitle;
//    private EditText emailEditText;
   // private EditText courseTitleEditText;
   // private EditText scheduleEditText;
    private Button enrollButton;
  //  private TextView scheduleTextView;
    Spinner Course_titles;
    Spinner OFFERID;
    DataBaseHelper dbHelper;
    public Enroll() {
        // Required empty public constructor
    }

    public static Enroll newInstance() {
        Enroll fragment = new Enroll();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enroll, container, false);

        // Initialize views
       // courseTitleEditText = view.findViewById(R.id.emailSechedule);
        //scheduleEditText = view.findViewById(R.id.Schedule);
//        textViewTitle = view.findViewById(R.id.textViewTitle);
//        emailEditText = view.findViewById(R.id.Email);


        enrollButton = view.findViewById(R.id.Enroll_Button);
       OFFERID = view.findViewById(R.id.OfferID);
        ////////spinner
        Course_titles = view.findViewById(R.id.Title);
        dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);
        ArrayList<Courses> courses = (ArrayList<Courses>) dbHelper.getOfferedCourses();

        ArrayList<String> Offering_courses = new ArrayList<>();
        for (int i = 0; i < courses.size(); ++i)
            Offering_courses.add(courses.get(i).gettitle());

        ArrayAdapter<String> adaptere = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, Offering_courses);
        adaptere.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Course_titles.setAdapter(adaptere);
        /////////////////////////////////////////////////
        Course_titles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Retrieve the selected course title
                String selectedCourseTitle = Course_titles.getSelectedItem().toString();

                // Retrieve offering IDs based on the selected course title
                ArrayList<Integer> offeringIDs = dbHelper.getOfferingIDsByCourseTitle(selectedCourseTitle);

                // Create the ArrayAdapter for OFFERID spinner using offeringIDs
                ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, offeringIDs);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                OFFERID.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle the case when no course title is selected
            }
        });

// ...

// Inside enrollButton click listener
        enrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered values
                Bundle extras = getActivity().getIntent().getExtras();
                String email = new String();
                if (extras != null) {
                    email = extras.getString("email");
                    //The key argument here must match that used in the other activity
                }
                String selectedCourseTitle = Course_titles.getSelectedItem().toString();
                int selectedOfferingID = (int) OFFERID.getSelectedItem();

                // Perform enrollment logic and save to the database
                dbHelper.enrollStudentInCourse(email, selectedCourseTitle, selectedOfferingID);
                // Get the start date of the course

                Offer offer  = dbHelper.getCourseOfferByofferId(selectedOfferingID); // Implement this method in dbHelper to retrieve the start date
                long startDate = offer.getCourseStartDate().getTime();
                // Schedule a notification for the start date of the course, passing the email
                scheduleNotification(email, startDate);

                Toast.makeText(getContext(), "Enroll added successfully", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    private void scheduleNotification(String email, long startDate) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        // Create an explicit intent for the notification receiver
        Intent intent = new Intent(getActivity(), NotificationReceiver.class);
        intent.putExtra("notification_title", "Course Reminder");
        intent.putExtra("notification_message", "Your course is starting today!");
        intent.putExtra("notification_email", email);
        // Set a unique request code for the pending intent
        int requestCode = 12345;

        // Create a pending intent with the notification receiver intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the start date as the trigger time for the alarm
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, startDate, pendingIntent);
    }


}
