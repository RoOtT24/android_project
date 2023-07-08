package com.example.myproject;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstructorProfilesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstructorProfilesFragment extends Fragment {
    private DataBaseHelper dbHelper;
    private SimpleCursorAdapter cursorAdapter;
    private ListView listViewInstructors;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InstructorProfilesFragment() {
        // Required empty public constructor
    }

    public static InstructorProfilesFragment newInstance(String param1, String param2) {
        InstructorProfilesFragment fragment = new InstructorProfilesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_instructor_profiles, container, false);

        // Get a reference to the ListView
        listViewInstructors = view.findViewById(R.id.listView_instructors);

        // Call the method to display the instructor profiles
        displayInstructorProfiles();

        return view;
            //return inflater.inflate(R.layout.fragment_instructor_profiles, container, false);
    }

    private void displayInstructorProfiles() {
        // Get an instance of the database helper
        DataBaseHelper dbHelper = new DataBaseHelper(getActivity().getApplicationContext(), "DATABASE", null, 1);

        // Call the getAllInstructors method to retrieve the instructor profiles
        List<Instructor> instructors = dbHelper.getAllInstructorsList();

        // Create an ArrayAdapter to populate the ListView
        ArrayAdapter<Instructor> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, instructors);

        // Set the adapter for the ListView
        listViewInstructors.setAdapter(adapter);
    }

    public void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}