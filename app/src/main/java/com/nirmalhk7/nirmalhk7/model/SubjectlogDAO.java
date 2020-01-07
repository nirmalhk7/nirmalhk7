package com.nirmalhk7.nirmalhk7.model;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SubjectlogDAO {
    @Insert
    void insertLog(SubjectlogEntity attendance);

    @Insert
    void insertMultipleLogs(List<SubjectlogEntity> moviesList);

   /* @Query(“SELECT * FROM SubjectlogEntity WHERE movieId =:movieId“)
    SubjectlogEntity fetchOneSchedulebyMovieId(int movieId);*/

    @Query("SELECT * FROM SubjectlogEntity WHERE mSubject=:subjName")
    List<SubjectlogEntity> getAllLog(String subjName);
    
    @Update
    void updateLog(SubjectlogEntity subject);

    @Query("SELECT * FROM SubjectlogEntity WHERE id=:mId")
    SubjectlogEntity getLogbyId(int mId);

    @Delete
    void deleteSchedule(SubjectlogEntity schedule);
}
