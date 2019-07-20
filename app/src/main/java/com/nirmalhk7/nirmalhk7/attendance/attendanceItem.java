package com.nirmalhk7.nirmalhk7.attendance;

public class attendanceItem {

    /** Default translation for the word */
    private String mSubjName;

    /** Miwok translation for the word */
private int mid;
    private int mPresent;
    private int mAbsent;
    private int mCancelled;
    private int mPRABCA;
    private String dateAdded;
    private String dayTime;



    public attendanceItem(String subjName,int presentCt,int absentCt,int id)
    {
        mSubjName=subjName;
        mPresent=presentCt;
        mAbsent=absentCt;
        mid=id;
    }

    public attendanceItem(String date,String dt,int prabca,int id)
    {

        mPRABCA=prabca;
        dateAdded=date;
        mid=id;
        dayTime=dt;

    }

    public int getmPRABCA(){
        return mPRABCA;
    }

    public String getDayTime(){
        return dayTime;
    }
    public String getDateAdded(){
        return dateAdded;
    }


    /**
     * Get the default translation of the word.
     */
    public String getSubjName() {
        return mSubjName;
    }

    /**
     * Get the Miwok translation of the word.
     */

    public int getmPresent(){
        return mPresent;
    }
    public int getmAbsent(){
        return mAbsent;
    }
    public int getmId(){return mid;}
}
