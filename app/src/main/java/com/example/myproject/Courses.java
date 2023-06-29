package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

public class Courses {//extends AppCompatActivity {
    private int courseId = 1;

    private String title;
    private String[] mainTopics;
    private String[] prerequisites;
    private String photoUrl;

    public Courses(int courseId, String title, String[] mainTopics, String[] prerequisites, String photoUrl) {
        this.courseId = courseId;
        this.title = title;
        this.mainTopics = mainTopics;
        this.prerequisites = prerequisites;
        this.photoUrl = photoUrl;
    }

    public Courses(String title, String[] mainTopics, String[] prerequisites, String photoUrl){
        this.title = title;
        this.mainTopics = mainTopics;
        this.prerequisites = prerequisites;
        this.photoUrl = photoUrl;

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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }
}
