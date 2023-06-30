package com.example.myproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DATABASE";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER = "User";
    public static final String TABLE_COURSES = "Courses";
    private static final String TABLE_Admin = "Admin";
    private static final String TABLE_INSTRUCTOR = "instrcutor";
    public static final String COLUMN_USER_FIRST_NAME = "FirstName";
    public static final String COLUMN_USER_LAST_NAME = "LastName";
    private static final String COLUMN_USER_EMAIL = "Email";
    private static final String COLUMN_USER_PHONE = "Phone";
    private static final String COLUMN_USER_ADDRESS = "Address";
    private static final String COLUMN_USER_DEGREE = "degree";
    private static final String COLUMN_USER_SPECIALIZATION = "specialization";
    private static final String COLUMN_USER_COURSES = "courses";

//////////////////////////
    public static final String COLUMN_USER_PASSWORD = "Password";

    public static final String COLUMN_USER_IMAGE = "Image";

    public static final String COLUMN_USER_COURSEID = "courseId";
    public static final String COLUMN_USER_TITLE = "TITLE";
    public static final String COLUMN_USER_MAINTOPICES = "mainTopics";
    public static final String COLUMN_USER_PREEQUISITES = "prerequisites";

//    public static final String COLUMN_INSTRUCTOR_NAME = "instructor_name";

    public static final String COLUMN_REGISTRATION_DEADLINE = "registration_deadline";

    public static final String COLUMN_START_DATE = "start_date";

//    public static final String COLUMN_END_DATE = "end_date";

    public static final String COLUMN_COURSE_SCHEDULE = "course_schedule";

    public static final String COLUMN_VENUE = "venue";

    /////////////////////////////////////

    public  static final String TABLE_ENROLL = "enroll";

    private static final String COLUMN_ENROLL_ID = "enroll_id";
    ////////////////////////////////////

    private String COLUMN_ACCPETED = "accepted";

    //////////////////////////

    private String TABLE_OFFERING = "offering";
    private String COLUMN_OFFERING_ID = "offer_id";

    ///////////////////////////////////////



    public DataBaseHelper(Context context, String database, Object o, int i) {
        super(context, "DATABASE", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_OFFERING_TABLE = "CREATE TABLE " + TABLE_OFFERING +
                "(" +
                COLUMN_OFFERING_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_USER_COURSEID + " INTEGER NOT NULL," +
                COLUMN_REGISTRATION_DEADLINE + " INTEGER," +
                COLUMN_START_DATE+" INTEGER,"+
                COLUMN_COURSE_SCHEDULE+" TEXT,"+
                COLUMN_VENUE+" TEXT"+
                ")";
        db.execSQL(CREATE_OFFERING_TABLE);

        String CREATE_COURSE_TABLE = "CREATE TABLE " + TABLE_COURSES +
                "(" +
                COLUMN_USER_COURSEID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USER_TITLE + " TEXT NOT NULL," +
                COLUMN_USER_MAINTOPICES + " TEXT NOT NULL," +
                COLUMN_USER_PREEQUISITES + " TEXT," +
                COLUMN_USER_IMAGE + " BLOB" +
                ")";
        db.execSQL(CREATE_COURSE_TABLE);

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER +
                "(" +
                COLUMN_USER_FIRST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_LAST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_EMAIL + " TEXT PRIMARY KEY NOT NULL," +
                COLUMN_USER_PHONE + " TEXT," +
                COLUMN_USER_ADDRESS + " TEXT," +
                COLUMN_USER_PASSWORD + " TEXT NOT NULL," +
                COLUMN_USER_IMAGE + " BLOB," +
                COLUMN_ACCPETED+" INTEGER DEFAULT 0 CHECK("+COLUMN_ACCPETED+" IN (0, 1))" + // MAKE IT BOOLEAN
                ")";
        db.execSQL(CREATE_USER_TABLE);


        String CREATE_Admin_TABLE = "CREATE TABLE " + TABLE_Admin +
                "(" +
                COLUMN_USER_FIRST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_LAST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_EMAIL + " TEXT PRIMARY KEY NOT NULL," +
                COLUMN_USER_PASSWORD + " TEXT NOT NULL," +
                COLUMN_USER_IMAGE + " BLOB," +
                COLUMN_ACCPETED+" INTEGER DEFAULT 0 CHECK("+COLUMN_ACCPETED+" IN (0, 1))" + // MAKE IT BOOLEAN
                ")";
        db.execSQL(CREATE_Admin_TABLE);

        String CREATE_INSTRUCTOR_TABLE = "CREATE TABLE " + TABLE_INSTRUCTOR +
                "(" +
                COLUMN_USER_FIRST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_LAST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_EMAIL + " TEXT PRIMARY KEY NOT NULL," +
                COLUMN_USER_PHONE + " TEXT," +
                COLUMN_USER_COURSES + " TEXT NOT NULL," +
                COLUMN_USER_SPECIALIZATION + " TEXT NOT NULL,"+
                COLUMN_USER_DEGREE + " TEXT," +
                COLUMN_USER_ADDRESS + " TEXT," +
                COLUMN_USER_PASSWORD + " TEXT NOT NULL," +
                COLUMN_USER_IMAGE + " BLOB," +
                COLUMN_ACCPETED+" INTEGER DEFAULT 0 CHECK("+COLUMN_ACCPETED+" IN (0, 1))" + // MAKE IT BOOLEAN
                ")";
        db.execSQL(CREATE_INSTRUCTOR_TABLE);

        String CREATE_ENROLL_TABLE = "CREATE TABLE "+TABLE_ENROLL+
                " ("
                +COLUMN_ENROLL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_USER_EMAIL+" TEXT NOT NULL,"
                +COLUMN_OFFERING_ID+" INT NOT NULL"+
                ")";
        db.execSQL(CREATE_ENROLL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Admin);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFERING);
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

    public void insertCourse(Courses course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_TITLE, course.gettitle());
        values.put(COLUMN_USER_MAINTOPICES, convertArrayToString(course.getMainTopics()));
        values.put(COLUMN_USER_PREEQUISITES, convertArrayToString(course.getPrerequisites()));
        values.put(COLUMN_USER_IMAGE, course.getPhotoUrl());
        db.insert(TABLE_COURSES, null, values);
        db.close();
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


    public Cursor getAdminByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
//        return db.query(TABLE_USER, null, COLUMN_USER_EMAIL + "=?", new String[]{email}, null, null, null);
        return db.rawQuery("SELECT * FROM "+TABLE_Admin+" WHERE "+COLUMN_USER_EMAIL+" =?", new String[]{email});

    }


    public Cursor getInstructorByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
//        return db.query(TABLE_USER, null, COLUMN_USER_EMAIL + "=?", new String[]{email}, null, null, null);
        return db.rawQuery("SELECT * FROM "+TABLE_INSTRUCTOR+" WHERE "+COLUMN_USER_EMAIL+" =?", new String[]{email});

    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query TABLE_USER
        Cursor userCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + "=?", new String[]{email});
        if (userCursor.getCount() > 0) {
            userCursor.close();
            return true;
        }
        userCursor.close();

        // Query TABLE_Admin
        Cursor adminCursor = db.rawQuery("SELECT * FROM " + TABLE_Admin + " WHERE " + COLUMN_USER_EMAIL + "=?", new String[]{email});
        if (adminCursor.getCount() > 0) {
            adminCursor.close();
            return true;
        }
        adminCursor.close();

        // Query TABLE_INSTRUCTOR
        Cursor instructorCursor = db.rawQuery("SELECT * FROM " + TABLE_INSTRUCTOR + " WHERE " + COLUMN_USER_EMAIL + "=?", new String[]{email});
        if (instructorCursor.getCount() > 0) {
            instructorCursor.close();
            return true;
        }
        instructorCursor.close();

        return false;
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
    public Courses getCourseData(String courseTitle) {
        Courses courseData = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        try {
            // Query the database for the course data based on the title
            cursor = db.rawQuery("SELECT * FROM " + TABLE_COURSES + " WHERE " + COLUMN_USER_TITLE + " = ?", new String[]{courseTitle});

            if (cursor.moveToFirst()) {
                // Extract the data from the cursor
               // int idIndex = cursor.getColumnIndex(String.valueOf(COLUMN_USER_COURSEID));
                int titleIndex = cursor.getColumnIndex(COLUMN_USER_TITLE);
                int topicsIndex = cursor.getColumnIndex(COLUMN_USER_MAINTOPICES);
                int prerequisitesIndex = cursor.getColumnIndex(COLUMN_USER_PREEQUISITES);
                int photoUrlIndex = cursor.getColumnIndex(COLUMN_USER_IMAGE);

                // Create a new CourseData object with the retrieved data
              //  int courseId = cursor.getInt(idIndex);
                String title = cursor.getString(titleIndex);
                String topics = cursor.getString(topicsIndex);
                String prerequisites = cursor.getString(prerequisitesIndex);
                String photoUrl = cursor.getString(photoUrlIndex);

                courseData = new Courses( title, convertStringToArray(topics), convertStringToArray(prerequisites), photoUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return courseData;
    }
    public String[] convertStringToArray(String str) {
        String[] array = str.split(",");
        return array;
    }

    public void updateCourseData(Courses course) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_TITLE, course.gettitle());
        contentValues.put(COLUMN_USER_MAINTOPICES, convertArrayToString(course.getMainTopics()));
        contentValues.put(COLUMN_USER_PREEQUISITES, convertArrayToString(course.getPrerequisites()));
        contentValues.put(COLUMN_USER_IMAGE, course.getPhotoUrl());
        sqLiteDatabase.update("TABLE_COURSES", contentValues, "CourseID = ?", new String[]{String.valueOf(course.getCourseId())});
        sqLiteDatabase.close();
    }

    public void deleteCourse(String courseName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSES, COLUMN_USER_TITLE + " = ?", new String[]{courseName});
        db.close();
    }


    public void addOffer(Offer offer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_COURSEID, offer.getCourseId());
        values.put(COLUMN_USER_EMAIL, offer.getInstructorEmail());
        values.put(COLUMN_REGISTRATION_DEADLINE, persistDate(offer.getRegistrationDeadline()));
        values.put(COLUMN_START_DATE, persistDate(offer.getCourseStartDate()));
        values.put(COLUMN_COURSE_SCHEDULE, offer.getCourseSchedule());
        values.put(COLUMN_VENUE, offer.getVenue());
        db.insert(TABLE_OFFERING, null, values);
        db.close();
    }

    public static Long persistDate(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    public Cursor getOfferingStudents(int offeringId){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_OFFERING + "INNER JOIN "+ TABLE_USER
                + " ON "+ COLUMN_USER_EMAIL +" WHERE "+COLUMN_OFFERING_ID+ "=?", new String[]{Integer.toString(offeringId)});
    }

    public void setAccepted(String userEmail){
        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        switch(getUserType(userEmail)){
            case 1:
                query = "UPDATE "+TABLE_USER+" SET "+COLUMN_ACCPETED+" = 1 WHERE "+COLUMN_USER_EMAIL+" ='"+userEmail+"'";
                db.execSQL(query);
                break;
            case 2:
                query = "UPDATE "+TABLE_Admin+" SET "+COLUMN_ACCPETED+" = 1 WHERE "+COLUMN_USER_EMAIL+" ='"+userEmail+"'";
                db.execSQL(query);
                break;
            case 3:
                query = "UPDATE "+TABLE_INSTRUCTOR+" SET "+COLUMN_ACCPETED+" = 1 WHERE "+COLUMN_USER_EMAIL+" ='"+userEmail+"'";
                db.execSQL(query);
                break;
            default:
                System.out.println("no user found!");

        }
        db.close();
    }

    public Cursor getOfferingInstructor(int courseId){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_INSTRUCTOR + "INNER JOIN "+ TABLE_OFFERING
                + " ON "+ COLUMN_USER_EMAIL +" WHERE "+COLUMN_USER_COURSEID+ "=?", new String[]{Integer.toString(courseId)});
    }


        public Cursor getStudentsByCourseId(int courseId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_COURSES + " LIKE '%" + courseId + "%'";
        return db.rawQuery(query, null);
    }
    public List<String> getAllCourses() {
        List<String> courseList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT " + COLUMN_USER_TITLE + " FROM " + TABLE_COURSES;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String courseName = null;
                try {
                    int columnIndex = cursor.getColumnIndex(COLUMN_USER_TITLE);
                    if (columnIndex >= 0) {
                        courseName = cursor.getString(columnIndex);
                    } else {
                        // Handle the case when the column index is -1 (column not found)
                        // You can choose to assign a default value or take appropriate action here
                    }
                } catch (Exception e) {
                    // Handle any other exceptions that may occur during the retrieval of the course name
                    e.printStackTrace();
                }
               // String courseName = cursor.getString(cursor.getColumnIndex(COLUMN_USER_TITLE));
                courseList.add(courseName);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return courseList;
    }
    public int getCourseId(String courseName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int courseId = -1; // Default value if course not found

        Cursor cursor = null;
        try {
            String[] projection = {COLUMN_USER_COURSEID};
            String selection = COLUMN_USER_TITLE + "=?";
            String[] selectionArgs = {courseName};

            cursor = db.query(TABLE_COURSES, projection, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                try {
                    int columnIndex = cursor.getColumnIndex(COLUMN_USER_TITLE);
                    if (columnIndex >= 0) {
                        courseId = cursor.getInt(columnIndex);
                    } else {
                        // Handle the case when the column index is -1 (column not found)
                        // You can choose to assign a default value or take appropriate action here
                    }
                } catch (Exception e) {
                    // Handle any other exceptions that may occur during the retrieval of the course name
                    e.printStackTrace();
                }
              //  courseId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_COURSEID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return courseId;
    }

    public List<Instructor> getAllInstructors() {
        List<Instructor> instructors = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_INSTRUCTOR, null);

            if (cursor.moveToFirst()) {
                do {
                    String firstName = null;
                    String lastName = null;
                    String email = null;
                    String phone = null;
                    String address = null;
                    String degree = null;
                    String specialization = null;
                    String[] courses = null;
                    String password = null;
                    byte[] image = null;

                    int firstNameColumnIndex = cursor.getColumnIndex(COLUMN_USER_FIRST_NAME);
                    if (firstNameColumnIndex >= 0) {
                        firstName = cursor.getString(firstNameColumnIndex);
                    }

                    int lastNameColumnIndex = cursor.getColumnIndex(COLUMN_USER_LAST_NAME);
                    if (lastNameColumnIndex >= 0) {
                        lastName = cursor.getString(lastNameColumnIndex);
                    }

                    int emailColumnIndex = cursor.getColumnIndex(COLUMN_USER_EMAIL);
                    if (emailColumnIndex >= 0) {
                        email = cursor.getString(emailColumnIndex);
                    }

                    int phoneColumnIndex = cursor.getColumnIndex(COLUMN_USER_PHONE);
                    if (phoneColumnIndex >= 0) {
                        phone = cursor.getString(phoneColumnIndex);
                    }

                    int addressColumnIndex = cursor.getColumnIndex(COLUMN_USER_ADDRESS);
                    if (addressColumnIndex >= 0) {
                        address = cursor.getString(addressColumnIndex);
                    }

                    int degreeColumnIndex = cursor.getColumnIndex(COLUMN_USER_DEGREE);
                    if (degreeColumnIndex >= 0) {
                        degree = cursor.getString(degreeColumnIndex);
                    }

                    int specializationColumnIndex = cursor.getColumnIndex(COLUMN_USER_SPECIALIZATION);
                    if (specializationColumnIndex >= 0) {
                        specialization = cursor.getString(specializationColumnIndex);
                    }

                    int coursesColumnIndex = cursor.getColumnIndex(COLUMN_USER_COURSES);
                    if (coursesColumnIndex >= 0) {
                        courses = convertStringToArray(cursor.getString(coursesColumnIndex));
                    }

                    int passwordColumnIndex = cursor.getColumnIndex(COLUMN_USER_PASSWORD);
                    if (passwordColumnIndex >= 0) {
                        password = cursor.getString(passwordColumnIndex);
                    }

                    int imageColumnIndex = cursor.getColumnIndex(COLUMN_USER_IMAGE);
                    if (imageColumnIndex >= 0) {
                        image = cursor.getBlob(imageColumnIndex);
                    }

                    // Create an Instructor object with the retrieved data
                    Instructor instructor = new Instructor(firstName, lastName, email, phone, address, password, specialization, degree, courses);


                    // Add the instructor to the list
                    instructors.add(instructor);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return instructors;
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        try {
            // Query the database for all student records
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);

            if (cursor.moveToFirst()) {
                do {
                    String firstName = null;
                    int firstNameIndex = cursor.getColumnIndex(COLUMN_USER_FIRST_NAME);
                    if (firstNameIndex >= 0) {
                        firstName = cursor.getString(firstNameIndex);
                    }

                    String lastName = null;
                    int lastNameIndex = cursor.getColumnIndex(COLUMN_USER_LAST_NAME);
                    if (lastNameIndex >= 0) {
                        lastName = cursor.getString(lastNameIndex);
                    }

                    String email = null;
                    int emailIndex = cursor.getColumnIndex(COLUMN_USER_EMAIL);
                    if (emailIndex >= 0) {
                        email = cursor.getString(emailIndex);
                    }

                    String phone = null;
                    int phoneIndex = cursor.getColumnIndex(COLUMN_USER_PHONE);
                    if (phoneIndex >= 0) {
                        phone = cursor.getString(phoneIndex);
                    }

                    String address = null;
                    int addressIndex = cursor.getColumnIndex(COLUMN_USER_ADDRESS);
                    if (addressIndex >= 0) {
                        address = cursor.getString(addressIndex);
                    }

                    String password = null;
                    int passwordIndex = cursor.getColumnIndex(COLUMN_USER_PASSWORD);
                    if (passwordIndex >= 0) {
                        password = cursor.getString(passwordIndex);
                    }

                    byte[] image = null;
                    int imageIndex = cursor.getColumnIndex(COLUMN_USER_IMAGE);
                    if (imageIndex >= 0) {
                        image = cursor.getBlob(imageIndex);
                    }

                    // Create a new Student object with the retrieved data
                    Student student = new Student(firstName, lastName, email, phone, address, password, image);
                    // Add the student to the list
                    studentList.add(student);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return studentList;
    }

}
