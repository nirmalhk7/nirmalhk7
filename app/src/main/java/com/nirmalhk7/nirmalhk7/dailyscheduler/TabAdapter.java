package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabAdapter extends FragmentStatePagerAdapter {
    public static Integer mposition;
    public TabAdapter(FragmentManager fm){
        super(fm);
    }
    @Override    public Fragment getItem(int position) {
        mposition=position;
        return new DailyScheduleList();
    }
    @Override
    public int getCount() {
        return 7;
    }

    public static int getPosition(){
        return mposition;
    }

    @Override    public CharSequence getPageTitle(int position) {switch (position){
        case 0: return "Sunday";
        case 1: return "Monday";
        case 2: return "Tuesday";
        case 3: return "Wednesday";
        case 4: return "Thursday";
        case 5: return "Friday";
        case 6: return "Saturday";
        default: return null;
    }
    }
}
