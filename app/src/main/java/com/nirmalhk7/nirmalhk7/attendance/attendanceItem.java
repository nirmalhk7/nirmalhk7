package com.nirmalhk7.nirmalhk7.attendance;

public class attendanceItem {

    /** Default translation for the word */
    private String mSubjName;

    /** Miwok translation for the word */
    private String mSubjDate;
    private String  mSubjTime;

    private int mPresent;
    private int mAbsent;
    private int mCancelled;



    public attendanceItem(String subjName,int presentCt,int absentCt,int cancelledCt)
    {
        mSubjName=subjName;
        mPresent=presentCt;
        mAbsent=absentCt;
        mCancelled=cancelledCt;
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
    public int getmPresent(){
        return mPresent;
    }
    public int getmAbsent(){
        return mAbsent;
    }

}
