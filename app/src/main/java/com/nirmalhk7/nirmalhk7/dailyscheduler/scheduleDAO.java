package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface scheduleDAO {
    @Insert
    void insertAll(schedule... schedule);

    @Query("SELECT * FROM schedule")
    schedule[] getAll();


    @Insert
    public void insert(schedule... schedules);
    @Update
    public void update(schedule... schedules);
    @Delete
    public void delete(schedule schedule);


}
