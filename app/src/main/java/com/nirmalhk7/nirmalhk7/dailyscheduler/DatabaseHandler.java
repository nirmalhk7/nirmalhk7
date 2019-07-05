package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "scheduleDB";
    private static final String KEY_TABLENAME = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_TASK = "task";
    private static final String KEY_LABEL = "label";
    private static final String KEY_TIME = "label";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + KEY_TABLENAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TASK + " TEXT,"
                + KEY_LABEL + " TEXT," +KEY_TIME+"TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + KEY_TABLENAME);

        // Create tables again
        onCreate(db);
    }

    // code to add the new schedule
    void addSchedule(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK, schedule.getTask()); // Schedule Name
        values.put(KEY_LABEL, schedule.getLabel());
        values.put(KEY_TIME, schedule.getTime());// Schedule Phone

        // Inserting Row
        db.insert(KEY_TABLENAME, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single schedule
    Schedule getSchedule(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(KEY_TABLENAME, new String[] { KEY_ID,
                        KEY_TASK, KEY_LABEL }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Schedule schedule = new Schedule(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return schedule
        return schedule;
    }

    // code to get all contacts in a list view
    public List<Schedule> getAllSchedules() {
        List<Schedule> contactList = new ArrayList<Schedule>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + KEY_TABLENAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule();
                schedule.setID(Integer.parseInt(cursor.getString(0)));
                schedule.setTask(cursor.getString(1));
                schedule.setLabel(cursor.getString(2));
                schedule.setTime(cursor.getString(3));
                // Adding schedule to list
                contactList.add(schedule);
            } while (cursor.moveToNext());
        }

        // return schedule list
        return contactList;
    }

    // code to update the single schedule
    public int updateSchedule(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK, schedule.getTask());
        values.put(KEY_LABEL, schedule.getLabel());

        values.put(KEY_TIME, schedule.getLabel());

        // updating row
        return db.update(KEY_TABLENAME, values, KEY_ID + " = ?",
                new String[] { String.valueOf(Schedule.getID()) });
    }

    // Deleting single schedule
    public void deleteSchedule(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(KEY_TABLENAME, KEY_ID + " = ?",
                new String[] { String.valueOf(Schedule.getID()) });
        db.close();
    }

    // Getting contacts Count
    public int getSchedulesCount() {
        String countQuery = "SELECT  * FROM " + KEY_TABLENAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
