package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class homeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout ;
    NavigationView navigationView ;
    ActionBarDrawerToggle actionBarDrawerToggle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //this will make toolbar instead of action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar) ;
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout) ;

        navigationView = (NavigationView) findViewById(R.id.nav_view) ;

        // if user select item from the navigation view it will be detected here
        final createNewCourse createNewCourse = new createNewCourse();
        final edit_delete_course edit_delete_course = new edit_delete_course();
        final make_course_available make_course_available = new make_course_available();
        final ViewAllStudents viewAllStudents = new ViewAllStudents();
        final Search_Unaccpted_Users search_unaccpted_users = new Search_Unaccpted_Users();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragment_conatiner, createNewCourse, "create new course"); // initial fragment
        ft.commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.createCourse:
                        ft.replace(R.id.fragment_conatiner, createNewCourse);

                        break;
                    case R.id.editDeleteCourse :
                        ft.replace(R.id.fragment_conatiner, edit_delete_course);
                        break;
                    case R.id.makeCourseAvailable :
                        ft.replace(R.id.fragment_conatiner, make_course_available);
                        break;
                    case R.id.acceptReject:
                        ft.replace(R.id.fragment_conatiner, search_unaccpted_users);
                        break;
                    case R.id.viewStudents :
                        ft.replace(R.id.fragment_conatiner, viewAllStudents);
                        break;
                    case R.id.logout:
                        Intent intent = new Intent(homeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                ft.addToBackStack(null);
                ft.commit();
                return true;
            }
        });


        // this is important part to add button in the toolbar
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav) ;

        actionBarDrawerToggle.syncState();

        drawerLayout.setDrawerListener(actionBarDrawerToggle) ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item)||super.onOptionsItemSelected(item);
    }
}