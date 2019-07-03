package com.nirmalhk7.nirmalhk7.examholidays;

public class hsItem {

    private int mholidayOrExam;
    private String mTitle;
    private String mDate;

    public hsItem(int holiday1Exam0, String title, String date) {
        mholidayOrExam = holiday1Exam0;
        mTitle = title;
        mDate=date;
    }
    /**
     * Get the default translation of the word.
     */
    public int getHolidayOrExam(){
        return mholidayOrExam;
    }
    public String getTitle(){
        return mTitle;
    }
    public String getmDate() {
        return mDate;
    }
}
