package com.nirmalhk7.nirmalhk7.attendance;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

public class subjectlogEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mSubject;
    private int prabca;
    private String dateAdded;
    private String dayTime;

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public void setPrabca(int prabca) {
        this.prabca = prabca;
    }


    public String getmSubject() {
        return mSubject;
    }

    public int getId() {
        return id;
    }

    public int getPrabca() {
        return prabca;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public String getDayTime() {
        return dayTime;
    }
}
