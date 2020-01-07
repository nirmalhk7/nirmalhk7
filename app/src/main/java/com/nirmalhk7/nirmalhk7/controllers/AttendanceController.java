package com.nirmalhk7.nirmalhk7.controllers;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.attendance.AttendanceArrayAdapter;
import com.nirmalhk7.nirmalhk7.model.AttendanceDAO;
import com.nirmalhk7.nirmalhk7.model.AttendanceEntity;
import com.nirmalhk7.nirmalhk7.model.AttendanceListItem;

import java.util.ArrayList;
import java.util.List;

public class AttendanceController {
    private Context mContext;

    public AttendanceController(Context context){
        mContext=context;
    }
    public ArrayList<AttendanceListItem> fetchAttendance(){
        DBGateway database2 = Room.databaseBuilder(mContext, DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        AttendanceDAO AttendanceDAO = database2.getATTDao();

        List<AttendanceEntity> attendance=AttendanceDAO.getAllSubject();
        ArrayList<AttendanceListItem> SubjectItem = new ArrayList<>();
        Log.d(getClass().getName(),"Count"+attendance.size());

        for (AttendanceEntity cn : attendance) {
            Log.d("ATT/ALS", "Printing: Task "+cn.getSubject()+" P "+cn.getPresent()+" A "+cn.getAbsent());
            SubjectItem.add(new AttendanceListItem(cn.getSubject(), cn.getPresent(),cn.getAbsent(),cn.getId()));
        }
        return SubjectItem;
    }


    public void swipeToRefresh(final View rootview, final AttendanceArrayAdapter attAdapter){
        final SwipeRefreshLayout pullToRefresh = rootview.findViewById(R.id.pullToRefresh);
        pullToRefresh.setEnabled(true);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                Log.d("ATT/ALS","Refreshing");
                //   ALSfetchDB(rootview);
                attAdapter.clear();
                attAdapter.notifyDataSetChanged();
                ArrayList<AttendanceListItem> newlist=fetchAttendance();
                attAdapter.addAll(newlist);
                attAdapter.notifyDataSetChanged();


                pullToRefresh.setRefreshing(false);
            }
        });
    }
}
