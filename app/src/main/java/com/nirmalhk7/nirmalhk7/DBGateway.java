package com.nirmalhk7.nirmalhk7;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.nirmalhk7.nirmalhk7.attendance.AttendanceDAO;
import com.nirmalhk7.nirmalhk7.attendance.AttendanceEntity;
import com.nirmalhk7.nirmalhk7.attendance.SubjectlogDAO;
import com.nirmalhk7.nirmalhk7.attendance.SubjectlogEntity;
import com.nirmalhk7.nirmalhk7.examholidays.ExamholidaysEntity;
import com.nirmalhk7.nirmalhk7.examholidays.ExamholidaysDAO;
import com.nirmalhk7.nirmalhk7.timetable.TimetableDAO;
import com.nirmalhk7.nirmalhk7.timetable.TimetableEntity;
import com.nirmalhk7.nirmalhk7.utility.Converters;

@Database(entities = {TimetableEntity.class, AttendanceEntity.class, ExamholidaysEntity.class, SubjectlogEntity.class}, version = 22, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DBGateway extends RoomDatabase {
    public abstract TimetableDAO getTTDao();
    public abstract AttendanceDAO getATTDao();
    public abstract ExamholidaysDAO getEHDAO();
    public abstract SubjectlogDAO getSALDAO();

}