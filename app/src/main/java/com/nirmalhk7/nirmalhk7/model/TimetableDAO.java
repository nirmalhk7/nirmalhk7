package com.nirmalhk7.nirmalhk7.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface TimetableDAO {
    @Insert
    void insertOnlySingleSchedule(TimetableEntity schedule);

    @Query("SELECT * FROM TimetableEntity WHERE mDay =:Day ORDER BY mStartTime")
    List<TimetableEntity> getScheduleByDay(int Day);

    @Update
    void updateSchedule(TimetableEntity scheduleEntity);

    @Query("SELECT * FROM TimetableEntity WHERE mTask=:Task")
    TimetableEntity getScheduleDetails(String Task);

    @Query("SELECT mTask,mDay,mStartTime,mEndTime,id FROM TimetableEntity WHERE (mStartTime<:starttime AND mDay=:Day) ORDER BY mStartTime LIMIT 1")
    TimetableEntity getScheduleByDayAndTime(int Day,Date starttime);

    @Delete
    void deleteSchedule(TimetableEntity scheduleEntity);

    @Query("SELECT DISTINCT mTask,mDay,id FROM TimetableEntity WHERE mLabel=:Label")
    List<TimetableEntity> getSubjects(String Label);

    @Query("SELECT DISTINCT * FROM TimetableEntity WHERE mLabel=:Label AND id IN (SELECT MAX(id) FROM TimetableEntity GROUP BY mSubjCode)")
    List<TimetableEntity> getUnqiueSubjects(String Label);

    @Query("SELECT * FROM TimetableEntity WHERE id=:Id")
    TimetableEntity getScheduleById(int Id);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT DISTINCT mSubjCode,id,mDay FROM TimetableEntity WHERE mTask=:Task")
    List<TimetableEntity> getScheduleCodeByTaskName(String Task);
}

