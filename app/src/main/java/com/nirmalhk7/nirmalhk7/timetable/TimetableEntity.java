package com.nirmalhk7.nirmalhk7.timetable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;


import java.util.Date;

@Entity
public class TimetableEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    protected int id;
    private String mTask;
    private Date mStartTime;
    private Date mEndTime;
    private String mLabel;
    private int mDay;
    private String mSubjCode;

    public TimetableEntity() {
    }

    public String getSubjCode(){ return  mSubjCode;}
    public String getTask(){
        return mTask;
    }

    public Date getStartTime() {
        return mStartTime;
    }

    public Date getEndTime() {
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

    public void setStartTime(Date mStartTime) {
        this.mStartTime = mStartTime;
    }

    public void setEndTime(Date mEndTime) {
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
