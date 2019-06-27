package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {schedule.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract scheduleDAO getItemDAO();
}