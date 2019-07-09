package com.nirmalhk7.nirmalhk7.examholidays;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nirmalhk7.nirmalhk7.dailyscheduler.Schedule;
import com.nirmalhk7.nirmalhk7.dailyscheduler.scheduleDAO;


@Database(entities = {ehEntity.class}, version = 3)
public abstract class ehDatabase extends RoomDatabase {
    public abstract ehDAO getEHDAO();
}