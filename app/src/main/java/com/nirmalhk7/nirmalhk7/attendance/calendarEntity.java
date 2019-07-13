package com.nirmalhk7.nirmalhk7.attendance;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class calendarEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mSubject;
    private String mDate;
    private int mprabca;

    public void setId(int id) {
        this.id = id;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public void setMprabca(int mprabca) {
        this.mprabca = mprabca;
    }

    public int getId() {
        return id;
    }

    public String getmSubject() {
        return mSubject;
    }

    public String getmDate() {
        return mDate;
    }

    public int getMprabca() {
        return mprabca;
    }
}
