package com.example.myproject;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Search_Unaccpted_Users#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search_Unaccpted_Users extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Search_Unaccpted_Users() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search_Unaccpted_Users.
     */
    // TODO: Rename and change types and number of parameters
    public static Search_Unaccpted_Users newInstance(String param1, String param2) {
        Search_Unaccpted_Users fragment = new Search_Unaccpted_Users();
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
        return inflater.inflate(R.layout.fragment_search__unaccpted__users, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showAll();

    }

    public void showAll(){
        DataBaseHelper db = new DataBaseHelper(getActivity().getBaseContext(), "DB", null, 1);

        Cursor cursor = db.getUnApprovedRegistrations();
        LinearLayout linearLayout = getActivity().findViewById(R.id.unaccepted_linear_layout);
        linearLayout.removeAllViews();

        if(cursor.getCount() > 0){
            TextView studentText = new TextView(getActivity());
            studentText.setText("Students");
            linearLayout.addView(studentText);
            while(cursor.moveToNext()){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                @SuppressLint("Range") Student s = new Student(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_FIRST_NAME)), cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_LAST_NAME)), cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_EMAIL)), cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_PHONE)), cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_ADDRESS)), cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_PASSWORD)), cursor.getBlob(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_IMAGE)));

                @SuppressLint("Range") final int enrollId = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_ENROLL_ID));
//                Toast.makeText(getActivity(),"id="+ Integer.toString(enrollId), Toast.LENGTH_SHORT).show();
                LinearLayout ly = new LinearLayout(getActivity());
                ly.setOrientation(LinearLayout.HORIZONTAL);
                TextView email = new TextView(getActivity());
                email.setText(s.getEmail());
                params.setMargins(100, 0 , 0, 0);
                email.setLayoutParams(params);
                ImageView iv = new ImageView(getActivity());
                iv.setImageBitmap(BitmapFactory.decodeByteArray(s.getImage(), 0 , s.getImage().length));

                Button deny = new Button(getActivity());
                deny.setText("deny");
                Button approve = new Button(getActivity());
                approve.setText("approve");

                deny.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.removeEnroll(enrollId);
                        showAll();
                    }
                });

                approve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.setAccepted(enrollId);
                        showAll();
                    }
                });

                ly.addView(email,0);
                ly.addView(iv,1);
                iv.getLayoutParams().height = 150;
                iv.getLayoutParams().width = 250;
                iv.requestLayout();
                ly.addView(deny,2);
                ly.addView(approve, 3);

                linearLayout.addView(ly);
            }
        }
        cursor.close();


    }
}
