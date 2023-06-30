package com.example.myproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class StudentProfilesFragment extends Fragment {
    private ListView listView;
    private ArrayAdapter<Student> adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudentProfilesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentProfilesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentProfilesFragment newInstance(String param1, String param2) {
        StudentProfilesFragment fragment = new StudentProfilesFragment();
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
        View view = inflater.inflate(R.layout.fragment_student_profiles, container, false);

        // Get a reference to the ListView
        listView = view.findViewById(R.id.listView_student);

        // Call the method to display the instructor profiles
        displayStudentsProfiles();

        return view;
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_student_profiles, container, false);
    }
    private void displayStudentsProfiles() {
        // Get an instance of the database helper
        DataBaseHelper dbHelper = new DataBaseHelper(getActivity().getApplicationContext(), "DATABASE", null, 1);

        // Call the getAllInstructors method to retrieve the instructor profiles
        List<Student> students = dbHelper.getAllStudents();

        // Create an ArrayAdapter to populate the ListView
        ArrayAdapter<Student> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, students);

        // Set the adapter for the ListView
        listView.setAdapter(adapter);
    }
}