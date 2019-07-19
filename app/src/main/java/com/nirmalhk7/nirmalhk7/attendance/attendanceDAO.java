package com.nirmalhk7.nirmalhk7.attendance;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface attendanceDAO {
    @Insert
    void insertOnlySingleSubject(attendanceEntity attendance);

    @Insert
    void insertMultipleSchedule(List<attendanceEntity> moviesList);

   /* @Query(“SELECT * FROM attendanceEntity WHERE movieId =:movieId“)
    attendanceEntity fetchOneSchedulebyMovieId(int movieId);*/

    @Query("SELECT * FROM attendanceEntity")
    List<attendanceEntity> getAllSubject();

    @Query("SELECT * FROM attendanceEntity")
    List<attendanceEntity> getSubjectNames();

    @Update
    void updateSubject(attendanceEntity subject);

    @Query("SELECT * FROM attendanceEntity WHERE id=:mId")
    attendanceEntity getSubjectbyId(int mId);

    @Delete
    void deleteSchedule(attendanceEntity schedule);
}
