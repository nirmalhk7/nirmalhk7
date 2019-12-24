package com.nirmalhk7.nirmalhk7.attendance;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM attendanceEntity WHERE mSubject=:Subject")
    attendanceEntity getSubjectbyName(String Subject);
}
