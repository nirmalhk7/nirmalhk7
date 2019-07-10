package com.nirmalhk7.nirmalhk7.examholidays;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ehEntity {
    @PrimaryKey(autoGenerate = true)
    int id;
    int holexa;

    String mName;
    String mDateStart;
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

    public String getmDateEnd() {
        return mDateEnd;
    }

    public String getmDateStart() {
        return mDateStart;
    }

    public String getmType() {
        return mType;
    }

    public String getmDescription() {
        return mDescription;
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

    public void setmDateEnd(String mDateEnd) {
        this.mDateEnd = mDateEnd;
    }

    public void setmDateStart(String mDateStart) {
        this.mDateStart = mDateStart;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }
}
