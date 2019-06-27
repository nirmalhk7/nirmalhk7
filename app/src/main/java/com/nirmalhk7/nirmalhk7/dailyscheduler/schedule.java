package com.nirmalhk7.nirmalhk7.dailyscheduler;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class schedule {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private static String day;
    private static String startTime;
    private static String tasks;
    private static String label;
    private static String location;

   /* private Integer credits;

    private Float pointer;*/

    public Integer getId(){
        return id;
    }
    public static String getDay(){
        return day;
    }
    public static String getStartTime(){
        return startTime;
    }
    public static String getTasks(){
        return tasks;
    }
    public static String getLabel(){
        return label;
    }
    public static String getLocation(){
        return label;
    }
    /*
    public static Integer getCredits(){
        return credits;
    }
    public static Float getPointer(){
        return pointer;
    }
    */

    public void setId(int Id){
        id=Id;
    }
    public static void setDay(String Day){
        day=Day;
    }
    public static void setTasks(String Tasks){
        tasks=Tasks;
    }
    public static void setStartTime(String StartTime)
    {
        startTime=StartTime;
    }
    public static void setLabel(String Label)
    {
        label=Label;
    }
    public static void setLocation(String Location)
    {
        location=Location;
    }




}
