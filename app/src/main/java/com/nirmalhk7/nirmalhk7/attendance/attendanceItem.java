package com.nirmalhk7.nirmalhk7.attendance;

public class attendanceItem {

    /** Default translation for the word */
    private String mSubjName;

    /** Miwok translation for the word */
    private String mSubjDate;
    private String  mSubjTime;


    public attendanceItem(String subjName, String subjDate,String subjTime)
    {
        mSubjName=subjName;
        mSubjDate=subjDate;
        mSubjTime=subjTime;
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
    public String getmSubjDate() {
        return mSubjDate;
    }
    public String getmSubjTime(){   return mSubjTime;}
}
