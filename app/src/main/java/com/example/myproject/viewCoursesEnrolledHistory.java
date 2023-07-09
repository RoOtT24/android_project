package com.example.myproject;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link viewCoursesEnrolledHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class viewCoursesEnrolledHistory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public viewCoursesEnrolledHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment viewCoursesEnrolledHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static viewCoursesEnrolledHistory newInstance(String param1, String param2) {
        viewCoursesEnrolledHistory fragment = new viewCoursesEnrolledHistory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_courses_enrolled_history, container, false);
    }


    @SuppressLint("Range")
    private void displayCourseDetails() {
        LinearLayout linearLayout = getActivity().findViewById(R.id.viewAllOfferingsLayout);
        linearLayout.removeAllViews();
        DataBaseHelper dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);
        Bundle extras = getActivity().getIntent().getExtras();
        String email = new String();
        if (extras != null) {
            email = extras.getString("email");
            //The key argument here must match that used in the other activity
        }
        Cursor cursor = dbHelper.getOfferedCoursesHistory(email);

        if (cursor.getCount()>0) {
            while(cursor.moveToNext()){
                LinearLayout ly = new LinearLayout(getActivity());
                ly.setOrientation(LinearLayout.VERTICAL);
                EditText courseNameText = new EditText(getActivity());
                EditText scheduleEditText = new EditText(getActivity());
                EditText instructorText = new EditText(getActivity());
                instructorText.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_FIRST_NAME))+" "+cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_LAST_NAME)));
                scheduleEditText.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_COURSE_SCHEDULE)));
                courseNameText.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_TITLE)));
                ly.addView(instructorText);
                ly.addView(scheduleEditText);
                ly.addView(courseNameText);
                linearLayout.addView(ly);
            }
        } else {
            TextView history = new TextView(getActivity());
            history.setText("No Offerings found");
            linearLayout.addView(history);
        }
        cursor.close();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        displayCourseDetails();
    }
}