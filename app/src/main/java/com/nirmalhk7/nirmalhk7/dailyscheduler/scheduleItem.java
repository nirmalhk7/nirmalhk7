package com.nirmalhk7.nirmalhk7.dailyscheduler;

public class scheduleItem {

    /** Default translation for the word */
    private String mScheduleTitle;

    /** Miwok translation for the word */
    private String mTime;
    private String mLabel;
    private Integer mId;


    public scheduleItem(String scheduleTitle, String time, String label) {
        mScheduleTitle = scheduleTitle;
        mTime = time;
        mLabel=label;
    }
    /**
     * Get the default translation of the word.
     */
    public String getScheduleTitle() {
        return mScheduleTitle;
    }

    /**
     * Get the Miwok translation of the word.
     */
    public String getScheduleTime() {
        return mTime;
    }
    public String getScheduleLabel() {
        return mLabel;
    }
    public Integer getScheduleId(){ return mId;}
   
}
