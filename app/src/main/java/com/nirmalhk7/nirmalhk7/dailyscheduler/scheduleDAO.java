package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface scheduleDAO {
    @Insert
    void insertOnlySingleSchedule(Schedule movies);

    @Insert
    void insertMultipleSchedule(List<Schedule> moviesList);

   /* @Query(“SELECT * FROM Schedule WHERE movieId =:movieId“)
    Schedule fetchOneSchedulebyMovieId(int movieId);*/

    @Query("SELECT * FROM Schedule")
    List<Schedule> getItems();

    @Query("SELECT * FROM Schedule WHERE mDay =:Day ORDER BY mStartTime")
    List<Schedule> getScheduleByDay(int Day);

    @Update
    void updateSchedule(Schedule schedule);

    @Query("SELECT * FROM Schedule WHERE mTask=:Task")
    Schedule getScheduleDetails(String Task);



    @Delete
    void deleteSchedule(Schedule schedule);

    @Query("SELECT DISTINCT mTask,mDay,id FROM Schedule WHERE mLabel=:Label")
    List<Schedule> getSubjects(String Label);

    @Query("SELECT COUNT(DISTINCT mTask) FROM Schedule WHERE mLabel=:Label")
    int getSubjectCount(String Label);

    @Query("SELECT * FROM Schedule WHERE id=:Id")
    Schedule getScheduleById(int Id);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT DISTINCT mSubjCode,id,mDay FROM Schedule WHERE mTask=:Task")
    List<Schedule> getScheduleCodeByTaskName(String Task);
}

