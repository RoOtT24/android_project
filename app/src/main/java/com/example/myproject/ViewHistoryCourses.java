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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ViewHistoryCourses extends Fragment {
    TextView history ;
    DataBaseHelper dbHelper ;
    private DateFormat dateFormat;

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
//       Calendar calendar = Calendar.getInstance();
//        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Cursor cursor = dbHelper.getAllOfferedCoursesHistory();

        if (cursor.getCount()>0) {
            while(cursor.moveToNext()){
                LinearLayout ly = new LinearLayout(getActivity());
                ly.setOrientation(LinearLayout.HORIZONTAL);
                TextView courseNameText = new TextView(getActivity());
                TextView scheduleEditText = new TextView(getActivity());
                TextView instructorText = new TextView(getActivity());
                instructorText.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_FIRST_NAME))+" "+cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_LAST_NAME))+" ");
                scheduleEditText.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_COURSE_SCHEDULE)));
                courseNameText.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_TITLE)));
                ly.addView(instructorText);
                ly.addView(scheduleEditText);
                ly.addView(courseNameText);
                linearLayout.addView(ly);
                LinearLayout linearLayout1 = new LinearLayout(getActivity());
                linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout linearLayout2 = new LinearLayout(getActivity());
                linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
                TextView startDate = new TextView(getActivity());
                TextView EndDate = new TextView(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 0, 150);
                startDate.setText(new Date(cursor.getLong(cursor.getColumnIndex(DataBaseHelper.COLUMN_START_DATE))).toString());
                EndDate.setText(new Date(cursor.getLong(cursor.getColumnIndex(DataBaseHelper.COLUMN_END_DATE))).toString());
              //  startDate.setLayoutParams(params);
                EndDate.setLayoutParams(params);

                linearLayout1.addView(startDate);
                linearLayout2.addView(EndDate);
                linearLayout.addView(linearLayout1);
                linearLayout.addView(linearLayout2);
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