package com.example.myproject;

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

        FrameLayout fl = getActivity().findViewById(R.id.search_unaccpted_layout);
        Cursor cursor = db.getUnApprovedStudents();
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
                Student s = new Student(cursor.getString(1), cursor.getString(2), cursor.getString(0), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getBlob(6));
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
                        db.removeStudent(s.getEmail());
                        showAll();
                    }
                });

                approve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.setAccepted(s.getEmail());
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


        cursor = db.getUnApprovedInstructors();
        if(cursor.getCount() > 0){
            TextView instructorText = new TextView(getActivity());
            instructorText.setText("Instructors");
            linearLayout.addView(instructorText);
            while(cursor.moveToNext()){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                Instructor s = new Instructor(cursor.getString(1), cursor.getString(2), cursor.getString(0), cursor.getString(5), cursor.getString(6),  cursor.getBlob(9));
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
                        db.removeInstructor(s.getEmail());
                        showAll();
                    }
                });

                approve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.setAccepted(s.getEmail());
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


        cursor = db.getUnApprovedAdmins();
        if(cursor.getCount() > 0){
            TextView AdminText = new TextView(getActivity());
            AdminText.setText("Admins");
            linearLayout.addView(AdminText);
            while(cursor.moveToNext()){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                Admin s = new Admin(cursor.getString(1), cursor.getString(2), cursor.getString(0), cursor.getString(3) ,cursor.getBlob(4));
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
                        db.removeAdmin(s.getEmail());
                        showAll();
                    }
                });

                approve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), s.getEmail(), Toast.LENGTH_SHORT).show();
                        db.setAccepted(s.getEmail());
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
