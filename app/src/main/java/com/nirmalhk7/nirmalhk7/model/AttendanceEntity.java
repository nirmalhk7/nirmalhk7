package com.nirmalhk7.nirmalhk7.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity
public class AttendanceEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mSubject;
    private int mPresent;
    private int mAbsent;
    private int mCancelled;

    public int getId() { return id; }

    public int getCancelled(){
        return mCancelled;
    }
    public String getSubject() { return mSubject; }

    public int getPresent() { return mPresent; }

    public int getAbsent() { return mAbsent; }


    public void setId(int id) {this.id = id;}

    public void setSubject(String mSubject) { this.mSubject = mSubject; }

    public void setCancelled(int mCancelled) {
        this.mCancelled = mCancelled;
    }
    public void setPresent(int mPresent) { this.mPresent = mPresent; }

    public void setAbsent(int mAbsent) { this.mAbsent = mAbsent; }
}
