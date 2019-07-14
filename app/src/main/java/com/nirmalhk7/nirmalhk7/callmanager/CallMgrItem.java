package com.nirmalhk7.nirmalhk7.callmanager;

public class CallMgrItem {
    private String mgrNameX;
    private String callNo;
    private int callTimeX;

    public CallMgrItem(String mgrName, String mCallNo,int callTime) {
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
