package com.example.myproject;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstructorProfilesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstructorProfilesFragment extends Fragment {
    private DataBaseHelper dbHelper;
    private SimpleCursorAdapter cursorAdapter;
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InstructorProfilesFragment.
     */
    // TODO: Rename and change types and number of parameters
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

      //  dbHelper = new DataBaseHelper(getActivity());
        ListView listViewInstructors = view.findViewById(R.id.listView_instructors);

        // Get the cursor containing all instructor profiles
//        Cursor cursor = dbHelper.getAllInstructors();
//
//        // Specify the columns to display in the ListView
//        String[] columns = new String[] {
//                DataBaseHelper.COLUMN_USER_FIRST_NAME,
//                DataBaseHelper.COLUMN_USER_LAST_NAME,
//                DataBaseHelper.COLUMN_USER_EMAIL
//        };

        // Specify the XML components where the cursor columns will be displayed
//        int[] to = new int[] {
//                R.id.textView_firstName,
//                R.id.textView_lastName,
//                R.id.textView_email
//        };

        // Create a SimpleCursorAdapter to map the cursor data to the ListView
      //  cursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.fragment_instructor_profiles, cursor, columns, to, 0);

        // Set the adapter for the ListView
        listViewInstructors.setAdapter(cursorAdapter);

        return view;        //return inflater.inflate(R.layout.fragment_instructor_profiles, container, false);
    }
    public void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}