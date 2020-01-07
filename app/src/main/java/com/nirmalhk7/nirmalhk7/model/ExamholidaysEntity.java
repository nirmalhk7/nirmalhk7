package com.nirmalhk7.nirmalhk7.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


import java.util.Date;

@Entity
public class ExamholidaysEntity {
    @PrimaryKey(autoGenerate = true)
    int id;
    int holexa;

    String mName;
    String mDateStart;
    Date Start;
    Date End;
    String mDateEnd;
    String mType;
    String mDescription;

    public int getId() {
        return id;
    }

    public int getHolexa() {
        return holexa;
    }

    public String getmName() {
        return mName;
    }

    public String getmType() {
        return mType;
    }

    public String getmDescription() {
        return mDescription;
    }

    public Date getEnd() {
        return End;
    }

    public Date getStart() {
        return Start;
    }

    public void setEnd(Date end) {
        End = end;
    }

    public void setStart(Date start) {
        Start = start;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setHolexa(int holexa) {
        this.holexa = holexa;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }
}
