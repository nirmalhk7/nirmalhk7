package com.nirmalhk7.nirmalhk7.examholidays;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;



import java.util.List;

@Dao
public interface ExamholidaysDAO {
    @Insert
    void insertOnlySingleEvent(ExamholidaysEntity movies);

    @Insert
    void insertMultipleehEntity(List<ExamholidaysEntity> moviesList);

   /* @Query(“SELECT * FROM ExamholidaysEntity WHERE movieId =:movieId“)
    ExamholidaysEntity fetchOneehEntitybyMovieId(int movieId);*/

    @Query("SELECT * FROM ExamholidaysEntity")
    List<ExamholidaysEntity> getItems();


    @Update
    void updateEvent(ExamholidaysEntity movies);

    @Delete
    void deleteEvent(ExamholidaysEntity schedule);

    @Query("SELECT * FROM ExamholidaysEntity WHERE id=:Id")
    ExamholidaysEntity getehEntityById(int Id);
}
