package com.example.myproject;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class MyProfileInstructorFragment extends Fragment {


    EditText firtname;
    EditText lastname;
    EditText email;
    EditText phone;
    EditText address;
    EditText password;
    ImageView img;
    EditText DEGREE;
    EditText SPECIALIZATION;
    EditText COURSES;
    Button update;
    DataBaseHelper dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);
    public MyProfileInstructorFragment() {
        // Required empty public constructor
    }
    public static MyProfileInstructorFragment newInstance() {
        MyProfileInstructorFragment fragment = new MyProfileInstructorFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_profile_instructor, container, false);
        firtname = view.findViewById(R.id.first_name);
        lastname = view.findViewById(R.id.last_name);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        address = view.findViewById(R.id.address);
        password = view.findViewById(R.id.password);
        img = view.findViewById(R.id.img);
        SPECIALIZATION = view.findViewById(R.id.Specialization);
        DEGREE = view.findViewById(R.id.Degree);
        COURSES = view.findViewById(R.id.courses);
        update = view.findViewById(R.id.update);
        // Retrieve the user email from the arguments
        String userEmail = getArguments().getString("userEmail");
        Cursor cursor = dbHelper.getInstructorByEmail(userEmail);
        // Use the userEmail in your fragment logic
        int COLUMN_FIRSTNAME_INDEX = cursor.getColumnIndex("firstname");
        int COLUMN_LASTNAME_INDEX = cursor.getColumnIndex("lastname");
        int COLUMN_EMAIL_INDEX = cursor.getColumnIndex("email");
        int COLUMN_PHONE_INDEX = cursor.getColumnIndex("phone");
        int COLUMN_ADDERSS_INDEX = cursor.getColumnIndex("address");
        int COLUMN_IMAGE_INDEX = cursor.getColumnIndex("Image");
        int COLUMN_PASSWORD_INDEX = cursor.getColumnIndex("password");

        int COLUMN_DEGREE_INDEX = cursor.getColumnIndex("degree");
        int COLUMN_SPECIALIZATION_INDEX = cursor.getColumnIndex("Specialization");
        int COLUMN_COURSES_INDEX = cursor.getColumnIndex("courses");

        while(cursor.moveToNext()){
            firtname.setText(cursor.getString(COLUMN_FIRSTNAME_INDEX));
            lastname.setText(cursor.getString(COLUMN_LASTNAME_INDEX));
            email.setText(cursor.getString(COLUMN_EMAIL_INDEX)); // حطيتها هيك للتاكد انو بجيب صح لكن الاصل نحط القيمه useremail
            phone.setText(cursor.getString(COLUMN_PHONE_INDEX));
            address.setText(cursor.getString(COLUMN_ADDERSS_INDEX));
            password.setText(cursor.getString(COLUMN_PASSWORD_INDEX));

            DEGREE.setText(cursor.getString(COLUMN_DEGREE_INDEX));
            SPECIALIZATION.setText(cursor.getString(COLUMN_SPECIALIZATION_INDEX));
            COURSES.setText(cursor.getString(COLUMN_COURSES_INDEX));
            img.setImageBitmap(BitmapFactory.decodeByteArray(cursor.getBlob(COLUMN_IMAGE_INDEX),0,cursor.getBlob(COLUMN_IMAGE_INDEX).length));

        }
        cursor.close();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Fname = firtname.getText().toString();
                String Lname = lastname.getText().toString();
                // String Email = email.getText().toString();
                String Phone = phone.getText().toString();
                String Adderss = address.getText().toString();
                String PASS = password.getText().toString();

                String deg = DEGREE.getText().toString();
                String spec = SPECIALIZATION.getText().toString();
                String [] course = dbHelper.convertStringToArray(COURSES.getText().toString());

                Instructor instructor = new Instructor(Fname,Lname,userEmail,Phone,Adderss,PASS,spec,deg,course,null);
                dbHelper.updateInstrucorData(instructor);
            }
        });
        return view;
    }
}