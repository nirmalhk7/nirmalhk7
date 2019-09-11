package com.nirmalhk7.nirmalhk7.attendance;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.RequiresPermission;

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
