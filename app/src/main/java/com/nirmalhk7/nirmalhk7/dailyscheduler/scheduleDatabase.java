package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Schedule.class}, version = 1)
public abstract class scheduleDatabase extends RoomDatabase {
    public abstract scheduleDAO getScheduleDao();
}