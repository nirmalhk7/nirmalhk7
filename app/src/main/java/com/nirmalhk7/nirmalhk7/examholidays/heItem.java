package com.nirmalhk7.nirmalhk7.examholidays;

public class heItem {

    private int holexa;
    private String mTitle;
    private String mDate;

    public heItem(int holiday1Exam0, String title, String date) {
        holexa = holiday1Exam0;
        mTitle = title;
        mDate=date;
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
    public String getmDate() {
        return mDate;
    }
}
