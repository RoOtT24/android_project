//package com.example.myproject;
//
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ViewFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class ViewFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public ViewFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment View.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ViewFragment newInstance(String param1, String param2) {
//        ViewFragment fragment = new ViewFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Create the parent ViewGroup
//        LinearLayout parentLayout = new LinearLayout(requireContext());
//        parentLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        parentLayout.setOrientation(LinearLayout.VERTICAL);
//
//        // Create the TextView
//        TextView studentTextView = new TextView(requireContext());
//        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        textViewParams.setMargins(16, 16, 16, 16);
//        studentTextView.setLayoutParams(textViewParams);
//        parentLayout.addView(studentTextView);
//
//        // Retrieve all students from the database
//        DataBaseHelper dbHelper = new DataBaseHelper(requireContext());
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = dbHelper.getAllStudent();
//
//        StringBuilder studentsInfo = new StringBuilder();
//        if (cursor.moveToFirst()) {
//            do {
//                String firstName = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_FIRST_NAME));
//                String lastName = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_LAST_NAME));
//                String email = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_EMAIL));
//
//                // Append student information to the StringBuilder
//                studentsInfo.append("First Name: ").append(firstName).append("\n")
//                        .append("Last Name: ").append(lastName).append("\n")
//                        .append("Email: ").append(email).append("\n\n");
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//
//        // Set the student information to the TextView
//        studentTextView.setText(studentsInfo.toString());
//
//        return parentLayout;
//    }
//}