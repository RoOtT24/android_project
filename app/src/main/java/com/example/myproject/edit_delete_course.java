package com.example.myproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.os.Message;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.se.omapi.Session;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class edit_delete_course extends Fragment {

    Spinner courseSpinner;
    ArrayList<String> courseTitles = new ArrayList<>();
    DataBaseHelper dbHelper;
    EditText editTextTitle;
    EditText editTextMainTopics;
    EditText editTextPrerequisites;

    EditText idEditText;
    ImageView courseImage;
    Button update;
    Button delete;

    Bitmap bitmap;

    int id;

    ActivityResultLauncher<Intent> activityResultLauncher;

    public edit_delete_course() {
        // Required empty public constructor
    }

    public static edit_delete_course newInstance(String param1, String param2) {
        edit_delete_course fragment = new edit_delete_course();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DataBaseHelper(getActivity(), "DATABASE", null, 1);


        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                        courseImage.setImageBitmap(bitmap);
//                        imageChanged[0] = true;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


    }

    private void refreshSpinner() {
        courseTitles.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DataBaseHelper.COLUMN_USER_TITLE + " FROM " + DataBaseHelper.TABLE_COURSES, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(DataBaseHelper.COLUMN_USER_TITLE);
            if (columnIndex >= 0) {
                do {
                    String title = cursor.getString(columnIndex);
                    courseTitles.add(title);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, courseTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(adapter);
    }


    private String convertArrayToString(String[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            stringBuilder.append(array[i]);
            if (i < array.length - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    public String[] convertStringToArray(String str) {
        String[] array = str.split(",");
        return array;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_edit_delete_course, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialize the Spinner and ArrayList
        courseSpinner = getActivity().findViewById(R.id.courseSpinner);
        courseTitles = new ArrayList<>();

        editTextTitle = getActivity().findViewById(R.id.emailSechedule);
        editTextMainTopics = getActivity().findViewById(R.id.topics_onCreate);
        editTextPrerequisites = getActivity().findViewById(R.id.Prerequisites);
        idEditText = getActivity().findViewById(R.id.course_id_edit_text);
        courseImage = getActivity().findViewById(R.id.course_Image_on_update_fragment);
        update = getActivity().findViewById(R.id.ceate_course);
        delete = getActivity().findViewById(R.id.Delete);

        refreshSpinner();

        courseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editTextTitle = getActivity().findViewById(R.id.editTextCourseId);
                editTextMainTopics = getActivity().findViewById(R.id.topics_onCreate);
                editTextPrerequisites = getActivity().findViewById(R.id.Prerequisites);
                courseImage = getActivity().findViewById(R.id.course_Image_on_update_fragment);
                update = getActivity().findViewById(R.id.ceate_course);
                delete = getActivity().findViewById(R.id.Delete);
                idEditText = getActivity().findViewById(R.id.course_id_edit_text);


                String selectedCourse = courseTitles.get(position);
                // Retrieve course data from the database based on the selected course title
                Courses courseData = dbHelper.getCourseData(selectedCourse);
                if (courseData != null) {
                    String topics = convertArrayToString(courseData.getMainTopics());
                    String Prerequisites = convertArrayToString(courseData.getPrerequisites());
                    // Set the retrieved data to the EditText fields
                    Toast.makeText(getActivity(), convertArrayToString(courseData.getMainTopics()), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), convertArrayToString(courseData.getPrerequisites()), Toast.LENGTH_SHORT).show();
                    idEditText.setText(Integer.toString(courseData.getId()));
                    editTextTitle.setText(courseData.gettitle());
                    editTextMainTopics.setText(convertArrayToString(courseData.getMainTopics()));
                    editTextPrerequisites.setText(convertArrayToString(courseData.getPrerequisites()));
                    courseImage.setImageBitmap(BitmapFactory.decodeByteArray(courseData.getImage(), 0, courseData.getImage().length));
                } else {
                    Toast.makeText(getActivity(), "no data found", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                editTextTitle.setText(" ");
                editTextMainTopics.setText(" ");
                editTextPrerequisites.setText(" ");
                courseImage.setImageDrawable(null);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the updated values from the EditText fields
                String title = editTextTitle.getText().toString();
                String mainTopics = editTextMainTopics.getText().toString();
                String prerequisites = editTextPrerequisites.getText().toString();
//                String photoUrl = editTextPhotoUrl.getText().toString();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int id = Integer.parseInt(idEditText.getText().toString());

                byte[] bytes;

                if (bitmap == null) {
                    bitmap = ((BitmapDrawable) courseImage.getDrawable()).getBitmap();
                }

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                bytes = byteArrayOutputStream.toByteArray();

                // Update the course data in the database
                Courses courseData = new Courses(id, title, convertStringToArray(mainTopics), convertStringToArray(prerequisites), bytes);
                dbHelper.updateCourseData(courseData);

                // Show a success message or perform any other necessary actions
                Toast.makeText(getActivity(), "Course updated successfully", Toast.LENGTH_SHORT).show();
                if (dbHelper.isCourseOffered(courseData.getId())) {
                    List<String> students = dbHelper.getEnrolledStudents(courseData.getId());
                    for (String email : students) {
                        // Generate notification for the user
                        generateNotification(getActivity(), email, courseData.getTitle());
                    }
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected course title
                String selectedCourse = courseSpinner.getSelectedItem().toString();

                // Delete the course from the database
                dbHelper.deleteCourse(selectedCourse);

                // Show a success message or perform any other necessary actions
                Toast.makeText(getActivity(), "Course deleted successfully", Toast.LENGTH_SHORT).show();

                // Optionally, you can refresh the spinner or navigate back to a different activity
                // Refresh the spinner
                refreshSpinner();

                // Navigate back to a different activity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to generate a notification for a specific user
//    private void generateNotification(Context context, String email, String courseTitle) {
//        // Create a notification builder
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
//                .setContentTitle("Course Update")
//                .setContentText("Course " + courseTitle + " has an update.")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setAutoCancel(true);
//
//        // Show the notification
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//
//        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//
//            return;
//        }
//        notificationManager.notify(email.hashCode(), builder.build());
//    }


    private void generateNotification(Context context, String email, String courseTitle) {
        // Create the email intent
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + email));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Course Update");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Course " + courseTitle + " has an update.");

        // Check if there's an email client available to handle the intent
        if (emailIntent.resolveActivity(context.getPackageManager()) != null) {
            // Start the email intent
            context.startActivity(emailIntent);

            // Show a success message or perform any other necessary actions
            Toast.makeText(context, "Opening email client...", Toast.LENGTH_SHORT).show();
        } else {
            // Handle the case where no email client is available
            Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show();
        }
    }



    }

