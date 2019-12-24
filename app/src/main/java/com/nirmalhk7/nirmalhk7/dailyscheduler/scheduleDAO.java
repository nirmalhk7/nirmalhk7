package com.nirmalhk7.nirmalhk7.dailyscheduler;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface scheduleDAO {
    @Insert
    void insertOnlySingleSchedule(ScheduleEntity movies);

    @Insert
    void insertMultipleSchedule(List<ScheduleEntity> moviesList);

   /* @Query(“SELECT * FROM ScheduleEntity WHERE movieId =:movieId“)
    ScheduleEntity fetchOneSchedulebyMovieId(int movieId);*/

    @Query("SELECT * FROM ScheduleEntity")
    List<ScheduleEntity> getItems();

    @Query("SELECT * FROM ScheduleEntity WHERE mDay =:Day ORDER BY mStartTime")
    List<ScheduleEntity> getScheduleByDay(int Day);

    @Update
    void updateSchedule(ScheduleEntity scheduleEntity);

    @Query("SELECT * FROM ScheduleEntity WHERE mTask=:Task")
    ScheduleEntity getScheduleDetails(String Task);

    @Query("SELECT mTask,mDay,mStartTime,mEndTime,id FROM ScheduleEntity WHERE mStartTime>:starttime ORDER BY mStartTime")
    List<ScheduleEntity> getScheduleByDayAndTime(Date starttime);

    @Delete
    void deleteSchedule(ScheduleEntity scheduleEntity);

    @Query("SELECT DISTINCT mTask,mDay,id FROM ScheduleEntity WHERE mLabel=:Label")
    List<ScheduleEntity> getSubjects(String Label);

    @Query("SELECT COUNT(DISTINCT mTask) FROM ScheduleEntity WHERE mLabel=:Label")
    int getSubjectCount(String Label);

    @Query("SELECT * FROM ScheduleEntity WHERE id=:Id")
    ScheduleEntity getScheduleById(int Id);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT DISTINCT mSubjCode,id,mDay FROM ScheduleEntity WHERE mTask=:Task")
    List<ScheduleEntity> getScheduleCodeByTaskName(String Task);
}

