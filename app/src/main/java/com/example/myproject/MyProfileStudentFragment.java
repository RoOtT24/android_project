package com.example.myproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyProfileStudentFragment extends Fragment {
    EditText firtname;
    EditText lastname;
    EditText email;
    EditText phone;
    EditText address;
    EditText password;
    ImageView img;
    Button update;
    DataBaseHelper dbHelper ;


    public MyProfileStudentFragment() {
        // Required empty public constructor
    }


    public static MyProfileStudentFragment newInstance() {
        MyProfileStudentFragment fragment = new MyProfileStudentFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @SuppressLint("WrongThread")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile_student, container, false);
        firtname = view.findViewById(R.id.first_name);
        lastname = view.findViewById(R.id.last_name);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        address = view.findViewById(R.id.address);
        password = view.findViewById(R.id.password);
        img = view.findViewById(R.id.img);
        update = view.findViewById(R.id.update);
dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);
        Bundle extras = getActivity().getIntent().getExtras();
        String userEmail = new String();
        if (extras != null) {
            userEmail = extras.getString("email");
            //The key argument here must match that used in the other activity
        }

        Cursor cursor = dbHelper.getStudentByEmail(userEmail);
        // Use the userEmail in your fragment logic
        int COLUMN_FIRSTNAME_INDEX = cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_FIRST_NAME);
        int COLUMN_LASTNAME_INDEX = cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_LAST_NAME);
       // int COLUMN_EMAIL_INDEX = cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_EMAIL);
        int COLUMN_PHONE_INDEX = cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_PHONE);
        int COLUMN_ADDERSS_INDEX = cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_ADDRESS);
        int COLUMN_IMAGE_INDEX = cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_IMAGE);
        int COLUMN_PASSWORD_INDEX = cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_PASSWORD);

        while(cursor.moveToNext()){
            firtname.setText(cursor.getString(COLUMN_FIRSTNAME_INDEX));
            lastname.setText(cursor.getString(COLUMN_LASTNAME_INDEX));
            email.setText(userEmail);
            phone.setText(cursor.getString(COLUMN_PHONE_INDEX));
            address.setText(cursor.getString(COLUMN_ADDERSS_INDEX));
            password.setText(cursor.getString(COLUMN_PASSWORD_INDEX));
           img.setImageBitmap(BitmapFactory.decodeByteArray(cursor.getBlob(COLUMN_IMAGE_INDEX), 0, cursor.getBlob(COLUMN_IMAGE_INDEX).length));
        }
        cursor.close();



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Fname = firtname.getText().toString();
                String Lname = lastname.getText().toString();
                String Email = email.getText().toString();
                String Phone = phone.getText().toString();
                String Adderss = address.getText().toString();
                String PASS = password.getText().toString();
                Student stu = new Student(Fname,Lname,Email,Phone,Adderss,PASS,null);
                dbHelper.updateStudentData(stu);
                Toast.makeText(getActivity(), "Updated successfully ", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
void clear(){
  firtname.setText("");
  lastname.setText("");
  email.setText("");
  phone.setText("");
  address.setText("");
  password.setText("");
}
}

