package com.nirmalhk7.nirmalhk7.cpschedule;

public class cpItem {

    private int upcOrAct;
    private String CName;
    private String Curl;
    private String Hname;
    private String Time;
    private String Duration;
    private int mId;

    public cpItem(int id,String name,String hn,String url,String time,String duration,int upact) {
        upcOrAct=upact;
        CName=name;
        Curl=url;
        Hname=hn;
        Time=time;
        Duration=duration;
        mId=id;
    }
    /**
     * Get the default translation of the word.
     */
    public int getUpcOrAct() {
        return upcOrAct;
    }

    public String getCName() {
        return CName;
    }

    public String getCurl() {
        return Curl;
    }

    public String getDuration() {
        return Duration;
    }

    public String getHname() {
        return Hname;
    }

    public String getTime() {
        return Time;
    }

    public int getmId() {
        return mId;
    }
}
