package com.nirmalhk7.nirmalhk7.dailyscheduler;

public class Schedule {
    static int _id;
    String _task;
    String _label;
    String _time;

    public Schedule(){   }
    public Schedule(int id, String name, String _label){
        _id = id;
        this._task = name;
        this._label = _label;
    }

    public Schedule(String task, String _label,String time){
        this._task = task;
        this._label = _label;
        this._time=time;
    }
    public static int getID(){
        return _id;
    }

    public void setID(int id){
        _id = id;
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
        return this._label;
    }
    public void setTime(String time){
        this._time = time;
    }
}