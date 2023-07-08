package com.example.myproject;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewAllStudents#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewAllStudents extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DataBaseHelper dbHelper;

    public ViewAllStudents() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewAllStudents.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewAllStudents newInstance(String param1, String param2) {
        ViewAllStudents fragment = new ViewAllStudents();
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
        return inflater.inflate(R.layout.fragment_view_all_students, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = new DataBaseHelper( getActivity().getBaseContext(), "DATABASE", null , 1);

        Cursor cursor = dbHelper.getAllStudents();

        LinearLayout linearLayout = getActivity().findViewById(R.id.All_instructors_LinearLayout);
        linearLayout.removeAllViews();
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                LinearLayout vertical = new LinearLayout(getActivity());
                vertical.setOrientation(LinearLayout.VERTICAL);
                LinearLayout ly = new LinearLayout(getActivity());
                ly.setOrientation(LinearLayout.HORIZONTAL);

                TextView name_text = new TextView(getActivity());
                TextView email_text = new TextView(getActivity());
                TextView phone_text = new TextView(getActivity());
                TextView address_text = new TextView(getActivity());
                ImageView iv = new ImageView(getActivity());
                name_text.setText(cursor.getString(1)+" "+cursor.getString(2));
                email_text.setText(cursor.getString(0));
                phone_text.setText(cursor.getString(3));
                address_text.setText(cursor.getString(4));
                iv.setImageBitmap(BitmapFactory.decodeByteArray(cursor.getBlob(6), 0 , cursor.getBlob(6).length));
                iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                iv.getLayoutParams().height = 300;
                iv.getLayoutParams().width = 300;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(100, 50 , 50, 50);
                name_text.setLayoutParams(params);
                ly.addView(name_text);
                ly.addView(iv);
                vertical.addView(ly);
                LinearLayout ly2 = new LinearLayout(getActivity());
                params.setMargins(100, 0, 300, 50);
                email_text.setLayoutParams(params);
                ly2.addView(email_text);
                ly2.addView(phone_text);
                ly2.addView(address_text);
                vertical.addView(ly2);
                GradientDrawable border = new GradientDrawable();
//                border.setColor(0xFFFFFFFF); //white background
                border.setStroke(1, 0xFFFFFFFF); //black border with full opacity
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    vertical.setBackgroundDrawable(border);
                } else {
                    vertical.setBackground(border);
                }
                linearLayout.addView(vertical);
            }
        }
        else {
            TextView tv = new TextView(getActivity());
            tv.setText("no students found!");
            linearLayout.addView(tv);
        }
    }
}