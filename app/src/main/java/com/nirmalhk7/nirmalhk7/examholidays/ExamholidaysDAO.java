package com.nirmalhk7.nirmalhk7.examholidays;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;



import java.util.List;

@Dao
public interface ExamholidaysDAO {
    @Insert
    void insertOnlySingleEvent(ExamholidaysEntity movies);

    @Insert
    void insertMultipleehEntity(List<ExamholidaysEntity> moviesList);

   /* @Query(“SELECT * FROM ExamholidaysEntity WHERE movieId =:movieId“)
    ExamholidaysEntity fetchOneehEntitybyMovieId(int movieId);*/

    @Query("SELECT * FROM ExamholidaysEntity ORDER BY Start")
    List<ExamholidaysEntity> getEventsOrdered();


    @Update
    void updateEvent(ExamholidaysEntity movies);

    @Delete
    void deleteEvent(ExamholidaysEntity schedule);

    @Query("SELECT * FROM ExamholidaysEntity WHERE id=:Id")
    ExamholidaysEntity getehEntityById(int Id);
}
