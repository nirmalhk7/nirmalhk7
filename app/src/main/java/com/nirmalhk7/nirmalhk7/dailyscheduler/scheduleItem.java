package com.nirmalhk7.nirmalhk7.dailyscheduler;

public class scheduleItem {

    /** Default translation for the word */
    private String mScheduleTitle;

    /** Miwok translation for the word */
    private String mStartTime;
    private String mEndTime;
    private String mLabel;
    private int mId;
    private int mDay;


    public scheduleItem(String scheduleTitle, String starttime,String endtime, String label,int Id,int Day) {
        mScheduleTitle = scheduleTitle;
        mStartTime=starttime;
        mEndTime=endtime;
        mLabel=label;
        mId=Id;
        mDay=Day;
    }

    public String getScheduleTitle() {
        return mScheduleTitle;
    }

    public String getmStartTime() {
        return mStartTime;
    }

    public String getmEndTime() {
        return mEndTime;
    }

    public String getScheduleLabel() {
        return mLabel;
    }
    public int getScheduleId(){ return mId;}
    public int getDay(){return mDay;}
   
}
