package com.example.myproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyProfileStudentFragment extends Fragment {

    public MyProfileStudentFragment() {
        // Required empty public constructor
    }


    public static MyProfileStudentFragment newInstance() {
        MyProfileStudentFragment fragment = new MyProfileStudentFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile_student, container, false);

        // Retrieve the user email from the arguments
        String userEmail = getArguments().getString("userEmail");

        // Use the userEmail in your fragment logic

        return view;
    }

}