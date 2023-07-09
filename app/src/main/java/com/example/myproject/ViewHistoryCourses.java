package com.example.myproject;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;


public class ViewHistoryCourses extends Fragment {
    TextView history ;
    DataBaseHelper dbHelper ;

    public ViewHistoryCourses() {
        // Required empty public constructor
    }

    public static ViewHistoryCourses newInstance() {
        ViewHistoryCourses fragment = new ViewHistoryCourses();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        return view;
    }
    @SuppressLint("Range")
    private void displayCourseDetails() {
        LinearLayout linearLayout = getActivity().findViewById(R.id.viewOfferingsLayout);
        linearLayout.removeAllViews();
        dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);

        Cursor cursor = dbHelper.getAllOfferedCoursesHistory();

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
            history = new TextView(getActivity());
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