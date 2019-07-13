package com.nirmalhk7.nirmalhk7.attendance;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {attendanceEntity.class}, version = 5, exportSchema = false)
public abstract class calendarDatabase extends RoomDatabase {
    public abstract calendarDAO getCalendarDAO();
}

