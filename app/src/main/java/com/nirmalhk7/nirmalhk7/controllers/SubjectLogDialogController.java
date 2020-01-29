package com.nirmalhk7.nirmalhk7.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.ArrayAdapters.AttendanceArrayAdapter;
import com.nirmalhk7.nirmalhk7.model.AttendanceListItem;
import com.nirmalhk7.nirmalhk7.model.SubjectlogDAO;
import com.nirmalhk7.nirmalhk7.model.SubjectlogEntity;

import java.util.ArrayList;
import java.util.List;

public class SubjectLogDialogController {
    Context mContext;
    public SubjectLogDialogController(Context context)
    {
        mContext=context;
    }

    private void onLongClickDelete(View rootView, ListView listView)
    {

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to delete attendance record of this subject on this day?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Clicked YES
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                return false;
            }
        });
    }
    public void SALFetch(ListView listView,String subj)
    {
        DBGateway database2 = DBGateway.getInstance(mContext);
        SubjectlogDAO SLDAO = database2.getSALDAO();
        List<SubjectlogEntity> subjLog=SLDAO.getAllLog(subj);


        ArrayList<AttendanceListItem> SubjectItem = new ArrayList<>();
        Log.d("ATT/ALS","Count"+subjLog.size());

        for (SubjectlogEntity cn : subjLog) {
            //   Log.d("ATT/ALS", "Printing: Task "+cn.getSubject()+" P "+cn.getPresent()+" A "+cn.getAbsent());
            SubjectItem.add(new AttendanceListItem(Converters.date_to(cn.getDaytime(),"dd MMM yyyy"), Converters.date_to(cn.getDaytime(),"EEE")+", "+ Converters.date_to(cn.getDaytime(),"hh:mm a"),cn.getPrabca(),cn.getId()));
        }
        AttendanceArrayAdapter adapter = new AttendanceArrayAdapter(mContext, SubjectItem,2);
        listView.setAdapter(adapter);
    }
    public void swiperefresh(final View rootview, final String subj, final SwipeRefreshLayout pullToRefresh, final ListView listView){
        pullToRefresh.setEnabled(true);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                Log.d("ATT/ALS","Refreshing");
                SALFetch(listView,subj);
                pullToRefresh.setRefreshing(false);
            }
        });
    }
}
