package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface scheduleDAO {
    @Insert
    void insertOnlySingleMovie(Schedule movies);

    @Insert
    void insertMultipleSchedule(List<Schedule> moviesList);

   /* @Query(“SELECT * FROM Schedule WHERE movieId =:movieId“)
    Schedule fetchOneSchedulebyMovieId(int movieId);*/

    @Query("SELECT * FROM Schedule")
    public List<Schedule> getItems();

    @Query("SELECT * FROM Schedule WHERE mDay =:Day")
    public List<Schedule> getScheduleByDay(int Day);

    @Update
    void updateMovie(Schedule movies);

    @Delete
    void deleteSchedule(Schedule schedule);

    @Query("SELECT DISTINCT mTask FROM Schedule WHERE mLabel=:Label")
    public List<Schedule> getSubjects(String Label);

}

