package com.nirmalhk7.nirmalhk7.attendance;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
