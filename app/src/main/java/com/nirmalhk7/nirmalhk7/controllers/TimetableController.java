package com.nirmalhk7.nirmalhk7.controllers;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.timetable.TimetableArrayAdapter;
import com.nirmalhk7.nirmalhk7.timetable.TimetableDAO;
import com.nirmalhk7.nirmalhk7.timetable.TimetableEntity;

import java.util.List;

public class TimetableController {
    private TimetableArrayAdapter ttadapter;
    private View mRootView;
    private Context mContext;
    private int mListPosition;

    public TimetableController(View rootView,Context context,int listPosition)
    {
        mRootView=rootView;
        mContext=context;
        mListPosition=listPosition;
    }

    public List<TimetableEntity> TTFetch()
    {
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");


        DBGateway database = Room.databaseBuilder(mContext, DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        TimetableDAO SDAO=database.getTTDao();
        List<TimetableEntity> scheduleEntities = SDAO.getScheduleByDay(mListPosition);


        return scheduleEntities;

    }
    private SwipeRefreshLayout pullToRefresh;
    public void swipeToRefresh(final View rootview,final TimetableController ttController,final TimetableArrayAdapter ttadapter){
        pullToRefresh = rootview.findViewById(R.id.pullToRefresh);
        pullToRefresh.setEnabled(true);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                Log.d("ATT/ALS","Refreshing");
                ttadapter.clear();
                ttadapter.notifyDataSetChanged();
                ttadapter.addAll(ttController.TTFetch());
                ttadapter.notifyDataSetChanged();
                pullToRefresh.setRefreshing(false);
            }
        });
    }
}
