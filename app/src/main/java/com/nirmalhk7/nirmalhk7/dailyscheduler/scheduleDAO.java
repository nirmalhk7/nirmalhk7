package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface scheduleDAO {
    @Insert
    void insertAll(schedule... schedule);
    @Query("SELECT * FROM schedule")
    List<schedule> getAll();

    @Query("SELECT * FROM schedule WHERE day LIKE:mday")
    schedule findByDay(String mday);


}
