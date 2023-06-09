package com.example.appforelderlyprotoelec;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "users";

    // column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_BIRTHDATE = "birthdate";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_CALORIES_BURNT = "calories_burnt";
    private static final String COLUMN_DISTANCE = "distance";
    private static final String COLUMN_STEPS = "steps";
    private static final String COLUMN_EXERCISE = "exercise";
    private static final String COLUMN_EXERCISE_DURATION = "exercise_duration";

    private static final String TAG = "DatabaseHelper";
    private static final String SCHEDULE_TABLE_NAME = "schedule_table";
    private static final String SCHEDULE_COLUMN_ID = "schedule_id";
    private static final String SCHEDULE_COLUMN_USER_ID = "schedule_column_id";
    private static final String SCHEDULE_COLUMN_DAY = "schedule_column_day";
    private static final String SCHEDULE_COLUMN_TIME = "schedule_column_time";
    private static final String SCHEDULE_COLUMN_ACTIVITY = "schedule_column_activity";
    private static final String COLUMN_LEVEL = "column_level";

    private static final String COLUMN_POINTS="column_points";

    private SQLiteDatabase db;
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT UNIQUE, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_BIRTHDATE + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_WEIGHT + " REAL, " +
                COLUMN_HEIGHT + " REAL, " +
                COLUMN_CALORIES_BURNT + " REAL, " +
                COLUMN_DISTANCE + " REAL, " +
                COLUMN_STEPS + " INTEGER, " +
                COLUMN_EXERCISE + " TEXT, " +
                COLUMN_LEVEL+"INTEGER, "+
                COLUMN_POINTS+"INTEGER, "+
                COLUMN_EXERCISE_DURATION + " INTEGER)";
                db.execSQL(createTableQuery);
                String createScheduleTableQuery = "CREATE TABLE " + SCHEDULE_TABLE_NAME + " (" +
                        SCHEDULE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        SCHEDULE_COLUMN_USER_ID + " INTEGER, " + // Add the user_id column
                        SCHEDULE_COLUMN_DAY + " TEXT, " +
                        SCHEDULE_COLUMN_TIME + " TEXT, " +
                        SCHEDULE_COLUMN_ACTIVITY + " TEXT, " +

                        "FOREIGN KEY(" + SCHEDULE_COLUMN_USER_ID + ") REFERENCES " + TABLE_NAME + "(" + COLUMN_ID + "))";

        db.execSQL(createScheduleTableQuery);

        System.out.println("HERE");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE_NAME);

        onCreate(db);
    }

    public void addUser(User user) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_BIRTHDATE, user.getBirthdate());
        values.put(COLUMN_GENDER, user.getGender());
        values.put(COLUMN_WEIGHT, user.getWeight());
        values.put(COLUMN_HEIGHT, user.getHeight());
        values.put(COLUMN_CALORIES_BURNT, user.getCaloriesBurnt());
        values.put(COLUMN_DISTANCE, user.getDistance());
        values.put(COLUMN_STEPS, user.getSteps());
        values.put(COLUMN_EXERCISE, user.getExercise());
        values.put(COLUMN_EXERCISE_DURATION, user.getExerciseDuration());
        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public boolean checkUserExists(String username, String password) {
        db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + "=? AND " + COLUMN_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean userExists = cursor.getCount() > 0;

        cursor.close();
        db.close();
        return userExists;
    }

    @SuppressLint("Range")
    public User getLoggedInUser() {
        SQLiteDatabase db = null;
        User user = null;

        try {
            db = this.getReadableDatabase();

            // get the ID of the logged-in user from the app's state
            SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            int loggedInUserId = prefs.getInt("loggedInUserId", -1);

            if (loggedInUserId > -1) {
                // query the users table to retrieve the user with the specified ID
                Cursor cursor = db.query(TABLE_NAME, null, COLUMN_ID + "=?", new String[]{String.valueOf(loggedInUserId)}, null, null, null);
                if (cursor.moveToFirst()) {
                    user = new User();
                    user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                    user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                    user.setBirthdate(cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDATE)));
                    user.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
                    user.setWeight(cursor.getFloat(cursor.getColumnIndex(COLUMN_WEIGHT)));
                    user.setHeight(cursor.getFloat(cursor.getColumnIndex(COLUMN_HEIGHT)));
                    user.setCaloriesBurnt(cursor.getFloat(cursor.getColumnIndex(COLUMN_CALORIES_BURNT)));
                    user.setDistance(cursor.getFloat(cursor.getColumnIndex(COLUMN_DISTANCE)));
                    user.setSteps(cursor.getInt(cursor.getColumnIndex(COLUMN_STEPS)));
                    user.setExercise(cursor.getString(cursor.getColumnIndex(COLUMN_EXERCISE)));
                    user.setExerciseDuration(cursor.getInt(cursor.getColumnIndex(COLUMN_EXERCISE_DURATION)));
                }
                cursor.close();

                if (user == null) {
                    Log.e(TAG, "No user found with ID " + loggedInUserId);
                    // Return a default user object if no user is found
                    user = new User();
                    user.setId(loggedInUserId);
                }
            } else {
                Log.e(TAG, "Invalid user ID: " + loggedInUserId);
                // Return a default user object if the user ID is invalid or not set
                user = new User();
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "Error retrieving logged in user", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return user;
    }



    public void updateUser(User user) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_BIRTHDATE, user.getBirthdate());
        values.put(COLUMN_GENDER, user.getGender());
        values.put(COLUMN_WEIGHT, user.getWeight());
        values.put(COLUMN_HEIGHT, user.getHeight());
        values.put(COLUMN_CALORIES_BURNT, user.getCaloriesBurnt());
        values.put(COLUMN_DISTANCE, user.getDistance());
        values.put(COLUMN_STEPS, user.getSteps());
        values.put(COLUMN_EXERCISE, user.getExercise());
        values.put(COLUMN_EXERCISE_DURATION, user.getExerciseDuration());

        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(user.getId())});
        db.close();
    }


    public void addActivity(Schedule schedule) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SCHEDULE_COLUMN_USER_ID, schedule.getId());
        values.put(SCHEDULE_COLUMN_DAY, schedule.getDay());
        values.put(SCHEDULE_COLUMN_TIME, schedule.getTime());
        values.put(SCHEDULE_COLUMN_ACTIVITY, schedule.getActivity());

        db.insert(SCHEDULE_TABLE_NAME, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<String> getAllActivities() {
        List<String> activitiesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, SCHEDULE_TABLE_NAME, new String[]{SCHEDULE_COLUMN_ACTIVITY}, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                activitiesList.add(cursor.getString(cursor.getColumnIndex(SCHEDULE_COLUMN_ACTIVITY)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return activitiesList;
    }


}
