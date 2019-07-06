package com.nirmalhk7.nirmalhk7.dailyscheduler;

public class Schedule {
    static int _id;
    String _task;
    String _label;
    String _time;
    int _day;

    public Schedule(){   }
    public Schedule(int id, String name, String _label){
        this._id = id;
        this._task = name;
        this._label = _label;
    }

    public Schedule(String task, String _label,String time,int day){
        this._task = task;
        this._label = _label;
        this._time=time;
        this._day=day;
    }
    public static int getID(){
        return _id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getTask(){
        return this._task;
    }
    public void setTask(String name){
        this._task = name;
    }
    public String getLabel(){
        return this._label;
    }
    public void setLabel(String label){
        this._label = label;
    }
    public String getTime(){
        return this._time;
    }
    public void setTime(String time){
        this._time = time;
    }

    public int getDay(){return this._day;}
    public void setDay(int day){this._day=day;}
}