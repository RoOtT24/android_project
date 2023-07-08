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

public class homeStudent extends AppCompatActivity {
    DrawerLayout drawerLayout ;
    NavigationView navigationView ;
    ActionBarDrawerToggle actionBarDrawerToggle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);
        //this will make toolbar instead of action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar) ;
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout) ;

        navigationView = (NavigationView) findViewById(R.id.nav_view) ;

        // if user select item from the navigation view it will be detected here
        final SearchCourse SearchCourse = new SearchCourse();
        final Enroll enroll = new Enroll();
        final ViewHistoryCourses viewHistory = new ViewHistoryCourses();
        final EnrollCoursesFragment EnrollCoursesFragment = new EnrollCoursesFragment();
        final ViewCoursesInCenterFragment ViewCoursesInCenterFragment = new ViewCoursesInCenterFragment();
        final MyProfileStudentFragment MyProfileStudentFragment = new MyProfileStudentFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragment_conatiner, SearchCourse, "Search Course"); // initial fragment
       ft.commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.search_course:
                        ft.replace(R.id.fragment_conatiner, SearchCourse);

                        break;
                    case R.id.enrollInCourseAvailable :
                        ft.replace(R.id.fragment_conatiner, enroll);
                        break;
                    case R.id.viewMyCoursesFinshed :
                        ft.replace(R.id.fragment_conatiner, viewHistory);
                        break;
                    case R.id.viewCoursesHistory:
                        ft.replace(R.id.fragment_conatiner, ViewCoursesInCenterFragment);
                        break;
                    case R.id.viewCourses_withdrow :
                        ft.replace(R.id.fragment_conatiner, EnrollCoursesFragment);
                        break;
                    case R.id.MyProfile :
                        ft.replace(R.id.fragment_conatiner, MyProfileStudentFragment);
                        break;
//                    case R.id.logout:
//                        Intent intent = new Intent(homeActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                        finish();
//                        break;
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