package com.example.myproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Enroll extends Fragment {

    private TextView textViewTitle;
    private EditText emailEditText;
    private EditText courseTitleEditText;
   // private EditText scheduleEditText;
    private Button enrollButton;
  //  private TextView scheduleTextView;

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
        textViewTitle = view.findViewById(R.id.textViewTitle);
        emailEditText = view.findViewById(R.id.Email);
        courseTitleEditText = view.findViewById(R.id.email);
        //scheduleEditText = view.findViewById(R.id.Schedule);
        enrollButton = view.findViewById(R.id.Enroll_Button);
       // scheduleTextView = view.findViewById(R.id.textView2);

        // Set up the enrollButton click listener
        enrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered values
                DataBaseHelper dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);
                String email = emailEditText.getText().toString();
                String courseTitle = courseTitleEditText.getText().toString();
                //String schedule = scheduleEditText.getText().toString();

                // Perform enrollment logic and save to the database
                if ( dbHelper.enrollStudentInCourse( email,  courseTitle)){
                    Toast.makeText(getContext(), "Enroll added successfully", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Error in enroll adding process", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


}
