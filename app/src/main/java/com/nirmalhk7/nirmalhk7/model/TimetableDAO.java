package com.nirmalhk7.nirmalhk7.timetable;

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
    void insertOnlySingleSchedule(TimetableEntity movies);

    @Insert
    void insertMultipleSchedule(List<TimetableEntity> moviesList);

   /* @Query(“SELECT * FROM TimetableEntity WHERE movieId =:movieId“)
    TimetableEntity fetchOneSchedulebyMovieId(int movieId);*/

    @Query("SELECT * FROM TimetableEntity")
    List<TimetableEntity> getItems();

    @Query("SELECT * FROM TimetableEntity WHERE mDay =:Day ORDER BY mStartTime")
    List<TimetableEntity> getScheduleByDay(int Day);

    @Update
    void updateSchedule(TimetableEntity scheduleEntity);

    @Query("SELECT * FROM TimetableEntity WHERE mTask=:Task")
    TimetableEntity getScheduleDetails(String Task);

    @Query("SELECT mTask,mDay,mStartTime,mEndTime,id FROM TimetableEntity WHERE mStartTime>:starttime ORDER BY mStartTime")
    List<TimetableEntity> getScheduleByDayAndTime(Date starttime);

    @Delete
    void deleteSchedule(TimetableEntity scheduleEntity);

    @Query("SELECT DISTINCT mTask,mDay,id FROM TimetableEntity WHERE mLabel=:Label")
    List<TimetableEntity> getSubjects(String Label);

    @Query("SELECT DISTINCT * FROM TimetableEntity WHERE mLabel=:Label AND id IN (SELECT MAX(id) FROM TimetableEntity GROUP BY mSubjCode)")
    List<TimetableEntity> getUnqiueSubjects(String Label);

    @Query("SELECT COUNT(DISTINCT mTask) FROM TimetableEntity WHERE mLabel=:Label")
    int getSubjectCount(String Label);

    @Query("SELECT * FROM TimetableEntity WHERE id=:Id")
    TimetableEntity getScheduleById(int Id);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT DISTINCT mSubjCode,id,mDay FROM TimetableEntity WHERE mTask=:Task")
    List<TimetableEntity> getScheduleCodeByTaskName(String Task);
}

