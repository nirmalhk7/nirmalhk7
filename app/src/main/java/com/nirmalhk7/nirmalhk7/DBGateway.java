package com.nirmalhk7.nirmalhk7;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.nirmalhk7.nirmalhk7.attendance.attendanceDAO;
import com.nirmalhk7.nirmalhk7.attendance.attendanceEntity;
import com.nirmalhk7.nirmalhk7.attendance.subjectlogDAO;
import com.nirmalhk7.nirmalhk7.attendance.subjectlogEntity;
import com.nirmalhk7.nirmalhk7.timetable.ScheduleEntity;
import com.nirmalhk7.nirmalhk7.timetable.scheduleDAO;
import com.nirmalhk7.nirmalhk7.examholidays.ehDAO;
import com.nirmalhk7.nirmalhk7.examholidays.ehEntity;

@Database(entities = {ScheduleEntity.class, attendanceEntity.class, ehEntity.class, subjectlogEntity.class}, version = 22, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DBGateway extends RoomDatabase {
    public abstract scheduleDAO getScheduleDao();
    public abstract attendanceDAO getAttendanceDao();
    public abstract ehDAO getEHDAO();
    public abstract subjectlogDAO getSALDAO();
}