package com.example.myproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DATABASE";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USER = "User";
    public static final String TABLE_COURSES = "Courses";
    public static final String TABLE_Admin = "Admin";
    public static final String TABLE_INSTRUCTOR = "instrcutor";
    public static final String COLUMN_USER_FIRST_NAME = "FirstName";
    public static final String COLUMN_USER_LAST_NAME = "LastName";
    public static final String COLUMN_USER_EMAIL = "Email";
    public static final String COLUMN_USER_PHONE = "Phone";
    public static final String COLUMN_USER_ADDRESS = "Address";
    public static final String COLUMN_USER_DEGREE = "degree";
    public static final String COLUMN_USER_SPECIALIZATION = "specialization";
    public static final String COLUMN_USER_COURSES = "courses";

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

    public static final String COLUMN_ENROLL_ID = "enroll_id";
    ////////////////////////////////////

    public String COLUMN_ACCPETED = "accepted";

    //////////////////////////

    public String TABLE_OFFERING = "offering";
    public String COLUMN_OFFERING_ID = "offer_id";

    ///////////////////////////////////////



    public DataBaseHelper(Context context, String database, Object o, int i) {
        super(context, "DATABASE", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_OFFERING_TABLE = "CREATE TABLE " + TABLE_OFFERING +
                "(" +
                COLUMN_OFFERING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USER_COURSEID + " INTEGER NOT NULL," +
                COLUMN_REGISTRATION_DEADLINE + " INTEGER," +
                COLUMN_START_DATE + " INTEGER," +
                COLUMN_COURSE_SCHEDULE + " TEXT," +
                COLUMN_VENUE + " TEXT," +
                "FOREIGN KEY (" + COLUMN_USER_COURSEID + ") REFERENCES " + TABLE_COURSES + "(" + COLUMN_USER_COURSEID + ")" +
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
                COLUMN_USER_EMAIL + " TEXT PRIMARY KEY NOT NULL," +
                COLUMN_USER_FIRST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_LAST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_PHONE + " TEXT," +
                COLUMN_USER_ADDRESS + " TEXT," +
                COLUMN_USER_PASSWORD + " TEXT NOT NULL," +
                COLUMN_USER_IMAGE + " BLOB" +
                ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_Admin_TABLE = "CREATE TABLE " + TABLE_Admin +
                "(" +
                COLUMN_USER_EMAIL + " TEXT PRIMARY KEY NOT NULL," +
                COLUMN_USER_FIRST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_LAST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_PASSWORD + " TEXT NOT NULL," +
                COLUMN_USER_IMAGE + " BLOB" +

                ")";
        db.execSQL(CREATE_Admin_TABLE);

        String CREATE_INSTRUCTOR_TABLE = "CREATE TABLE " + TABLE_INSTRUCTOR +
                "(" +
                COLUMN_USER_EMAIL + " TEXT PRIMARY KEY NOT NULL," +
                COLUMN_USER_FIRST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_LAST_NAME + " TEXT NOT NULL," +
                COLUMN_USER_PHONE + " TEXT," +
                COLUMN_USER_COURSES + " TEXT NOT NULL," +
                COLUMN_USER_SPECIALIZATION + " TEXT NOT NULL," +
                COLUMN_USER_DEGREE + " TEXT," +
                COLUMN_USER_ADDRESS + " TEXT," +
                COLUMN_USER_PASSWORD + " TEXT NOT NULL," +
                COLUMN_USER_IMAGE + " BLOB" +
                ")";
        db.execSQL(CREATE_INSTRUCTOR_TABLE);

        String CREATE_ENROLL_TABLE = "CREATE TABLE " + TABLE_ENROLL +
                "(" +
                COLUMN_ENROLL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_EMAIL + " TEXT NOT NULL," +
                COLUMN_OFFERING_ID + " INT NOT NULL," +
                COLUMN_ACCPETED + " INTEGER DEFAULT 0 CHECK(" + COLUMN_ACCPETED + " IN (0, 1))," +
                "FOREIGN KEY (" + COLUMN_USER_EMAIL + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_EMAIL + ")," +
                "FOREIGN KEY (" + COLUMN_OFFERING_ID + ") REFERENCES " + TABLE_OFFERING + "(" + COLUMN_OFFERING_ID + ")" +
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENROLL);
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


    public Cursor getUnApprovedRegistrations(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ENROLL +" JOIN "+TABLE_USER+" ON "+COLUMN_USER_EMAIL+" WHERE "+COLUMN_ACCPETED+" =?";
        return db.rawQuery(query, new String[]{Integer.toString(0)});
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
        values.put(COLUMN_USER_IMAGE, admin.getImage());

        db.insert(TABLE_Admin, null, values);
        db.close();
    }

    public void removeEnroll(int enrollId){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+TABLE_ENROLL+" WHERE "+COLUMN_ENROLL_ID+" =?";
        db.rawQuery(query, new String[]{Integer.toString(enrollId)});
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
        values.put(COLUMN_USER_COURSES, convertArrayToString(instructor.getCourses()));

        if (isValidPassword(instructor.getPassword())) {
            values.put(COLUMN_USER_PASSWORD, instructor.getPassword());
        } else {
            throw new IllegalArgumentException("Invalid password format");
        }
        values.put(COLUMN_USER_IMAGE, instructor.getImage());

        db.insert(TABLE_INSTRUCTOR, null, values);
        db.close();
    }

    public void insertCourse(Courses course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_TITLE, course.gettitle());
        values.put(COLUMN_USER_MAINTOPICES, convertArrayToString(course.getMainTopics()));
        values.put(COLUMN_USER_PREEQUISITES, convertArrayToString(course.getPrerequisites()));
        values.put(COLUMN_USER_IMAGE, course.getImage());
        db.insert(TABLE_COURSES, null, values);
        db.close();
    }


    public String convertArrayToString(String[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            stringBuilder.append(array[i].trim());
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


    public Cursor getAllStudents() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_USER, null);
    }

    public Cursor getAllCoursesOfInstructor(String instructorEmail){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT " + TABLE_COURSES + ".*" +
                " FROM " + TABLE_COURSES +
                " JOIN " + TABLE_OFFERING + " ON " + TABLE_OFFERING + "." + COLUMN_USER_COURSEID + " = " + TABLE_COURSES + "." + COLUMN_USER_COURSEID +
                " JOIN " + TABLE_INSTRUCTOR + " ON " + TABLE_INSTRUCTOR + "." + COLUMN_USER_EMAIL + " = ?" +
                " WHERE " + COLUMN_USER_EMAIL + " = ?";
        return  sqLiteDatabase.rawQuery(query, new String[]{instructorEmail});
    }
    public Cursor getAllInstructors() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_INSTRUCTOR, null);
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
                int idIndex = cursor.getColumnIndex(String.valueOf(COLUMN_USER_COURSEID));
                int titleIndex = cursor.getColumnIndex(COLUMN_USER_TITLE);
                int topicsIndex = cursor.getColumnIndex(COLUMN_USER_MAINTOPICES);
                int prerequisitesIndex = cursor.getColumnIndex(COLUMN_USER_PREEQUISITES);
                int photoUrlIndex = cursor.getColumnIndex(COLUMN_USER_IMAGE);

                // Create a new CourseData object with the retrieved data
                int courseId = cursor.getInt(idIndex);
                String title = cursor.getString(titleIndex);
                String topics = cursor.getString(topicsIndex);
                String prerequisites = cursor.getString(prerequisitesIndex);
                byte[] photoUrl = cursor.getBlob(photoUrlIndex); //.getString(photoUrlIndex);
                System.out.println("topics : "+topics);
                courseData = new Courses(courseId, title, convertStringToArray(topics), convertStringToArray(prerequisites), photoUrl);
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
        for(int i = 0; i < array.length; ++i)
            array[i] = array[i].trim();
        return array;
    }

    public void updateCourseData(Courses course) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_TITLE, course.gettitle());
        contentValues.put(COLUMN_USER_MAINTOPICES, convertArrayToString(course.getMainTopics()));
        contentValues.put(COLUMN_USER_PREEQUISITES, convertArrayToString(course.getPrerequisites()));
        contentValues.put(COLUMN_USER_IMAGE, course.getImage());
        sqLiteDatabase.update(TABLE_COURSES, contentValues, COLUMN_USER_COURSEID+" = ?", new String[]{Integer.toString(course.getId())});
        sqLiteDatabase.close();
    }
    public void updateStudentData(Student student) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_EMAIL, student.getEmail());
        contentValues.put(COLUMN_USER_FIRST_NAME,student.getFirstName() );
        contentValues.put(COLUMN_USER_LAST_NAME, student.getLastName());
        contentValues.put(COLUMN_USER_PHONE, student.getPhone());
        contentValues.put(COLUMN_USER_ADDRESS, student.getAddress());
        contentValues.put(COLUMN_USER_PASSWORD, student.getPassword());
        contentValues.put(COLUMN_USER_IMAGE, student.getImage());
        sqLiteDatabase.update(TABLE_USER, contentValues, COLUMN_USER_EMAIL+" = ?", new String[]{student.getEmail()});
        sqLiteDatabase.close();
    }
    public void updateInstrucorData(Instructor instructor) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_EMAIL, instructor.getEmail());
        contentValues.put(COLUMN_USER_FIRST_NAME,instructor.getFirstName() );
        contentValues.put(COLUMN_USER_LAST_NAME, instructor.getLastName());
        contentValues.put(COLUMN_USER_PHONE, instructor.getPhone());
        contentValues.put(COLUMN_USER_ADDRESS, instructor.getAddress());
        contentValues.put(COLUMN_USER_PASSWORD, instructor.getPassword());
        contentValues.put(COLUMN_USER_IMAGE, instructor.getImage());
        contentValues.put(COLUMN_USER_COURSES, convertArrayToString(instructor.getCourses()));
        contentValues.put(COLUMN_USER_SPECIALIZATION, instructor.getSpecialization());
        contentValues.put(COLUMN_USER_DEGREE, instructor.getDegree());
      //  contentValues.put(COLUMN_ACCPETED, instructor.getaccpeted());
        sqLiteDatabase.update(TABLE_INSTRUCTOR, contentValues, COLUMN_USER_EMAIL+" = ?", new String[]{instructor.getEmail()});
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

    public Cursor getOfferingStudents(String courseTitle){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + TABLE_USER + ".*" +
                " FROM " + TABLE_USER +
                " JOIN " + TABLE_ENROLL + " ON " + TABLE_USER + "." + COLUMN_USER_EMAIL + " = " + TABLE_ENROLL + "." + COLUMN_USER_EMAIL +
                " JOIN " + TABLE_OFFERING + " ON " + TABLE_OFFERING + "." + COLUMN_OFFERING_ID + " = " + TABLE_ENROLL + "." + COLUMN_OFFERING_ID +
                " JOIN " + TABLE_COURSES + " ON " + TABLE_COURSES + "." + COLUMN_USER_COURSEID + " = " + TABLE_OFFERING + "." + COLUMN_USER_COURSEID +
                " WHERE " + TABLE_COURSES + "." + COLUMN_USER_TITLE + " = ?";

        return db.rawQuery(query, new String[]{courseTitle});
//        return db.rawQuery("SELECT * FROM " + TABLE_USER + " INNER JOIN (SELECT * FROM "+ TABLE_OFFERING
//                + " INNER JOIN "+TABLE_COURSES+" ON "+COLUMN_USER_TITLE+" INNER JOIN (SELECT * FROM "+TABLE_ENROLL+" WHERE)) ON "+ COLUMN_USER_EMAIL +" WHERE "+COLUMN_USER_TITLE+ " =?", new String[]{courseName});
 
    }

    public void setAccepted(int enrollId){
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        query = "UPDATE "+TABLE_ENROLL+" SET "+COLUMN_ACCPETED+" = 1 WHERE "+COLUMN_ENROLL_ID+" =?";
        db.rawQuery(query, new String[]{Integer.toString(enrollId)});

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
    public Cursor getAllCourses() {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_COURSES;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
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

//    public List<Instructor> getAllInstructors() {
//        List<Instructor> instructors = new ArrayList<>();
//
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = null;
//
//        try {
//            cursor = db.rawQuery("SELECT * FROM " + TABLE_INSTRUCTOR, null);
//
//            if (cursor.moveToFirst()) {
//                do {
//                    String firstName = null;
//                    String lastName = null;
//                    String email = null;
//                    String phone = null;
//                    String address = null;
//                    String degree = null;
//                    String specialization = null;
//                    String[] courses = null;
//                    String password = null;
//                    byte[] image = null;
//
//                    int firstNameColumnIndex = cursor.getColumnIndex(COLUMN_USER_FIRST_NAME);
//                    if (firstNameColumnIndex >= 0) {
//                        firstName = cursor.getString(firstNameColumnIndex);
//                    }
//
//                    int lastNameColumnIndex = cursor.getColumnIndex(COLUMN_USER_LAST_NAME);
//                    if (lastNameColumnIndex >= 0) {
//                        lastName = cursor.getString(lastNameColumnIndex);
//                    }
//
//                    int emailColumnIndex = cursor.getColumnIndex(COLUMN_USER_EMAIL);
//                    if (emailColumnIndex >= 0) {
//                        email = cursor.getString(emailColumnIndex);
//                    }
//
//                    int phoneColumnIndex = cursor.getColumnIndex(COLUMN_USER_PHONE);
//                    if (phoneColumnIndex >= 0) {
//                        phone = cursor.getString(phoneColumnIndex);
//                    }
//
//                    int addressColumnIndex = cursor.getColumnIndex(COLUMN_USER_ADDRESS);
//                    if (addressColumnIndex >= 0) {
//                        address = cursor.getString(addressColumnIndex);
//                    }
//
//                    int degreeColumnIndex = cursor.getColumnIndex(COLUMN_USER_DEGREE);
//                    if (degreeColumnIndex >= 0) {
//                        degree = cursor.getString(degreeColumnIndex);
//                    }
//
//                    int specializationColumnIndex = cursor.getColumnIndex(COLUMN_USER_SPECIALIZATION);
//                    if (specializationColumnIndex >= 0) {
//                        specialization = cursor.getString(specializationColumnIndex);
//                    }
//
//                    int coursesColumnIndex = cursor.getColumnIndex(COLUMN_USER_COURSES);
//                    if (coursesColumnIndex >= 0) {
//                        courses = convertStringToArray(cursor.getString(coursesColumnIndex));
//                    }
//
//                    int passwordColumnIndex = cursor.getColumnIndex(COLUMN_USER_PASSWORD);
//                    if (passwordColumnIndex >= 0) {
//                        password = cursor.getString(passwordColumnIndex);
//                    }
//
//                    int imageColumnIndex = cursor.getColumnIndex(COLUMN_USER_IMAGE);
//                    if (imageColumnIndex >= 0) {
//                        image = cursor.getBlob(imageColumnIndex);
//                    }
//
//                    // Create an Instructor object with the retrieved data
//                    Instructor instructor = new Instructor(firstName, lastName, email, phone, address, password, specialization, degree, courses);
//
//
//                    // Add the instructor to the list
//                    instructors.add(instructor);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//
//        return instructors;
//    }

//    public List<Student> getAllStudents() {
//        List<Student> studentList = new ArrayList<>();
//
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = null;
//
//        try {
//            // Query the database for all student records
//            cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);
//
//            if (cursor.moveToFirst()) {
//                do {
//                    String firstName = null;
//                    int firstNameIndex = cursor.getColumnIndex(COLUMN_USER_FIRST_NAME);
//                    if (firstNameIndex >= 0) {
//                        firstName = cursor.getString(firstNameIndex);
//                    }
//
//                    String lastName = null;
//                    int lastNameIndex = cursor.getColumnIndex(COLUMN_USER_LAST_NAME);
//                    if (lastNameIndex >= 0) {
//                        lastName = cursor.getString(lastNameIndex);
//                    }
//
//                    String email = null;
//                    int emailIndex = cursor.getColumnIndex(COLUMN_USER_EMAIL);
//                    if (emailIndex >= 0) {
//                        email = cursor.getString(emailIndex);
//                    }
//
//                    String phone = null;
//                    int phoneIndex = cursor.getColumnIndex(COLUMN_USER_PHONE);
//                    if (phoneIndex >= 0) {
//                        phone = cursor.getString(phoneIndex);
//                    }
//
//                    String address = null;
//                    int addressIndex = cursor.getColumnIndex(COLUMN_USER_ADDRESS);
//                    if (addressIndex >= 0) {
//                        address = cursor.getString(addressIndex);
//                    }
//
//                    String password = null;
//                    int passwordIndex = cursor.getColumnIndex(COLUMN_USER_PASSWORD);
//                    if (passwordIndex >= 0) {
//                        password = cursor.getString(passwordIndex);
//                    }
//
//                    byte[] image = null;
//                    int imageIndex = cursor.getColumnIndex(COLUMN_USER_IMAGE);
//                    if (imageIndex >= 0) {
//                        image = cursor.getBlob(imageIndex);
//                    }
//
//                    // Create a new Student object with the retrieved data
//                    Student student = new Student(firstName, lastName, email, phone, address, password, image);
//                    // Add the student to the list
//                    studentList.add(student);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//
//        return studentList;
//    }
    public boolean isCourseOffered(int courseId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_OFFERING_ID};
        String selection = COLUMN_OFFERING_ID + " = ?";
        String[] selectionArgs = {String.valueOf(courseId)};

        Cursor cursor = db.query(TABLE_OFFERING, columns, selection, selectionArgs, null, null, null);

        boolean isOffered = cursor.moveToFirst();

        cursor.close();
        db.close();

        return isOffered;
    }
    //////////////////////////////// new for student enroll
    public ArrayList<Integer> getOfferingIDsByCourseTitle(String courseTitle) {
        ArrayList<Integer> offeringIDs = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_OFFERING_ID};
        String selection = COLUMN_USER_COURSEID + " IN (SELECT " + COLUMN_USER_COURSEID + " FROM " + TABLE_COURSES + " WHERE " + COLUMN_USER_TITLE + " = ?)";
        String[] selectionArgs = {courseTitle};
        Cursor cursor = db.query(TABLE_OFFERING, columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(COLUMN_OFFERING_ID);
            if (index >= 0) {
                int value = cursor.getInt(index);
                offeringIDs.add(value);
            }
        }

        cursor.close();
        db.close();

        return offeringIDs;
    }

    public boolean enrollStudentInCourse(String studentEmail, String courseTitle, int offerid) {
        Student student = getStudentByEmailforEnroll(studentEmail);
        Courses course = getCourseData(courseTitle);
        // Check if the course is available
        if (!isCourseOffered(course.getId())){
            return false;
        }
        // Check if the student has already completed the prerequisites
        if (!hasCompletedPrerequisites(student, course)) {
            return false; // Prerequisites not completed
        }

        // Check for time conflicts with other enrolled courses
        if (hasTimeConflict(student, course)) {
            return false; // Time conflict with other courses
        }

        // Enroll the student in the course
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, student.getEmail());
        values.put(COLUMN_OFFERING_ID,offerid);
        long result = db.insert(TABLE_ENROLL, null, values);

        db.close();

        return result != -1; // Enrollment successful if insertion was successful
    }
    public Student getStudentByEmailforEnroll(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " =?", new String[]{email});

        Student student = null;
        if (cursor.moveToFirst()) {
            int firstNameIndex = cursor.getColumnIndex(COLUMN_USER_FIRST_NAME);
            int lastNameIndex = cursor.getColumnIndex(COLUMN_USER_LAST_NAME);
            int phoneIndex = cursor.getColumnIndex(COLUMN_USER_PHONE);
            int addressIndex = cursor.getColumnIndex(COLUMN_USER_ADDRESS);

            if (firstNameIndex != -1 && lastNameIndex != -1 && phoneIndex != -1 && addressIndex != -1) {
                String firstName = cursor.getString(firstNameIndex);
                String lastName = cursor.getString(lastNameIndex);
                String phone = cursor.getString(phoneIndex);
                String address = cursor.getString(addressIndex);

                // Assuming you have a Student class with appropriate constructor
                student = new Student(firstName, lastName, email, phone, address,null,null);
            }
        }

        cursor.close();
        db.close();

        return student;
    }

    // Check if a student has completed the prerequisites for a specific course
    public boolean hasCompletedPrerequisites(Student student, Courses course) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Check if the student has completed all the prerequisites for the course
        for (String prerequisite : course.getPrerequisites()) {
            if (!isCourseCompletedByStudent(prerequisite, student.getEmail())) {
                return false;
            }
        }

        db.close();

        return true;
    }

    // Check if a specific course is completed by the student
    private boolean isCourseCompletedByStudent(String courseName, String studentEmail) {
        String[] columns = {COLUMN_ENROLL_ID};
        String selection = COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_TITLE + " = ?";
        String[] selectionArgs = {studentEmail, courseName};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ENROLL, columns, selection, selectionArgs, null, null, null);

        boolean isCompleted = cursor.moveToFirst();

        cursor.close();
        return isCompleted;
    }

    // Check for time conflicts with other enrolled courses
    public boolean hasTimeConflict(Student student, Courses course) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Get the course schedule for the new course
        List<String> newCourseSchedule = getCourseSchedule(course.getId());

        // Retrieve the enrolled courses of the student
        List<Integer> enrolledCourses = getEnrolledCourseIds(student.getEmail());

        // Check for time conflicts with each enrolled course
        for (Integer enrolledCourse : enrolledCourses) {
            List<String> enrolledCourseSchedule = getCourseSchedule(enrolledCourse);

            // Compare the schedules of the new course and enrolled course
            if (isScheduleConflict(newCourseSchedule, enrolledCourseSchedule)) {
                db.close();
                return true; // Time conflict found
            }
        }

        db.close();

        return false; // No time conflicts with other courses
    }

    // Retrieve the course schedule from the table_offering based on the course ID
    private List<String> getCourseSchedule(int courseId) {
        List<String> courseSchedule = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_COURSE_SCHEDULE};
        String selection = COLUMN_OFFERING_ID + " = ?";
        String[] selectionArgs = {String.valueOf(courseId)};

        Cursor cursor = db.query(TABLE_OFFERING, columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            String schedule = null;
            int scheduleIndex = cursor.getColumnIndex(COLUMN_COURSE_SCHEDULE);
            if (scheduleIndex >= 0) {
                schedule = cursor.getString(scheduleIndex);
            }
            /// String schedule = cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_SCHEDULE));

            // Add the schedule to the course schedule list
            courseSchedule.add(schedule);
        }

        cursor.close();

        return courseSchedule;
    }

    public List<Integer> getEnrolledCourseIds(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Integer> enrolledCourseIds = new ArrayList<>();

        // Query to retrieve the enrolled course IDs for the given student email
        String query = "SELECT " + COLUMN_USER_COURSEID +
                " FROM " + TABLE_ENROLL +
                " WHERE " + COLUMN_USER_EMAIL + " = '" + userEmail + "'";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int courseId = 0;
            int courseIdIndex = cursor.getColumnIndex(COLUMN_USER_COURSEID);
            if (courseIdIndex >= 0) {
                courseId = cursor.getInt(courseIdIndex);
            }
            // int courseId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_COURSEID));
            enrolledCourseIds.add(courseId);
        }

        cursor.close();

        return enrolledCourseIds;
    }

    private boolean isScheduleConflict(List<String> newCourseSchedule, List<String> enrolledCourseSchedule) {
        for (String newSchedule : newCourseSchedule) {
            for (String enrolledSchedule : enrolledCourseSchedule) {
                if (isScheduleConflict(newSchedule, enrolledSchedule)) {
                    return true; // There is a schedule conflict
                }
            }
        }
        return false; // No schedule conflict
    }

    private boolean isScheduleConflict(String newSchedule, String enrolledSchedule) {
        // Assuming the schedule format is in a specific format (e.g., start time - end time)

        // Split the schedule strings to extract the start and end times
        String[] newScheduleParts = newSchedule.split("-");
        String[] enrolledScheduleParts = enrolledSchedule.split("-");

        // Extract the start and end times from the schedule parts
        String newStartTime = newScheduleParts[0].trim();
        String newEndTime = newScheduleParts[1].trim();

        String enrolledStartTime = enrolledScheduleParts[0].trim();
        String enrolledEndTime = enrolledScheduleParts[1].trim();

        // Compare the start and end times to check for conflicts
        // Assuming the time format is in HH:mm (24-hour format)
        if (newStartTime.compareTo(enrolledEndTime) < 0 && newEndTime.compareTo(enrolledStartTime) > 0) {
            // There is a schedule conflict between the new course and the enrolled course
            return true;
        }

        // No schedule conflict
        return false;
    }

    // for search in available courses
    public List<Courses> getOfferedCourses() {
        List<Courses> offeredCourses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_OFFERING_ID, COLUMN_USER_COURSEID};
        Cursor cursor = db.query(TABLE_OFFERING, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Integer courseId = 0;
            int courseIdIndex = cursor.getColumnIndex(COLUMN_OFFERING_ID);
            if (courseIdIndex >= 0) {
                courseId = cursor.getInt(courseIdIndex);
            }
            Courses course = getCourseById(courseId);
            offeredCourses.add(course);
        }

        cursor.close();
        db.close();

        return offeredCourses;
    }

    public Courses getCourseById(int courseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_TITLE, COLUMN_USER_MAINTOPICES, COLUMN_USER_PREEQUISITES};
        String selection = COLUMN_USER_COURSEID + " = ?";
        String[] selectionArgs = {String.valueOf(courseId)};
        Cursor cursor = db.query(TABLE_COURSES, columns, selection, selectionArgs, null, null, null);

        Courses course = null;
        if (cursor.moveToFirst()) {
            String title = null;
            int titleIndex = cursor.getColumnIndex(COLUMN_USER_TITLE);
            if (titleIndex >= 0) {
                title = cursor.getString(titleIndex);
            }
            String[] mainTopics = null;
            int mainTopicsIndex = cursor.getColumnIndex(COLUMN_USER_MAINTOPICES);
            if (mainTopicsIndex >= 0) {
                mainTopics = convertStringToArray(cursor.getString(mainTopicsIndex));
            }
            String[] prerequisites = null;
            int prerequisitesIndex = cursor.getColumnIndex(COLUMN_USER_PREEQUISITES);
            if (prerequisitesIndex >= 0) {
                prerequisites = convertStringToArray(cursor.getString(prerequisitesIndex));
            }

            course = new Courses(courseId, title, mainTopics, prerequisites, null);
        }

        cursor.close();
        db.close();

        return course;
    }
    public Offer getCourseOfferByofferId(int offerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_REGISTRATION_DEADLINE, COLUMN_START_DATE, COLUMN_COURSE_SCHEDULE, COLUMN_VENUE};
        String selection = COLUMN_OFFERING_ID + " = ?";
        String[] selectionArgs = {String.valueOf(offerId)};
        Cursor cursor = db.query(TABLE_OFFERING, columns, selection, selectionArgs, null, null, null);

        Offer offer = null;
        if (cursor.moveToFirst()) {
            Date deadline = null;
            int deadlineIndex = cursor.getColumnIndex(COLUMN_REGISTRATION_DEADLINE);
            if (deadlineIndex >= 0) {
                long deadlineValue = cursor.getLong(deadlineIndex);
                deadline = new Date(deadlineValue);
            }

            Date startDate = null;
            int startDateIndex = cursor.getColumnIndex(COLUMN_START_DATE);
            if (startDateIndex >= 0) {
                long startDateValue = cursor.getLong(startDateIndex);
                startDate = new Date(startDateValue);
            }

            String schedule = null;
            int scheduleIndex = cursor.getColumnIndex(COLUMN_COURSE_SCHEDULE);
            if (scheduleIndex >= 0) {
                schedule = cursor.getString(scheduleIndex);
            }

            String venue = null;
            int venueIndex = cursor.getColumnIndex(COLUMN_VENUE);
            if (venueIndex >= 0) {
                venue = cursor.getString(venueIndex);
            }

            offer = new Offer(offerId, null, deadline, startDate, schedule, venue);
        }

        cursor.close();
        db.close();

        return offer;
    }

    public Offer getCourseofferById(int courseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_REGISTRATION_DEADLINE, COLUMN_START_DATE, COLUMN_COURSE_SCHEDULE,COLUMN_VENUE};
        String selection = COLUMN_USER_COURSEID + " = ?";
        String[] selectionArgs = {String.valueOf(courseId)};
        Cursor cursor = db.query(TABLE_OFFERING, columns, selection, selectionArgs, null, null, null);

        Offer offer = null;
        if (cursor.moveToFirst()) {
            Date DEADLINE = null;
            int DEADLINEIndex = cursor.getColumnIndex(COLUMN_REGISTRATION_DEADLINE);
            if (DEADLINEIndex >= 0) {
                long deadlineValue = cursor.getLong(DEADLINEIndex);
                DEADLINE = new Date(deadlineValue);
            }
            Date START_DATE = null;
            int START_DATEIndex = cursor.getColumnIndex(COLUMN_START_DATE);
            if (START_DATEIndex >= 0) {
                long startDateValue = cursor.getLong(START_DATEIndex);
                START_DATE = new Date(startDateValue);
            }
            String SCHEDULE = null;
            int SCHEDULEIndex = cursor.getColumnIndex(COLUMN_COURSE_SCHEDULE);
            if (SCHEDULEIndex >= 0) {
                SCHEDULE = cursor.getString(SCHEDULEIndex);
            }
            String VENUE = null;
            int VENUEIndex = cursor.getColumnIndex(COLUMN_VENUE);
            if (VENUEIndex >= 0) {
                VENUE = cursor.getString(VENUEIndex);
            }

            offer = new Offer(0, null, DEADLINE, START_DATE, SCHEDULE,VENUE);
        }

        cursor.close();
        db.close();

        return offer;
    }
    // return the history courses that deadline is done
    public List<Courses> getOfferedCoursesHistory() {
        List<Courses> offeredCourses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_OFFERING_ID, COLUMN_USER_COURSEID};
        Cursor cursor = db.query(TABLE_OFFERING, columns, null, null, null, null, null);
        int courseId = 0;
        while (cursor.moveToNext()) {
            int courseIdIndex = cursor.getColumnIndex(COLUMN_OFFERING_ID);
            if (courseIdIndex >= 0) {
                courseId = cursor.getInt(courseIdIndex);
            }
            // int courseId = cursor.getInt(cursor.getColumnIndex(COLUMN_OFFERING_ID));
            Courses course = getCourseById(courseId);


            // Check if the course's registration deadline has passed
            if (isRegistrationDeadlinePassed(course)) {
                offeredCourses.add(course);
            }
        }

        cursor.close();
        db.close();

        return offeredCourses;
    }

    private boolean isRegistrationDeadlinePassed(Courses course) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_REGISTRATION_DEADLINE};
        String selection = COLUMN_USER_COURSEID + " = ?";
        String[] selectionArgs = {String.valueOf(course.getId())};
        Cursor cursor = db.query(TABLE_OFFERING, columns, selection, selectionArgs, null, null, null);

        long registrationDeadline = 0;

        if (cursor.moveToFirst()) {
            int registrationDeadlineIndex = cursor.getColumnIndex(COLUMN_REGISTRATION_DEADLINE);
            if (registrationDeadlineIndex >= 0) {
                registrationDeadline = cursor.getLong(registrationDeadlineIndex);
            }
        }

        cursor.close();
        db.close();

        long currentTimestamp = System.currentTimeMillis();

        return currentTimestamp > registrationDeadline;
    }
    public List<String> enrollIN(String userEmail) {
        List<String> enrolledCourseTitles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to join the TABLE_COURSES and TABLE_ENROLL tables
        String query = "SELECT " +
                TABLE_COURSES + "." + COLUMN_USER_TITLE +
                " FROM " + TABLE_COURSES +
                " INNER JOIN " + TABLE_ENROLL +
                " ON " + TABLE_COURSES + "." + COLUMN_USER_COURSEID + " = " + TABLE_ENROLL + "." + COLUMN_OFFERING_ID +
                " WHERE " + TABLE_ENROLL + "." + COLUMN_USER_EMAIL + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{userEmail});

        while (cursor.moveToNext()) {
            String title = null;
            int titleIndex = cursor.getColumnIndex(COLUMN_USER_TITLE);
            if (titleIndex >= 0) {
                title = cursor.getString(titleIndex);
            }
            // String title = cursor.getString(cursor.getColumnIndex(COLUMN_USER_TITLE));
            enrolledCourseTitles.add(title);
        }

        cursor.close();
        db.close();

        return enrolledCourseTitles;
    }
    public List<String> getEnrolledStudents(int courseId) {
        List<String> enrolledStudents = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to join the TABLE_USER and TABLE_ENROLL tables
        String query = "SELECT " +
                TABLE_USER + "." + COLUMN_USER_EMAIL +
                " FROM " + TABLE_USER +
                " INNER JOIN " + TABLE_ENROLL +
                " ON " + TABLE_USER + "." + COLUMN_USER_EMAIL + " = " + TABLE_ENROLL + "." + COLUMN_USER_EMAIL +
                " WHERE " + TABLE_ENROLL + "." + COLUMN_OFFERING_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(courseId)});

        while (cursor.moveToNext()) {
            String email = null;
            int emailIndex = cursor.getColumnIndex(COLUMN_USER_EMAIL);
            if (emailIndex >= 0) {
                email = cursor.getString(emailIndex);
            }
            // String email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL));
            enrolledStudents.add(email);
        }

        cursor.close();
        db.close();

        return enrolledStudents;
    }

    public void deleteEnrollCource(String email, String title) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get the offering ID for the course title
        int offeringId = getOfferingIdByTitle(title);

        // Define the WHERE clause to delete the enrollment entry
        String whereClause = COLUMN_USER_EMAIL + " = ? AND " + COLUMN_OFFERING_ID + " = ?";
        String[] whereArgs = {email, String.valueOf(offeringId)};

        // Perform the delete operation
        int rowsAffected = db.delete(TABLE_ENROLL, whereClause, whereArgs);

        db.close();

        if (rowsAffected > 0) {
            // Deletion successful
            //  Toast.makeText(, "Enrollment for course " + title + " deleted.", Toast.LENGTH_SHORT).show();
        } else {
            // No matching enrollment found
            //  Toast.makeText(context, "No enrollment found for course " + title + ".", Toast.LENGTH_SHORT).show();
        }
    }

    private int getOfferingIdByTitle(String title) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_OFFERING_ID};
        String selection = COLUMN_USER_TITLE + " = ?";
        String[] selectionArgs = {title};

        Cursor cursor = db.query(TABLE_COURSES, columns, selection, selectionArgs, null, null, null);

        int offeringId = 0;

        if (cursor.moveToFirst()) {
            int offeringindex = cursor.getColumnIndex(COLUMN_OFFERING_ID);
            if (offeringindex>0)
                offeringId = cursor.getInt(offeringindex);
        }

        cursor.close();
        db.close();

        return offeringId;
    }

    public Cursor getPreviousCoursesByInstructor(String instructorEmail) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * " +
                "FROM " + TABLE_OFFERING +
                " JOIN " + TABLE_COURSES + " ON " + TABLE_COURSES + "." + COLUMN_USER_COURSEID + " = " + TABLE_OFFERING + "." + COLUMN_USER_COURSEID +
                " JOIN " + TABLE_ENROLL + " ON " + TABLE_ENROLL + "." + COLUMN_OFFERING_ID + " = " + TABLE_OFFERING + "." + COLUMN_OFFERING_ID +
                " WHERE " + TABLE_ENROLL + "." + COLUMN_USER_EMAIL + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{instructorEmail});

        List<Integer> courseIds = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int courseId=0 ;
                        int index = cursor.getColumnIndex(COLUMN_USER_COURSEID);
                if (index>0)
                    courseId = cursor.getInt(index);
                Courses course = getCourseById(courseId);
                if (isRegistrationDeadlinePassed(course)) {
                    courseIds.add(courseId);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        // Create a new cursor with the filtered course ids
        MatrixCursor filteredCursor = new MatrixCursor(new String[]{COLUMN_USER_COURSEID});
        for (int courseId : courseIds) {
            filteredCursor.addRow(new Object[]{courseId});
        }

        return filteredCursor;
    }


public Cursor getCurrentScheduleByInstructor(String instructorEmail) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_OFFERING_ID + ", " + COLUMN_COURSE_SCHEDULE +
        " FROM " + TABLE_OFFERING +
        " WHERE " + COLUMN_USER_EMAIL + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{instructorEmail});

        List<Offer> offerings = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
        do {
        @SuppressLint("Range") int offeringId = cursor.getInt(cursor.getColumnIndex(COLUMN_OFFERING_ID));
        @SuppressLint("Range") String courseSchedule = cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_SCHEDULE));

        Courses course = getCourseById(offeringId);
        if (course != null && !isRegistrationDeadlinePassed(course)) {
        Offer offering = new Offer(offeringId,null ,null , null,courseSchedule ,null);
        offerings.add(offering);
        }
        } while (cursor.moveToNext());
        }

        cursor.close();

        // Convert the list of offerings to a MatrixCursor
        MatrixCursor offeringCursor = new MatrixCursor(new String[]{COLUMN_OFFERING_ID, COLUMN_COURSE_SCHEDULE});
        for (Offer offering : offerings) {
        offeringCursor.addRow(new Object[]{offering.getCourseId(), offering.getCourseSchedule()});
        }

        return offeringCursor;
        }

public Cursor getAllCoursesByInstructor(String instructorEmail) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_OFFERING_ID + ", " + COLUMN_USER_COURSEID +
                " FROM " + TABLE_OFFERING +
                " WHERE " + COLUMN_USER_EMAIL + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{instructorEmail});

        return cursor;
}

public Cursor getAllStudentsByCourse(int courseId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_USER_FIRST_NAME + COLUMN_USER_LAST_NAME +
                " FROM " + TABLE_ENROLL +
                " WHERE " + COLUMN_USER_COURSEID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(courseId)});

        return cursor;
}



}

