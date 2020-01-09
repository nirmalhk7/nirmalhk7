package com.nirmalhk7.nirmalhk7.controllers;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.model.AttendanceDAO;
import com.nirmalhk7.nirmalhk7.model.AttendanceEntity;
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

    public TimetableController(View rootView,Context context)
    {
        mRootView=rootView;
        mContext=context;
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
        Log.d(getClass().getName(),"Fetching List of Size:"+scheduleEntities.size());

        return scheduleEntities;

    }

    public void swipeToRefresh(final SwipeRefreshLayout pullToRefresh,final TimetableController ttController,final TimetableArrayAdapter ttadapter){

        pullToRefresh.setEnabled(true);
        ttadapter.clear();
        ttadapter.notifyDataSetChanged();
        ttadapter.addAll(ttController.TTFetch());
        ttadapter.notifyDataSetChanged();
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
    public String[] insertSuggestions(){
        DBGateway database = Room.databaseBuilder(mContext, DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        final TimetableDAO SDAO = database.getTTDao();
        AttendanceDAO attendanceDAO=database.getATTDao();

        List<TimetableEntity> x = SDAO.getSubjects("College");

        List<AttendanceEntity> z = attendanceDAO.getSubjectNames();
        String[] subject = new String[x.size()+z.size()];

        int i = 0;

        for (TimetableEntity cn : x) {
            subject[i] = cn.getTask();
            Log.d(getClass().getName(), "Autocomplete Local "+subject[i]);
            ++i;
        }
        for (AttendanceEntity cn: z)
        {
            subject[i]=cn.getSubject();
            Log.d(getClass().getName(),"Autocomplete From Attendance: "+subject[i]);
            ++i;
        }
        return subject;
    }
    public void deleteTimetableEntry(int dbNo)
    {
        DBGateway database = Room.databaseBuilder(mContext, DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        com.nirmalhk7.nirmalhk7.timetable.TimetableDAO SDAO=database.getTTDao();
        Log.d(getClass().getName(), dbNo+" DB Deleted");
        SDAO.deleteSchedule(SDAO.getScheduleById(dbNo));
    }

    public void saveTimetable(String taskName,String taskCode,String startTime,String endTime,int day,
                              boolean editingOrNot,int dbNo){



        //  db.addSchedule(new TimetableEntity("Task 1","Label 1","Time 1"));

        DBGateway database = Room.databaseBuilder(mContext, DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();
        TimetableDAO SDAO = database.getTTDao();

        TimetableEntity scheduleEntity;
        if (editingOrNot) {
            scheduleEntity = SDAO.getScheduleById(dbNo);




        } else {
            scheduleEntity = new TimetableEntity();
        }
        scheduleEntity.setTask(taskName);
        scheduleEntity.setSubjCode(taskCode);
        scheduleEntity.setStartTime(Converters.to_date(startTime,"hh:mm a"));
        scheduleEntity.setEndTime(Converters.to_date(endTime,"hh:mm a"));
        scheduleEntity.setDay(day);
        Log.d(getClass().getName(),"Day saved: "+day);
        if(editingOrNot)
            SDAO.updateSchedule(scheduleEntity);
        else
            SDAO.insertOnlySingleSchedule(scheduleEntity);

    }
    public void refreshOnSave(TimetableArrayAdapter adapter,TimetableController ttController)
    {
        adapter.clear();
        adapter.notifyDataSetChanged();
        adapter.addAll(ttController.TTFetch());
        adapter.notifyDataSetChanged();
        swipeToRefresh((SwipeRefreshLayout)mRootView.findViewById(R.id.pullToRefresh),ttController,ttadapter);
    }


}
