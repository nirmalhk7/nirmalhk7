package com.nirmalhk7.nirmalhk7.attendance;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class subjectlogEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mSubject;
    private int prabca;
    private String dateAdded;
    private String dayTime;

    public void setSubject(String mSubject) {
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


    public String getSubject() {
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
