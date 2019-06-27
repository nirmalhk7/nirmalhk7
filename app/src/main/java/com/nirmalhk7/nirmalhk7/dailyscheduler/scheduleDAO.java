package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface scheduleDAO {
    @Insert
    void insertAll(Schedule schedule);

    @Query("SELECT * FROM Schedule")
    List<Schedule> getAll();

    @Query("delete from Schedule")
    void removeAllUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Schedule schedule);
    @Update
    public void update(Schedule schedule);
    @Delete
    public void delete(Schedule schedule);


}
