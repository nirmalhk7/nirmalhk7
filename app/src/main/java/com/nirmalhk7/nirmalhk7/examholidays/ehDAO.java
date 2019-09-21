package com.nirmalhk7.nirmalhk7.examholidays;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;

@Dao
public interface ehDAO {
    @Insert
    void insertOnlySingleEvent(ehEntity movies);

    @Insert
    void insertMultipleehEntity(List<ehEntity> moviesList);

   /* @Query(“SELECT * FROM ehEntity WHERE movieId =:movieId“)
    ehEntity fetchOneehEntitybyMovieId(int movieId);*/

    @Query("SELECT * FROM ehEntity")
    List<ehEntity> getItems();


    @Update
    void updateEvent(ehEntity movies);

    @Delete
    void deleteEvent(ehEntity schedule);

    @Query("SELECT * FROM ehEntity WHERE id=:Id")
    ehEntity getehEntityById(int Id);
}
