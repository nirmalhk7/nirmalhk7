package com.nirmalhk7.nirmalhk7.Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.daimajia.swipe.SwipeLayout;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.ArrayAdapters.AttendanceArrayAdapter;
import com.nirmalhk7.nirmalhk7.DialogFragments.AttendanceDialogFragment;
import com.nirmalhk7.nirmalhk7.model.AttendanceDAO;
import com.nirmalhk7.nirmalhk7.model.AttendanceEntity;
import com.nirmalhk7.nirmalhk7.model.AttendanceListItem;

import java.util.ArrayList;
import java.util.List;

public class AttendanceController implements FragmentControllerInterface {
    private Context mContext;
    private AttendanceArrayAdapter mAttendanceAdapter;
    private Activity mActivity;
    private AttendanceListItem mCurrentItem;
    public AttendanceController(Context context,Activity activity){
        
        mContext=context;
        mActivity=activity;
    }
    public AttendanceController(Context context){
        mContext=context;
    }
    public void swipeLayout(final SwipeLayout swipeLayout, final ImageView deleteb, final ImageView editb, AttendanceListItem attListItem)
    {
        mCurrentItem=attListItem;
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

                DBGateway database = DBGateway.getInstance(mContext);

                final AttendanceDAO attendanceDAO = database.getATTDao();
                deleteb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AttendanceEntity x=attendanceDAO.getSubjectbyId(mCurrentItem.getmId());
                        attendanceDAO.deleteSchedule(x);
                        swipeLayout.close();

                    }
                });
                editb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("ATT/ATA","Clicked");
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        AttendanceDialogFragment newFragment = new AttendanceDialogFragment();
                        Bundle bundle=new Bundle();
                        bundle.putInt("key", mCurrentItem.getmId());
                        bundle.putInt("present", mCurrentItem.getmPresent());
                        bundle.putInt("absent", mCurrentItem.getmAbsent());
                        bundle.putString("subject", mCurrentItem.getSubjName());
                        newFragment.setArguments(bundle);
                        activity.getSupportFragmentManager().beginTransaction().add(android.R.id.content, newFragment).addToBackStack(null).addToBackStack(null).commit();

                    }
                });
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });
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
    public void attachAdapter(ListView listView)
    {
        mAttendanceAdapter = new AttendanceArrayAdapter(mContext, fetchAttendance(),1);
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
    public void swipeToRefresh(final SwipeRefreshLayout pullToRefresh){
        pullToRefresh.setEnabled(true);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
