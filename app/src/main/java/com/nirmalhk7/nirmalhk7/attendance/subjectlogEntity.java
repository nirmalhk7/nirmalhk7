package com.nirmalhk7.nirmalhk7.attendance;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.Date;

@Entity
public class subjectlogEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mSubject;
    private int prabca;
    private Date daytime;

    public void setSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDaytime(Date daytime) {
        this.daytime = daytime;
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

    public Date getDaytime() {
        return daytime;
    }
}
