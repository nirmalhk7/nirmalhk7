package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Schedule {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    protected int id;
    private String mTask;
    private String mTime;
    private String mLabel;
    private int mDay;

    public Schedule() {
    }


    public String getTask(){
        return mTask;
    }

    public String getTime(){
        return mTime;
    }

    public String getLabel(){
        return mLabel;
    }

    public int getDay(){
        return mDay;
    }

    public int getId(){
        return id;
    }

    public void setId(int mId){
        id=mId;
    }

    public void setTask(String task){
        mTask=task;
    }

    public void setTime(String time){
        mTime=time;
    }

    public void setLabel(String label){
        mLabel=label;
    }

    public void setDay(int day){
        mDay=day;
    }
}
