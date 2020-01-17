package com.nirmalhk7.nirmalhk7.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.widget.ListView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.attendance.AttendanceArrayAdapter;
import com.nirmalhk7.nirmalhk7.attendance.AttendanceDialogFragment;
import com.nirmalhk7.nirmalhk7.model.AttendanceDAO;
import com.nirmalhk7.nirmalhk7.model.AttendanceEntity;
import com.nirmalhk7.nirmalhk7.model.AttendanceListItem;

import java.util.ArrayList;
import java.util.List;

public class AttendanceController implements FragmentControllerInterface {
    private Context mContext;
    private AttendanceArrayAdapter mAttendanceAdapter;
    private Activity mActivity;
    public AttendanceController(Context context,Activity activity){
        
        mContext=context;
        mActivity=activity;
    }

    @Override
    public void speedDialOnClick(SpeedDialView speed, final FragmentManager fragmentManager) {
        speed.setOnActionSelectedListener( new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.content:
                        Log.d("ATT/ALS","Selct");

                        AttendanceDialogFragment newFragment = new AttendanceDialogFragment();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
                        return false; // true to keep the Speed Dial open
                    default:
                        return false;
                }
            }
        });
    }


    @Override
    public void attachAdapter(ListView listView) {
        mAttendanceAdapter = new AttendanceArrayAdapter(mContext, fetchAttendance());
        listView.setAdapter(mAttendanceAdapter);
    }

    @Override
    public void addSpeedDialOptions(SpeedDialView speed, Resources resources, Resources.Theme theme) {
        speed.addActionItem(
                new SpeedDialActionItem.Builder(R.id.content, R.drawable.ic_examholidays)
                        .setLabel("Add Subject")
                        .setLabelColor(Color.WHITE)
                        .setLabelBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorLightDark, theme))
                        .create()
        );
    }

    public ArrayList<AttendanceListItem> fetchAttendance(){
        DBGateway database2 = DBGateway.getInstance(mContext);

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

    @Override
    public void swipeToRefresh(final SwipeRefreshLayout pullToRefresh ){
        pullToRefresh.setEnabled(true);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                Log.d("ATT/ALS","Refreshing");
                //   ALSfetchDB(rootview);
                mAttendanceAdapter.clear();
                mAttendanceAdapter.notifyDataSetChanged();
                ArrayList<AttendanceListItem> newlist=fetchAttendance();
                mAttendanceAdapter.addAll(newlist);
                mAttendanceAdapter.notifyDataSetChanged();


                pullToRefresh.setRefreshing(false);
            }
        });
    }
}
