package com.nirmalhk7.nirmalhk7.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface ExamholidaysDAO {
    @Insert
    void insertOnlySingleEvent(ExamholidaysEntity event);

    @Insert
    void insertMultipleehEntity(List<ExamholidaysEntity> moviesList);

   /* @Query(“SELECT * FROM ExamholidaysEntity WHERE movieId =:movieId“)
    ExamholidaysEntity fetchOneehEntitybyMovieId(int movieId);*/

    @Query("SELECT * FROM ExamholidaysEntity ORDER BY Start")
    List<ExamholidaysEntity> getEventsOrdered();



    @Update
    void updateEvent(ExamholidaysEntity event);

    @Delete
    void deleteEvent(ExamholidaysEntity event);

    @Query("SELECT * FROM ExamholidaysEntity WHERE id=:Id")
    ExamholidaysEntity getehEntityById(int Id);

    @Query("SELECT DISTINCT * FROM ExamholidaysEntity WHERE id IN (SELECT MAX(id) FROM ExamholidaysEntity GROUP BY mType)")
    List<ExamholidaysEntity> getEHTypesUnique();

    @Query("SELECT * FROM ExamholidaysEntity WHERE Start>:date AND holexa=:type LIMIT 1")
    ExamholidaysEntity getNextEvent(Date date, int type);

    @Query("SELECT * FROM ExamholidaysEntity WHERE Start>:date LIMIT 1")
    ExamholidaysEntity getAnyNextEvent(Date date);

}
