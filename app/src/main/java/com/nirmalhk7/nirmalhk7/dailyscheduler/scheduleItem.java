package com.nirmalhk7.nirmalhk7.dailyscheduler;

public class scheduleItem {

    /** Default translation for the word */
    private String mScheduleTitle;

    /** Miwok translation for the word */
    private String mTime;
    private String mLabel;
    private int mId;
    private int mDay;


    public scheduleItem(String scheduleTitle, String time, String label,int Id,int Day) {
        mScheduleTitle = scheduleTitle;
        mTime = time;
        mLabel=label;
        mId=Id;
        mDay=Day;
    }

    public String getScheduleTitle() {
        return mScheduleTitle;
    }

    public String getScheduleTime() {
        return mTime;
    }
    public String getScheduleLabel() {
        return mLabel;
    }
    public int getScheduleId(){ return mId;}
    public int getDay(){return mDay;}
   
}
