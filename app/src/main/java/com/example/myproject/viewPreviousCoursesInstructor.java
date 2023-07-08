package com.example.myproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class viewPreviousCoursesInstructor extends Fragment {
 EditText email ;
 TextView courses;
    DataBaseHelper dbHelper;
    public viewPreviousCoursesInstructor() {
        // Required empty public constructor
    }
    public static viewPreviousCoursesInstructor newInstance() {
        viewPreviousCoursesInstructor fragment = new viewPreviousCoursesInstructor();
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

        View view = inflater.inflate(R.layout.fragment_view_previous_courses_instructor, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper  = new DataBaseHelper(getActivity(), "DATABASE", null, 1);
        Bundle extras = getActivity().getIntent().getExtras();
        String email = new String();
        if (extras != null) {
            email = extras.getString("email");
            //The key argument here must match that used in the other activity
        }

        Cursor cursor = dbHelper.getPreviousCoursesByInstructor(email);

//        StringBuilder coursesBuilder = new StringBuilder();
        LinearLayout linearLayout = getActivity().findViewById(R.id.view_previous_courses_layout);
        linearLayout.removeAllViews();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                LinearLayout vertical = new LinearLayout(getActivity());
                vertical.setOrientation(LinearLayout.VERTICAL);

                LinearLayout ly1 = new LinearLayout(getActivity());
                ly1.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout ly2 = new LinearLayout(getActivity());
                ly2.setOrientation(LinearLayout.HORIZONTAL);

                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String mainTopics = cursor.getString(2);
                String pre = cursor.getString(3);
                byte [] img = cursor.getBlob(4);

                TextView id_view = new TextView(getActivity());
                id_view.setText(Integer.toString(id));
                TextView title_view = new TextView(getActivity());
                title_view.setText(title);
                TextView mainTopicsText = new TextView(getActivity());
                mainTopicsText.setText(mainTopics);
                TextView preText = new TextView(getActivity());
                preText.setText(pre);
                ImageView iv = new ImageView(getActivity());
                iv.setImageBitmap(BitmapFactory.decodeByteArray(img, 0 , img.length));

                ly1.addView(id_view,0);
                ly1.addView(title_view, 1);
                ly1.addView(iv, 2);

                vertical.addView(ly1, 0);

                ly2.addView(mainTopicsText,0);
                ly2.addView(preText);

                vertical.addView(ly2,1);

                linearLayout.addView(vertical);


            } while (cursor.moveToNext());
        }
        else {
            TextView tv = new TextView(getActivity());
            tv.setText("no courses found!");
            linearLayout.addView(tv);
        }

        cursor.close();

    }
}