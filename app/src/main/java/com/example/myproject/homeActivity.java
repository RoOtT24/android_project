package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

public class homeActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private DataBaseHelper dbHelper = new DataBaseHelper(homeActivity.this, "DATABASE", null, 1);
    private LoginActivity login = new LoginActivity();

    private NavigationView navigationView;
    private int currentUserType ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       // drawer = findViewById(R.id.drawer_layout);
      //  navigationView = findViewById(R.id.nav_view);
        currentUserType = dbHelper.getUserType(login.emailEditText.getText().toString());
        // Load appropriate layout based on user type
        switch (currentUserType) {
            case 1:
               // loadInstructorLayout();
                break;
           // case 2:
               // loadUserType2Layout();
              //  break;
            // Add cases for other user types as needed
        }

        // Set up the navigation drawer toggle button
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Handle navigation view item clicks
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // Handle the menu item clicks based on the user type
                int id = menuItem.getItemId();
                switch (currentUserType) {
                    case 1:
                        // Handle menu item clicks for user type 1
                        break;
                    case 2:
                        // Handle menu item clicks for user type 2
                        break;
                    // Add cases for other user types as needed
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

 /*   private void loadInstructorLayout() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View userType1Layout = inflater.inflate(R.layout.activity_create_course, null);
        FrameLayout container = findViewById(R.id.content_frame);
        container.removeAllViews();
        container.addView(userType1Layout);
    }*/

//    private void loadUserType2Layout() {
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View userType2Layout = inflater.inflate(R.layout.layout_user_type2, null);
//        FrameLayout container = findViewById(R.id.content_frame);
//        container.removeAllViews();
//        container.addView(userType2Layout);
//    }




}

