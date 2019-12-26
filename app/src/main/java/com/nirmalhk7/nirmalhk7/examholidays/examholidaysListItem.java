package com.nirmalhk7.nirmalhk7.examholidays;

public class examholidaysListItem {

    private int holexa;
    private String mTitle;
    private String mDate;
    private String mType;
    private int mId;

    public examholidaysListItem(int id, int holiday1Exam0, String title, String date, String Type) {
        holexa = holiday1Exam0;
        mTitle = title;
        mDate=date;
        mType=Type;
        mId=id;
    }
    /**
     * Get the default translation of the word.
     */
    public int getHolidayOrExam(){
        return holexa;
    }
    public String getTitle(){
        return mTitle;
    }
    public String getmType(){return mType;}
    public String getmDate() {
        return mDate;
    }
    public int getmId(){ return mId;}
}
