package com.nirmalhk7.nirmalhk7.dailyscheduler;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class schedule {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String day;
    private String startTime;
    private String tasks;
    private String label;
    private String location;

   /* private Integer credits;

    private Float pointer;*/

    public Integer getId(){
        return id;
    }
    public String getDay(){
        return day;
    }
    public String getStartTime(){
        return startTime;
    }
    public String getTasks(){
        return tasks;
    }
    public String getLabel(){
        return label;
    }
    public String getLocation(){
        return label;
    }
    /*
    public Integer getCredits(){
        return credits;
    }
    public Float getPointer(){
        return pointer;
    }
    */

    public void setId(int id){
        this.id=id;
    }
    public void setDay(String day){
        this.day=day;
    }
    public void setStartTime(String startTime)
    {
        this.startTime=startTime;
    }
    public void setLabel(String label)
    {
        this.label=label;
    }
    public void setLocation(String location)
    {
        this.location=location;
    }




}
