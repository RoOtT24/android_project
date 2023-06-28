package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

public class Courses extends AppCompatActivity {
    private static int nextCourseNumber = 1;
    private int courseNumber;
    private String title;
    private String[] mainTopics;
    private String[] prerequisites;
    private String photoUrl;

    private String instructorName;

    private String registrationDeadline;

    private String courseStartDate;

    private String courseSchedule;

    private String venue;

    public Courses(String title, String[] mainTopics, String[] prerequisites, String photoUrl,
                   String instructorName, String registrationDeadline, String courseStartDate,
                   String courseSchedule, String venue) {
        this.courseNumber = nextCourseNumber++;
        this.title = title;
        this.mainTopics = mainTopics;
        this.prerequisites = prerequisites;
        this.photoUrl = photoUrl;
        this.instructorName = instructorName;
        this.registrationDeadline = registrationDeadline;
        this.courseStartDate = courseStartDate;
        this.courseSchedule = courseSchedule;
        this.venue = venue;
    }

    public Courses(String title, String[] strings, String[] convertStringToArray, String photoUrl){}

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

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(String registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseSchedule() {
        return courseSchedule;
    }

    public void setCourseSchedule(String courseSchedule) {
        this.courseSchedule = courseSchedule;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
