package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nirmalhk7.nirmalhk7.dailyscheduler.Schedule;
import com.nirmalhk7.nirmalhk7.dailyscheduler.scheduleDAO;

@Database(entities = {Schedule.class}, version = 11, exportSchema = false)
public abstract class scheduleDatabase extends RoomDatabase {
    public abstract scheduleDAO getScheduleDao();

}