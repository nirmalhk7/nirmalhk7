package com.nirmalhk7.nirmalhk7.model;

public class CallManagerListItem {
    private String mgrNameX;
    private String callNo;
    private int callTimeX;

    public CallManagerListItem(String mgrName, String mCallNo, int callTime) {
        mgrNameX =mgrName;
        callNo = mCallNo;
        callTimeX=callTime;
    }
    /**
     * Get the default translation of the word.
     */
    public String getName(){
        return mgrNameX;
    }
    public String getNo(){
        return callNo;
    }
    public int getCallNo() {
        return callTimeX;
    }
}
