package com.nirmalhk7.nirmalhk7.controllers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.examholidays.ExamHolidayArrayAdapter;
import com.nirmalhk7.nirmalhk7.examholidays.ExamHolidaysDialog;
import com.nirmalhk7.nirmalhk7.model.ExamholidaysDAO;
import com.nirmalhk7.nirmalhk7.model.ExamholidaysEntity;

import java.util.List;

public class ExamHolidaysController implements FragmentControllerInterface {
    private View mRootView;
    private Context mContext;
    private ExamHolidayArrayAdapter adapter;
    private DBGateway database;
    public ExamHolidaysController(View rootView, Context context, ListView listView)
    {

        mRootView=rootView;
        mContext=context;
        database = DBGateway.getInstance(mContext);
        attachAdapter(listView);
    }
    List<ExamholidaysEntity> getEAHList()
    {
        ExamholidaysDAO examholidaysDAO = database.getEHDAO();
        return examholidaysDAO.getEventsOrdered();
    }

    @Override
    public void attachAdapter(ListView listView)
    {
        adapter = new ExamHolidayArrayAdapter(mContext, getEAHList());
        listView.setAdapter(adapter);
    }



    @Override
    public void speedDialOnClick(SpeedDialView speed,final FragmentManager fragmentManager)
    {
        speed.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                ExamHolidaysDialog newFragment = new ExamHolidaysDialog();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                Bundle b = new Bundle();
                switch (speedDialActionItem.getId()) {
                    case R.id.content:
                        Log.d("ATT/ALS", "Select Exams");

                        b.putInt("key", 0);
                        newFragment.setArguments(b);
                        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
                        return false;
                    case R.id.label_container:
                        Log.d("ATT/ALS", "Select Holidays");
                        b.putInt("key", 1);
                        newFragment.setArguments(b);
                        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
                        return false; // true to keep the Speed Dial open
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public void addSpeedDialOptions(SpeedDialView speed, Resources resources, Resources.Theme theme)
    {
        speed.addActionItem(
                new SpeedDialActionItem.Builder(R.id.content, R.drawable.ic_examholidays)
                        .setLabel("Add Exam")
                        .setLabelColor(Color.WHITE)
                        .setFabBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorWarning, theme))
                        .setLabelBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorLightDark, theme))
                        .create()
        );
        speed.addActionItem(new SpeedDialActionItem.Builder(R.id.label_container, R.drawable.ic_examholidays)
                .setLabel("Add Holiday")
                .setLabelColor(Color.WHITE)
                .setFabBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorGreen, theme))
                .setLabelBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorLightDark, theme))
                .create()
        );
    }
    @Override
    public void swipeToRefresh(final SwipeRefreshLayout pullToRefresh) {
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                Log.d("ATT/ALS", "Refreshing");
                adapter.clear();
                adapter.notifyDataSetChanged();
                adapter.addAll(getEAHList());
                adapter.notifyDataSetChanged();
                pullToRefresh.setRefreshing(false);
            }
        });
    }
}
