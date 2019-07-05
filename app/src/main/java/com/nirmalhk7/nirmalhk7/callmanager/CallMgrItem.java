package com.nirmalhk7.nirmalhk7.callmanager;

public class CallMgrItem {
    private String mgrNameX;
    private String mgrNoX;
    private int callTimeX;

    public CallMgrItem(String mgrName, String mgrNo,int callTime) {
        mgrNameX =mgrName;
        mgrNoX = mgrNo;
        callTimeX=callTime;
    }
    /**
     * Get the default translation of the word.
     */
    public String getName(){
        return mgrNameX;
    }
    public String getNo(){
        return mgrNoX;
    }
    public int getCallNo() {
        return callTimeX;
    }
}
