package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    //Make LOOP_COUNT 1 for single loop.
    public static int LOOPS_COUNT = 1;
    private ArrayList<String> mProducts;


    public ViewPagerAdapter(FragmentManager manager)
    {
        super(manager);
        mProducts=new ArrayList<>();
        mProducts.add("Monday");
        mProducts.add("Tuesday");
        mProducts.add("Wednesday");
        mProducts.add("Thursday");
        mProducts.add("Friday");
        mProducts.add("Saturday");
        mProducts.add("Sunday");
    }


    @Override
    public Fragment getItem(int position)
    {
        if (mProducts != null && mProducts.size() > 0)
        {
            position = position % mProducts.size();
            DailyScheduleList dsl=new DailyScheduleList();
            Bundle b=new Bundle();
            b.putInt("key",position);
            Log.d("DAS/DS","Swipe Registered: "+position);
            dsl.setArguments(b);
            return dsl;
        }
        else
        {
            return null;
        }
    }


    @Override
    public int getCount()
    {
        if (mProducts != null && mProducts.size() > 0)
        {
            return mProducts.size()*LOOPS_COUNT; // simulate infinite by big number of products
        }
        else
        {
            return 1;
        }
    }





    @Override
    public CharSequence getPageTitle(int position) {
        int fposition=position%7;
        switch(fposition) {
            case 0: return "Monday";
            case 1: return "Tuesday";
            case 2: return "Wednesday";
            case 3: return "Thursday";
            case 4: return "Friday";
            case 5: return "Saturday";
            case 6: return "Sunday";
        }
        return null;
    }


}
