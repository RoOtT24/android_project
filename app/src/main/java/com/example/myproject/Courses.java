package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

public class Courses extends AppCompatActivity {
    private static int nextCourseNumber = 1;
    private int courseNumber;
    private String title;
    private String[] mainTopics;
    private String[] prerequisites;
    private String photoUrl;

    public Courses(String title, String[] mainTopics, String[] prerequisites, String photoUrl) {
        this.courseNumber = nextCourseNumber++;
        this.title = title;
        this.mainTopics = mainTopics;
        this.prerequisites = prerequisites;
        this.photoUrl = photoUrl;
    }
    public Courses(){}

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String gettitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getMainTopics() {
        return mainTopics;
    }

    public void setMainTopics(String[] mainTopics) {
        this.mainTopics = mainTopics;
    }

    public String[] getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String[] prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
