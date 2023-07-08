package com.example.myproject;

import java.util.Date;

public class Offer {


    private int courseId;
private int offerid;
    private String instructorEmail;

    private Date registrationDeadline;

    private Date courseStartDate;
    private String courseSchedule;

    private String venue;

    public Offer(int courseId, String instructorEmail, Date registrationDeadline, Date courseStartDate, String courseSchedule, String venue) {
        this.courseId = courseId;
        this.instructorEmail = instructorEmail;
        this.registrationDeadline = registrationDeadline;
        this.courseStartDate = courseStartDate;
        this.courseSchedule = courseSchedule;
        this.venue = venue;
    }
    public Offer(int offerid,int courseId, String instructorEmail, Date registrationDeadline, Date courseStartDate, String courseSchedule, String venue) {
        this.offerid = offerid;
        this.courseId = courseId;
        this.instructorEmail = instructorEmail;
        this.registrationDeadline = registrationDeadline;
        this.courseStartDate = courseStartDate;
        this.courseSchedule = courseSchedule;
        this.venue = venue;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public int getofferId() {
        return offerid;
    }

    public void setofferId(int courseId) {
        this.offerid = offerid;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public Date getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(Date registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Date courseStartDate) {
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
