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
    private String mStartTime;
    private String mEndTime;
    private String mLabel;
    private int mDay;
    private String mSubjCode;

    public Schedule() {
    }

    public String getSubjCode(){ return  mSubjCode;}
    public String getTask(){
        return mTask;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public String getEndTime() {
        return mEndTime;
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

    public void setStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public void setEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    public void setLabel(String label){
        mLabel=label;
    }

    public void setDay(int day){
        mDay=day;
    }
    public void setSubjCode(String mSubjCode)
    {
        this.mSubjCode=mSubjCode;
    }
}
