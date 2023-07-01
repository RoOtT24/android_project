package com.example.myproject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Instructor {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String password;
    private String specialization;
    private String degree;
    private String[] courses;

    private byte[] image;

    public Instructor(String firstName, String lastName, String email, String specialization, String degree, byte[] image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.specialization = specialization;
        this.degree = degree;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Instructor(String firstName, String lastName, String email, String phone, String address, String password, String specialization, String degree, String[] courses, byte[] image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.specialization = specialization;
        this.degree = degree;
        this.courses = courses;
        this.image = image;
    }

    public Instructor(String firstName, String lastName, String email, String phone, String address, String password,
                      String specialization, String degree, String[] courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.specialization = specialization;
        this.degree = degree;
        this.courses = courses;
    }

    // Getters and Setters for the attributes
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {

        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", specialization='" + specialization + '\'' +
                ", degree='" + degree + '\'' +
                ", courses=" + Arrays.toString(courses) +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
