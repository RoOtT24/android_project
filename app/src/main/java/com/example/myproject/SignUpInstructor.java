package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class SignUpInstructor extends AppCompatActivity {

    private  DataBaseHelper dbHelper ;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int x =0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupinstructor);
        dbHelper = new DataBaseHelper(SignUpInstructor.this, "DATABASE", null, 1);

        Button signup = findViewById(R.id.SignUp);
        EditText firstName = findViewById(R.id.FirstName);
        EditText lastName = findViewById(R.id.LastName);
        EditText email = findViewById(R.id.Email);
        EditText phone = findViewById(R.id.Phone);
        EditText specialization = findViewById(R.id.specialization);
        EditText degree = findViewById(R.id.Degree);
        EditText courses = findViewById(R.id.Courses);
        EditText address = findViewById(R.id.Address);
        EditText password = findViewById(R.id.Password);
        EditText confirmPassword = findViewById(R.id.ConfrimPassword);



            signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] courseNames={};
                String fName = firstName.getText().toString().trim();
                String lName = lastName.getText().toString().trim();
                String userEmail = email.getText().toString().trim();
                String userPhone = phone.getText().toString().trim();
                String userAddress = address.getText().toString().trim();
                String userSpecialization = specialization.getText().toString().trim();
                String userDegree = degree.getText().toString().trim();
                String usercourse = courses.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                String confirmPass = confirmPassword.getText().toString().trim();

                // Validate required fields
                boolean isValid = true;
                if (fName.isEmpty()) {
                    isValid = false;
                    firstName.setError("First name is required");
                } else if (fName.length() < 3 || fName.length() > 20) {
                    isValid = false;
                    firstName.setError("First name must be between 3 and 20 characters");
                }

                if (lName.isEmpty()) {
                    isValid = false;
                    lastName.setError("Last name is required");
                } else if (lName.length() < 3 || lName.length() > 20) {
                    isValid = false;
                    lastName.setError("Last name must be between 3 and 20 characters");
                }
                if (userEmail.isEmpty()) {
                    isValid = false;
                    email.setError("Email is required");
                }
                if (userPhone.isEmpty()) {
                    isValid = false;
                    phone.setError("Phone is required");
                }
                if (userAddress.isEmpty()) {
                    isValid = false;
                    address.setError("Address is required");
                }
                if (userDegree.isEmpty()) {
                    isValid = false;
                    degree.setError("Degree is required");
                }
                if (userSpecialization.isEmpty()) {
                    isValid = false;
                    specialization.setError("Specialization is required");
                }
               if (usercourse.isEmpty()) {
                    isValid = false;
                    courses.setError("Courses is required");
                }
                else{
                    courseNames=usercourse.split(",");
                }
                if (userPassword.isEmpty()) {
                    isValid = false;
                    password.setError("Password is required");
                }
                if (confirmPass.isEmpty()) {
                    isValid = false;
                    confirmPassword.setError("Confirm password is required");
                }

                // Validate email format
                if (!userEmail.isEmpty() && !isValidEmail(userEmail)) {
                    isValid = false;
                    email.setError("Invalid email format");
                }

                // Validate password format
                if (!userPassword.isEmpty() && !isValidPassword(userPassword)) {
                    isValid = false;
                    password.setError("Invalid password format, must be 8-15 character and contain at least one number, one lowercase letter, and one uppercase letter. ");
                }

                if (isValid && userPassword.equals(confirmPass)) {
                    // Create a new Student object
                    Instructor instructor = new Instructor(fName, lName, userEmail, userPhone, userAddress, userPassword, userSpecialization,userDegree, courseNames);
                    // Insert the instructor into the database
                    dbHelper.insertInstructor(instructor);

                    // Show a success message
                    Toast.makeText(SignUpInstructor.this, "Signup successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUpInstructor.this,LoginActivity.class);
                    startActivity(intent);
                    finish();

                    // Clear the input fields
                    clearInputFields();
                } else {
                    Toast.makeText(SignUpInstructor.this, "Please fill in all required fields correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearInputFields() {
        EditText firstName = findViewById(R.id.FirstName);
        EditText lastName = findViewById(R.id.LastName);
        EditText email = findViewById(R.id.Email);
        EditText phone = findViewById(R.id.Phone);
        EditText address = findViewById(R.id.Address);
        EditText degree = findViewById(R.id.Degree);
        EditText specialization = findViewById(R.id.specialization);
        EditText courses = findViewById(R.id.Courses);
        EditText password = findViewById(R.id.Password);
        EditText confirmPassword = findViewById(R.id.ConfrimPassword);

        firstName.setText("");
        lastName.setText("");
        email.setText("");
        degree.setText("");
        specialization.setText("");
        courses.setText("");
        phone.setText("");
        address.setText("");
        password.setText("");
        confirmPassword.setText("");
    }
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return Pattern.matches(emailPattern, email);
    }

    private boolean isValidPassword(String password) {
        // Password must contain at least one number, one lowercase letter, one uppercase letter
        // and be between 8 and 15 characters long
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,15}$";
        return Pattern.matches(passwordPattern, password);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

        }
    }

}
