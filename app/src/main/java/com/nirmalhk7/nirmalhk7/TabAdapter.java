package com.nirmalhk7.nirmalhk7;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentStatePagerAdapter {
    public TabAdapter(FragmentManager fm){
        super(fm);
    }
    @Override    public Fragment getItem(int position) {
        switch (position){
            case 0: return new DailyScheduleList();
            case 1: return new DailyScheduleList();
            case 2: return new Academics();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 3;
    }
    @Override    public CharSequence getPageTitle(int position) {switch (position){
        case 0: return "Tab X";
        case 1: return "Tab 2";
        default: return null;
    }
    }
}
