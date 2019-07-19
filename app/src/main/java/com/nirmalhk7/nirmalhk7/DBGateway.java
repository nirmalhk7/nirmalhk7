package com.nirmalhk7.nirmalhk7;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nirmalhk7.nirmalhk7.attendance.attendanceDAO;
import com.nirmalhk7.nirmalhk7.attendance.attendanceEntity;
import com.nirmalhk7.nirmalhk7.dailyscheduler.Schedule;
import com.nirmalhk7.nirmalhk7.dailyscheduler.scheduleDAO;
import com.nirmalhk7.nirmalhk7.examholidays.ehDAO;
import com.nirmalhk7.nirmalhk7.examholidays.ehEntity;

@Database(entities = {Schedule.class, attendanceEntity.class, ehEntity.class}, version = 16, exportSchema = false)
public abstract class DBGateway extends RoomDatabase {
    public abstract scheduleDAO getScheduleDao();
    public abstract attendanceDAO getAttendanceDao();
    public abstract ehDAO getEHDAO();
}