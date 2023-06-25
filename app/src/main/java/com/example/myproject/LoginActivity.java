package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private DataBaseHelper dbHelper = new DataBaseHelper(LoginActivity.this, "DATABASE", null, 1);

    final boolean [] rem= new boolean[1] ;
    EditText emailEditText;
    // emailEditText.setText(savedEmail);
    EditText password ;

    Button login ;
    CheckBox remember;
    Button Signupbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rem[0] = false;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedEmail = sharedPreferences.getString("email", "");
        String savedPassword = sharedPreferences.getString("password", "");
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        password =(EditText) findViewById(R.id.password);
        login= (Button) findViewById(R.id.login);
        Signupbtn= (Button) findViewById(R.id.Signupbtn);

        remember  = (CheckBox) findViewById(R.id.checkBox);

        emailEditText.setText(savedEmail);
        password.setText(savedPassword);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(loginUser()) {
                   // if (dbHelper.getUserType(emailEditText.getText().toString()) == 2) {// admin
                    Intent intent = new Intent(LoginActivity.this, AdminFragment.class);
                    startActivity(intent);
                    finish();
                //}
                    // add another cases 1+3
                }
//                getUsers();
            }
        });

        Signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                toSignup(view);
                Intent intent = new Intent(LoginActivity.this,WhoAreYou.class);
                startActivity(intent);
                finish();
//                getUsers();
            }
        });





        remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              rem[0]= !rem[0];
            }
        });

    }


//    public void toSignup(View view) {
//        Intent intent = new Intent(LoginActivity.this,WhoAreYou.class);
//        startActivity(intent);
//        finish();
//    }
    private boolean loginUser() {
        String username = emailEditText.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (username.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dbHelper.isEmailExists(username)) {
            Cursor cursor = dbHelper.getStudentByEmail(username);
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String storedPassword = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_PASSWORD));
                Toast.makeText(this, storedPassword, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "here3", Toast.LENGTH_SHORT).show();
                if (pass.equals(storedPassword)) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    if (rem[0]){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", username);
                    editor.putString("password", pass);
                    editor.apply();
                        Toast.makeText(this, "shared Preferences done", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", "");
                        editor.putString("password", "");
                        editor.apply();
                    }
                    return true;
                } else {

                    Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
                }
            }
            cursor.close();
        } else {

            Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    public void getUsers(){
        Cursor c =dbHelper.getAllStudent();
        while(c.moveToNext()){
            Toast.makeText(this, "email : "+c.getString(2), Toast.LENGTH_SHORT).show();
        }
        c.close();
        Toast.makeText(this, "done looping", Toast.LENGTH_SHORT).show();
    }
}
