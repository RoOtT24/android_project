package com.example.myproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private  DataBaseHelper dbHelper ;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int x =0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        dbHelper = new DataBaseHelper(SignupActivity.this, "DATABASE", null, 1);

        Button signup = findViewById(R.id.SignUp);
        EditText firstName = findViewById(R.id.FirstName);
        EditText lastName = findViewById(R.id.LastName);
        EditText email = findViewById(R.id.Email);
        EditText phone = findViewById(R.id.Phone);
        EditText address = findViewById(R.id.Address);
        EditText password = findViewById(R.id.Password);
        EditText confirmPassword = findViewById(R.id.ConfrimPassword);
        ImageView profileImageView = (ImageView) findViewById(R.id.profilePic);
        Boolean []imageChanged = {false};


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK ){
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        profileImageView.setImageBitmap(bitmap);
                        imageChanged[0] = true;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName = firstName.getText().toString().trim();
                String lName = lastName.getText().toString().trim();
                String userEmail = email.getText().toString().trim();
                String userPhone = phone.getText().toString().trim();
                String userAddress = address.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                String confirmPass = confirmPassword.getText().toString().trim();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                // Validate required fields
                boolean []isValid = {true};
                byte []bytes = {};

                if(bitmap != null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100,byteArrayOutputStream);
                    bytes = byteArrayOutputStream.toByteArray();
                }
                else{
                    isValid[0] = false;
                }


                if (fName.isEmpty()) {
                    isValid[0] = false;
                    firstName.setError("First name is required");
                } else if (fName.length() < 3 || fName.length() > 20) {
                    isValid[0] = false;
                    firstName.setError("First name must be between 3 and 20 characters");
                }

                if (lName.isEmpty()) {
                    isValid[0] = false;
                    lastName.setError("Last name is required");
                } else if (lName.length() < 3 || lName.length() > 20) {
                    isValid[0] = false;
                    lastName.setError("Last name must be between 3 and 20 characters");
                }
                if (userEmail.isEmpty()) {
                    isValid[0] = false;
                    email.setError("Email is required");
                }
                if (userPhone.isEmpty()) {
                    isValid[0] = false;
                    phone.setError("Phone is required");
                }
                if (userAddress.isEmpty()) {
                    isValid[0] = false;
                    address.setError("Address is required");
                }
                if (userPassword.isEmpty()) {
                    isValid[0] = false;
                    password.setError("Password is required");
                }
                if (confirmPass.isEmpty()) {
                    isValid[0] = false;
                    confirmPassword.setError("Confirm password is required");
                }

                // Validate email format
                if (!userEmail.isEmpty() && !isValidEmail(userEmail)) {
                    isValid[0] = false;
                    email.setError("Invalid email format");
                }

                // Validate password format
                if (!userPassword.isEmpty() && !isValidPassword(userPassword)) {
                    isValid[0] = false;
                    password.setError("Invalid password format, must be 8-15 character and contain at least one number, one lowercase letter, and one uppercase letter. ");
                }

                if(!imageChanged[0]){
                    isValid[0] = false;
                    Toast.makeText(SignupActivity.this, "Insert A Profile Picture!", Toast.LENGTH_SHORT).show();
                }
                if (isValid[0] && userPassword.equals(confirmPass)) {
                    // Create a new Student object
                    Student student = new Student(fName, lName, userEmail, userPhone, userAddress, userPassword, bytes);

                    // Insert the student into the database
                    dbHelper.insertUser(student);

                    // Show a success message
                    Toast.makeText(SignupActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();

                    // Clear the input fields
                    clearInputFields();
                }
                if(!isValid[0]) {
                    Toast.makeText(SignupActivity.this, "Please fill in all required fields correctly", Toast.LENGTH_SHORT).show();
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
        EditText password = findViewById(R.id.Password);
        EditText confirmPassword = findViewById(R.id.ConfrimPassword);
        ImageView iv = findViewById(R.id.profilePic);

        firstName.setText("");
        lastName.setText("");
        email.setText("");
        phone.setText("");
        address.setText("");
        password.setText("");
        confirmPassword.setText("");
        iv.setImageResource(R.drawable.upload_image);
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