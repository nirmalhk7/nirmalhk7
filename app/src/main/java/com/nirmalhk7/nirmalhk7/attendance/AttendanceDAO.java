package com.nirmalhk7.nirmalhk7.attendance;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AttendanceDAO {
    @Insert
    void insertOnlySingleSubject(AttendanceEntity attendance);

    @Insert
    void insertMultipleSchedule(List<AttendanceEntity> moviesList);

   /* @Query(“SELECT * FROM AttendanceEntity WHERE movieId =:movieId“)
    AttendanceEntity fetchOneSchedulebyMovieId(int movieId);*/

    @Query("SELECT * FROM AttendanceEntity")
    List<AttendanceEntity> getAllSubject();

    @Query("SELECT * FROM AttendanceEntity")
    List<AttendanceEntity> getSubjectNames();

    @Update
    void updateSubject(AttendanceEntity subject);

    @Query("SELECT * FROM AttendanceEntity WHERE id=:mId")
    AttendanceEntity getSubjectbyId(int mId);

    @Delete
    void deleteSchedule(AttendanceEntity schedule);

    @Query("SELECT * FROM AttendanceEntity WHERE mSubject=:Subject")
    AttendanceEntity getSubjectbyName(String Subject);
}
