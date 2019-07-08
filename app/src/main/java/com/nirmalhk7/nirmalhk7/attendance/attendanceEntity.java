package com.nirmalhk7.nirmalhk7.attendance;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class attendanceEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mSubject;
    private int mPresent;
    private int mAbsent;

    public int getId() { return id; }

    public String getSubject() { return mSubject; }

    public int getPresent() { return mPresent; }

    public int getAbsent() { return mAbsent; }


    public void setId(int id) {this.id = id;}

    public void setSubject(String mSubject) { this.mSubject = mSubject; }

    public void setPresent(int mPresent) { this.mPresent = mPresent; }

    public void setAbsent(int mAbsent) { this.mAbsent = mAbsent; }
}
