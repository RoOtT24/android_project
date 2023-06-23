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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rem[0] = false;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedEmail = sharedPreferences.getString("email", "");
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        password =(EditText) findViewById(R.id.password);
        login= (Button) findViewById(R.id.login);
        remember  = (CheckBox) findViewById(R.id.checkBox);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent = new Intent(LoginActivity.this, homeActivity.class);
              //  startActivity(intent);
                loginUser();
               // finish();
            }
        });

       remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              rem[0]= !rem[0];
            }
        });

    }


    public void toSignup(View view) {
        Intent intent = new Intent(this,WhoAreYou.class);
        startActivity(intent);
    }
    private void loginUser() {
        String username = emailEditText.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (username.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.isEmailExists(username)) {
            Cursor cursor = dbHelper.getStudentByEmail(username);
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String storedPassword = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_PASSWORD));
                Toast.makeText(this, storedPassword, Toast.LENGTH_SHORT).show();

                if (pass.equals(storedPassword)) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    if (rem[0]){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    EditText emailEditText = findViewById(R.id.emailEditText);
                    String email = emailEditText.getText().toString();
                    editor.putString("email", email);
                    editor.apply();
                        Toast.makeText(this, "shared Preferences done", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
                }
            }
            cursor.close();
        } else {

            Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show();
        }
    }
}
