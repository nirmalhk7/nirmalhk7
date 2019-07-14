package com.nirmalhk7.nirmalhk7.attendance;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {attendanceEntity.class}, version = 6, exportSchema = false)
public abstract class attendanceDatabase extends RoomDatabase {
    public abstract attendanceDAO getAttendanceDAO();
}
