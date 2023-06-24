package com.example.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DATABASE";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER = "User";
    private static final String TABLE_Admin = "Admin";
    private static final String TABLE_INSTRUCTOR = "instrcutor";
    private static final String COLUMN_USER_FIRST_NAME = "FirstName";
    private static final String COLUMN_USER_LAST_NAME = "LastName";
    private static final String COLUMN_USER_EMAIL = "Email";
    private static final String COLUMN_USER_PHONE = "Phone";
    private static final String COLUMN_USER_ADDRESS = "Address";
    private static final String COLUMN_USER_DEGREE = "degree";
    private static final String COLUMN_USER_SPECIALIZATION = "specialization";
    private static final String COLUMN_USER_COURSES = "courses";


    public static final String COLUMN_USER_PASSWORD = "Password";

    public static final String COLUMN_USER_IMAGE = "Image";

    public DataBaseHelper(Context context, String database, Object o, int i) {
        super(context, "ANDROID_DATABASE1", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER +
                "(" +
                COLUMN_USER_FIRST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_LAST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_EMAIL + " TEXT NOT NULL," +
                COLUMN_USER_PHONE + " TEXT," +
                COLUMN_USER_ADDRESS + " TEXT," +
                COLUMN_USER_PASSWORD + " TEXT NOT NULL," +
                COLUMN_USER_IMAGE + " BLOB" +
                ")";
        db.execSQL(CREATE_USER_TABLE);


        String CREATE_Admin_TABLE = "CREATE TABLE " + TABLE_Admin +
                "(" +
                COLUMN_USER_FIRST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_LAST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_EMAIL + " TEXT NOT NULL," +
                COLUMN_USER_PASSWORD + " TEXT NOT NULL" +
                ")";
        db.execSQL(CREATE_Admin_TABLE);

        String CREATE_INSTRUCTOR_TABLE = "CREATE TABLE " + TABLE_INSTRUCTOR +
                "(" +
                COLUMN_USER_FIRST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_LAST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_EMAIL + " TEXT NOT NULL," +
                COLUMN_USER_PHONE + " TEXT," +
                COLUMN_USER_COURSES + "TEXT NOT NULL," +
                COLUMN_USER_SPECIALIZATION + "TEXT NOT NULL,"+
                COLUMN_USER_DEGREE + "TEXT," +
                COLUMN_USER_ADDRESS + " TEXT," +
                COLUMN_USER_PASSWORD + " TEXT NOT NULL" +
                ")";
        db.execSQL(CREATE_INSTRUCTOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Admin);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTOR);
        onCreate(db);
    }

    public void insertUser(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FIRST_NAME, student.getFirstName());
        values.put(COLUMN_USER_LAST_NAME, student.getLastName());

        values.put(COLUMN_USER_IMAGE, student.getImage());


        if (isValidEmail(student.getEmail())) {
            values.put(COLUMN_USER_EMAIL, student.getEmail());
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }

        values.put(COLUMN_USER_PHONE, student.getPhone());
        values.put(COLUMN_USER_ADDRESS, student.getAddress());

        if (isValidPassword(student.getPassword())) {
            values.put(COLUMN_USER_PASSWORD, student.getPassword());
        } else {
            throw new IllegalArgumentException("Invalid password format");
        }

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void insertAdmin(Admin admin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FIRST_NAME, admin.getFirstName());
        values.put(COLUMN_USER_LAST_NAME, admin.getLastName());

        if (isValidEmail(admin.getEmail())) {
            values.put(COLUMN_USER_EMAIL, admin.getEmail());
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (isValidPassword(admin.getPassword())) {
            values.put(COLUMN_USER_PASSWORD, admin.getPassword());
        } else {
            throw new IllegalArgumentException("Invalid password format");
        }

        db.insert(TABLE_Admin, null, values);
        db.close();
    }

    public void insertInstructor(Instructor instructor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FIRST_NAME, instructor.getFirstName());
        values.put(COLUMN_USER_LAST_NAME, instructor.getLastName());

        if (isValidEmail(instructor.getEmail())) {
            values.put(COLUMN_USER_EMAIL, instructor.getEmail());
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }

        values.put(COLUMN_USER_PHONE, instructor.getPhone());
        values.put(COLUMN_USER_ADDRESS, instructor.getAddress());

        values.put(COLUMN_USER_DEGREE, instructor.getDegree());
        values.put(COLUMN_USER_SPECIALIZATION, instructor.getSpecialization());
        values.put(COLUMN_USER_COURSES, instructor.getCourses().toString());

        if (isValidPassword(instructor.getPassword())) {
            values.put(COLUMN_USER_PASSWORD, instructor.getPassword());
        } else {
            throw new IllegalArgumentException("Invalid password format");
        }

        db.insert(TABLE_USER, null, values);
        db.close();
//        Toast.makeText(SignupActivity.class, "", Toast.LENGTH_SHORT).show();
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return Pattern.matches(emailPattern, email);
    }

    private boolean isValidPassword(String password) {
        // Password must contain at least one number, one lowercase letter, one uppercase letter
        // and be between 8 and 15 characters long
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,15}$";
        return Pattern.matches(passwordPattern, password);
    }

    public Cursor getAllStudent() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_USER, null);
    }

    public Cursor getStudentByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
//        return db.query(TABLE_USER, null, COLUMN_USER_EMAIL + "=?", new String[]{email}, null, null, null);
        return db.rawQuery("SELECT * FROM "+TABLE_USER+" WHERE "+COLUMN_USER_EMAIL+" =?", new String[]{email});

    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_USER, null, COLUMN_USER_EMAIL + "=?", new String[]{email}, null, null, null);
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_USER+" WHERE "+COLUMN_USER_EMAIL+" =?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public int getUserType(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query TABLE_USER
        Cursor userCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + "=?", new String[]{email});
        if (userCursor.moveToFirst()) {
            return 1; // User type 1
        }

        // Query TABLE_Admin
        Cursor adminCursor = db.rawQuery("SELECT * FROM " + TABLE_Admin + " WHERE " + COLUMN_USER_EMAIL + "=?", new String[]{email});
        if (adminCursor.moveToFirst()) {
            return 2; // User type 2 (Admin)
        }

        // Query TABLE_INSTRUCTOR
        Cursor instructorCursor = db.rawQuery("SELECT * FROM " + TABLE_INSTRUCTOR + " WHERE " + COLUMN_USER_EMAIL + "=?", new String[]{email});
        if (instructorCursor.moveToFirst()) {
            return 3; // User type 3 (Instructor)
        }

        // No user found, return default value
        return -1; // Unknown user type
    }

}
