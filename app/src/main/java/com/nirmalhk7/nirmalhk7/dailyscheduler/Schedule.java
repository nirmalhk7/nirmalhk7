package com.nirmalhk7.nirmalhk7.dailyscheduler;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity
public class Schedule implements Serializable{

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name="dayDB")
    private String dayDB;

    @ColumnInfo(name="startTimeDB")
    private String startTimeDB;

    @ColumnInfo(name="tasksDB")
    private String tasksDB;

    @ColumnInfo(name="labelDB")
    private String labelDB;

    @ColumnInfo(name="locationDB")
    private String locationDB;

    public Schedule(){

    }

    public Schedule(String Tasks, String StartTime, String Label) {
        tasksDB=Tasks;
        startTimeDB=StartTime;
        labelDB=Label;
    }
   /* private Integer credits;

    private Float pointer;*/

    public Integer getId(){
        return id;
    }
    public  String getDay(){
        return dayDB;
    }
    private String getStartTime(){
        return startTimeDB;
    }
    private String getTasks(){
        return tasksDB;
    }
    private String getLabel(){
        return labelDB;
    }
    private String getLocation(){
        return labelDB;
    }
    /*
    public Integer getCredits(){
        return credits;
    }
    public Float getPointer(){
        return pointer;
    }
    */

    public void setId(int Id){
        id=Id;
    }
    public void setDay(String Day){
        dayDB=Day;
    }
    public void setTasks(String Tasks){
        tasksDB=Tasks;
    }
    public void setStartTime(String StartTime)
    {
        startTimeDB=StartTime;
    }
    public void setLabel(String Label)
    {
        labelDB=Label;
    }
    public void setLocation(String Location)
    {
        locationDB=Location;
    }




}
