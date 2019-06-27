package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabAdapter extends FragmentStatePagerAdapter {
    public TabAdapter(FragmentManager fm){
        super(fm);
    }
    @Override    public Fragment getItem(int position) {
        switch (position){
            case 0: return new DailyScheduleList();
            case 1: return new DailyScheduleList();
            case 2: return new DailyScheduleList();
            case 3: return new DailyScheduleList();
            case 4: return new DailyScheduleList();
            case 5: return new DailyScheduleList();
            case 6: return new DailyScheduleList();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 7;
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
