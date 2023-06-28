package com.example.myproject;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class admin_drawer extends AppCompatActivity {//implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // Find the DrawerLayout
//        drawerLayout = findViewById(R.id.drawer_layout);
//
//        // Find the NavigationView
//        NavigationView navigationView = findViewById(R.id.nav_view);
//
//        // Set the listener for navigation item clicks
//        navigationView.setNavigationItemSelectedListener(this);
    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        // Handle navigation item clicks here
//        int itemId = item.getItemId();
//
//        switch (itemId) {
//            case R.id.nav_create_course:
//                // Handle the "Create Course" menu item click
//                // Open the CreateCourse activity or perform any desired action
//                // For example, you can use an Intent to navigate to the CreateCourse activity:
//                // Intent intent = new Intent(MainActivity.this, CreateCourse.class);
//                // startActivity(intent);
//                break;
//            case R.id.nav_view_courses:
//                // Handle the "View Courses" menu item click
//                // Open the ViewCourses activity or perform any desired action
//                break;
//            case R.id.nav_previous_courses:
//                // Handle the "Previous Courses" menu item click
//                // Open the PreviousCourses activity or perform any desired action
//                break;
//            // Add more cases for other menu items as needed
//
//            default:
//                break;
//        }
//
//        // Close the drawer after handling the item click
//        drawerLayout.closeDrawer(GravityCompat.START);
//
//        return true;
//    }

//    @Override
//    public void onBackPressed() {
//        // Close the drawer if it's open when the back button is pressed
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
}
