package com.nirmalhk7.nirmalhk7.dailyscheduler;

public class Schedule {

    /** Default translation for the word */
    private String mScheduleTitle;

    /** Miwok translation for the word */
    private String mTime;
    private String mLabel;
    private Integer mId;
    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation is the word in the Miwok language
     */
    public Schedule(String defaultTranslation, String miwokTranslation, String Label) {
        mScheduleTitle = defaultTranslation;
        mTime = miwokTranslation;
        mLabel=Label;

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
