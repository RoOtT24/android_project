package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

public class Courses {//extends AppCompatActivity {
    private int id;

    private String title;
    private String[] mainTopics;
    private String[] prerequisites;
    private byte []image;


    public Courses(int id, String title, String[] mainTopics, String[] prerequisites, byte[] image) {
        this.id = id;
        this.title = title;
        this.mainTopics = mainTopics;
        this.prerequisites = prerequisites;
        this.image = image;
    }

    public Courses(String title, String[] mainTopics, String[] prerequisites, byte[] image) {
        this.title = title;
        this.mainTopics = mainTopics;
        this.prerequisites = prerequisites;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    public String getTitle() {
        return title;
    }
}
