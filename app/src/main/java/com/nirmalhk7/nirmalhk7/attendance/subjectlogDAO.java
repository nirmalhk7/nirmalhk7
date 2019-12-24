package com.nirmalhk7.nirmalhk7.attendance;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface subjectlogDAO {
    @Insert
    void insertLog(subjectlogEntity attendance);

    @Insert
    void insertMultipleLogs(List<subjectlogEntity> moviesList);

   /* @Query(“SELECT * FROM subjectlogEntity WHERE movieId =:movieId“)
    subjectlogEntity fetchOneSchedulebyMovieId(int movieId);*/

    @Query("SELECT * FROM subjectlogEntity WHERE mSubject=:subjName")
    List<subjectlogEntity> getAllLog(String subjName);
    
    @Update
    void updateLog(subjectlogEntity subject);

    @Query("SELECT * FROM subjectlogEntity WHERE id=:mId")
    subjectlogEntity getLogbyId(int mId);

    @Delete
    void deleteSchedule(subjectlogEntity schedule);
}
