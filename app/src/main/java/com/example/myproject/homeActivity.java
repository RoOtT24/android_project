package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
=======
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
>>>>>>> e203af7095955b51eafc230ac083617d5972bec5

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class homeActivity extends AppCompatActivity {
<<<<<<< HEAD
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private String currentUserType = "instructor";
=======

    DrawerLayout drawerLayout ;
    NavigationView navigationView ;
    ActionBarDrawerToggle actionBarDrawerToggle ;

>>>>>>> e203af7095955b51eafc230ac083617d5972bec5
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

<<<<<<< HEAD
        // Set up the navigation drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // Set up the navigation view
        navigationView = findViewById(R.id.navigation_view);

        new NavigationView.OnNavigationItemSelectedListener(this) {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        }

        // Set the default fragment based on the user type
        //  if (currentUserType.equals("admin")) {
        //        displayAdminFragment();
        //    } else if (currentUserType.equals("trainee")) {
        //    displayTraineeFragment();
        //    }
        //  else
        if (currentUserType.equals("instructor")) {
            displayInstructorFragment();
        }
=======
        //this will make toolbar instead of action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar) ;
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout) ;

        navigationView = (NavigationView) findViewById(R.id.nav_view) ;

        // if user select item from the navigation view it will be detected here
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.createCourse:  System.out.println("item 1 selected") ; break;
                    case R.id.editDeleteCourse : System.out.println("item 2 selected") ; break;
                }
                return true;
            }
        });

        // this is important part to add button in the toolbar
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav) ;

        actionBarDrawerToggle.syncState();

        drawerLayout.setDrawerListener(actionBarDrawerToggle) ;
>>>>>>> e203af7095955b51eafc230ac083617d5972bec5
    }


    // Handle navigation item clicks
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation item clicks here
        switch (item.getItemId()) {
//            case R.id.nav_create_course:
//                if (currentUserType.equals("admin")) {
//                    // Handle admin navigation item click
//                    // Open the create course activity or fragment
//                }
//                break;
           /* case R.id.nav_view_courses:
                if (currentUserType.equals("trainee")) {
                    // Handle trainee navigation item click
                    // Open the view courses activity or fragment
                }
                break;*/
            case R.id.nav_previous_courses:
                if (currentUserType.equals("trainee")) {
                    // Handle trainee navigation item click
                    // Open the previous courses activity or fragment
                } else if (currentUserType.equals("instructor")) {
                    // Handle instructor navigation item click
                    // Open the previous courses activity or fragment
                }
                break;
            // Add more navigation items based on the user type

            // Handle logout item click
//            case R.id.nav_logout:
//                // Handle logout functionality
//                // Clear user session and navigate to the login activity or fragment
//                break;
        }

        // Close the navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // Display admin fragment
//    private void displayAdminFragment() {
//        // Replace the current fragment with the admin fragment
//        Fragment adminFragment = new AdminFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, adminFragment);
//        transaction.commit();
//    }

    // Display trainee fragment
//    private void displayTraineeFragment() {
//        // Replace the current fragment with the trainee fragment
//        Fragment traineeFragment = new TraineeFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, traineeFragment);
//        transaction.commit();
//    }

    // Display instructor fragment
    private void displayInstructorFragment() {
        // Replace the current fragment with the instructor fragment
        Fragment instructorFragment = new InstructorFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, instructorFragment);
        transaction.commit();
    }




}
}