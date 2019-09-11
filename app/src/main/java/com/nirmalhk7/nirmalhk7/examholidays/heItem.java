package com.nirmalhk7.nirmalhk7.examholidays;

public class heItem {

    private int holexa;
    private String mTitle;
    private String mDate;
    private String mType;

    public heItem(int holiday1Exam0, String title, String date,String Type) {
        holexa = holiday1Exam0;
        mTitle = title;
        mDate=date;
        mType=Type;
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
}
