package com.nirmalhk7.nirmalhk7;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.nirmalhk7.nirmalhk7.controllers.Converters;
import com.nirmalhk7.nirmalhk7.model.AttendanceDAO;
import com.nirmalhk7.nirmalhk7.model.AttendanceEntity;
import com.nirmalhk7.nirmalhk7.model.ExamholidaysDAO;
import com.nirmalhk7.nirmalhk7.model.ExamholidaysEntity;
import com.nirmalhk7.nirmalhk7.model.SubjectlogDAO;
import com.nirmalhk7.nirmalhk7.model.SubjectlogEntity;
import com.nirmalhk7.nirmalhk7.timetable.TimetableDAO;
import com.nirmalhk7.nirmalhk7.timetable.TimetableEntity;

@Database(entities = {TimetableEntity.class, AttendanceEntity.class, ExamholidaysEntity.class, SubjectlogEntity.class}, version = 21, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DBGateway extends RoomDatabase {
    public abstract TimetableDAO getTTDao();
    public abstract AttendanceDAO getATTDao();
    public abstract ExamholidaysDAO getEHDAO();
    public abstract SubjectlogDAO getSALDAO();
    private static DBGateway INSTANCE;
    public static DBGateway getInstance(Context context)
    {
        if(INSTANCE==null)
        {

            INSTANCE=Room.databaseBuilder(context.getApplicationContext(),DBGateway.class,"finalDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
    public static void destroyInstance() {
        INSTANCE = null;
    }

}