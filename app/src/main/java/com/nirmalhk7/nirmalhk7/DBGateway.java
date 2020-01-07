package com.nirmalhk7.nirmalhk7;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.nirmalhk7.nirmalhk7.attendance.AttendanceDAO;
import com.nirmalhk7.nirmalhk7.attendance.AttendanceEntity;
import com.nirmalhk7.nirmalhk7.attendance.SubjectlogDAO;
import com.nirmalhk7.nirmalhk7.attendance.SubjectlogEntity;
import com.nirmalhk7.nirmalhk7.examholidays.ExamholidaysEntity;
import com.nirmalhk7.nirmalhk7.examholidays.ExamholidaysDAO;
import com.nirmalhk7.nirmalhk7.timetable.TimetableDAO;
import com.nirmalhk7.nirmalhk7.timetable.TimetableEntity;
import com.nirmalhk7.nirmalhk7.util.converter;

@Database(entities = {TimetableEntity.class, AttendanceEntity.class, ExamholidaysEntity.class, SubjectlogEntity.class}, version = 21, exportSchema = false)
@TypeConverters({converter.class})
public abstract class DBGateway extends RoomDatabase {
    public abstract TimetableDAO getTTDao();
    public abstract AttendanceDAO getATTDao();
    public abstract ExamholidaysDAO getEHDAO();
    public abstract SubjectlogDAO getSALDAO();

}