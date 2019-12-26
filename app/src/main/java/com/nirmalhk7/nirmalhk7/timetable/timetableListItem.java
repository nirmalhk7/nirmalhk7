package com.nirmalhk7.nirmalhk7.timetable;


public class timetableListItem {

    /** Default translation for the word */
    private String mScheduleTitle;

    /** Miwok translation for the word */
    private String mStartTime;
    private String mEndTime;
    private String mLabel;
    private String mSubjCode;
    private int mId;
    private int mDay;






    public timetableListItem(String scheduleTitle, String starttime, String endtime, String SubjCode, String label, int Id, int Day) {
        mScheduleTitle = scheduleTitle;
        mStartTime=starttime;
        mEndTime=endtime;
        mLabel=label;
        mId=Id;
        mSubjCode=SubjCode;
        mDay=Day;
    }
    public timetableListItem()
    {}

    public String getmSubjCode(){
        return mSubjCode;
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

    public void setmEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    public void setmLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public void setmScheduleTitle(String mScheduleTitle) {
        this.mScheduleTitle = mScheduleTitle;
    }

    public void setmStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public void setmSubjCode(String mSubjCode) {
        this.mSubjCode = mSubjCode;
    }
}
