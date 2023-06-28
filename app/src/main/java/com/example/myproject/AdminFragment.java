package com.example.myproject;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AdminFragment extends Fragment {

    private ImageView imageViewProfile;
    private TextView textViewName;
    private Button createButton;
    private Button editDeleteButton;
    private Button makeAvailableButton;
    private Button acceptRejectButton;
    private Button viewStudentsButton;
    private Button viewInstructorsButton;
    private Button viewOfferingHistoryButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);


        // Find views by their IDs
        imageViewProfile = view.findViewById(R.id.imageViewProfile);
        textViewName = view.findViewById(R.id.textViewName);
        createButton = view.findViewById(R.id.Create);
        editDeleteButton = view.findViewById(R.id.button2);
        makeAvailableButton = view.findViewById(R.id.button3);
        acceptRejectButton = view.findViewById(R.id.button4);
        viewStudentsButton = view.findViewById(R.id.button5);
        viewInstructorsButton = view.findViewById(R.id.button6);
        viewOfferingHistoryButton = view.findViewById(R.id.button8);

        // Set click listeners for the buttons
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateCourse.class);
                startActivity(intent);
            }

        });

        editDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Edit_DeleteCourse.class);
                startActivity(intent);
            }
        });

        makeAvailableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle make available button click event
            }
        });

        acceptRejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle accept/reject button click event
            }
        });

        viewStudentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle view students button click event
            }
        });

        viewInstructorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle view instructors button click event
            }
        });

        viewOfferingHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle view offering history button click event
            }
        });

        return view;
    }


}
