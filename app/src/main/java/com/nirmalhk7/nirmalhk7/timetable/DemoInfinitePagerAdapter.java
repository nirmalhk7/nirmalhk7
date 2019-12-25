package com.nirmalhk7.nirmalhk7.timetable;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.nirmalhk7.nirmalhk7.Converters;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.timetable.ScheduleAdapter;
import com.nirmalhk7.nirmalhk7.timetable.ScheduleEntity;
import com.nirmalhk7.nirmalhk7.timetable.scheduleDAO;
import com.nirmalhk7.nirmalhk7.timetable.scheduleItem;

import java.util.ArrayList;
import java.util.List;

public class DemoInfinitePagerAdapter extends LoopingPagerAdapter<Integer> {
    public Toolbar t;
    public DemoInfinitePagerAdapter(Context context, ArrayList<Integer> itemList, boolean isInfinite, Toolbar toolbar) {
        super(context, itemList, isInfinite);
        t=toolbar;
    }

    //This method will be triggered if the item View has not been inflated before.
    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {
        View listview= LayoutInflater.from(context).inflate(R.layout.timetable_list, container, false);
       // TTFetch(listview,context,listPosition);
        return listview;
    }

    //Bind your data with your item View here.
    //Below is just an example in the demo app.
    //You can assume convertView will not be null here.
    //You may also consider using a ViewHolder pattern.
    @Override
    protected void bindView(View convertView, int listPosition, int viewType) {
        //convertView.findViewById(R.id.image).setBackgroundColor(context.getResources().getColor(getBackgroundColor(listPosition)));
        TextView description = convertView.findViewById(R.id.description);
        description.setText(Converters.dayno_to_day(listPosition));
        swiperefresh(convertView,context,listPosition);
        TTFetch(convertView,context,listPosition);

    }
    private SwipeRefreshLayout pullToRefresh;
    void swiperefresh(final View rootview,final Context c,final int listpos){
        pullToRefresh = rootview.findViewById(R.id.pullToRefresh);
        pullToRefresh.setEnabled(true);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                Log.d("ATT/ALS","Refreshing");
                TTFetch(rootview,c,listpos);
                pullToRefresh.setRefreshing(false);
            }
        });
    }
    private static void TTFetch(View convertView,Context context,int listPosition)
    {
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");

        ArrayList<scheduleItem> sch = new ArrayList<scheduleItem>();

        DBGateway database = Room.databaseBuilder(context, DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        //scheduleDAO scheduleDAO = database.getScheduleDao();
        scheduleDAO SDAO=database.getScheduleDao();
        //Log.d("DAS/DS/X", "xx" + bundle.getInt("key"));
        List<ScheduleEntity> scheduleEntities = SDAO.getScheduleByDay(listPosition);
        for (ScheduleEntity cn : scheduleEntities) {

            Log.d("DAS/DSL", "Printing: Task " + cn.getTask() + " Time " + cn.getStartTime() + cn.getEndTime() + " Label " + cn.getLabel());
            sch.add(new scheduleItem(cn.getTask(), Converters.date_to_t12(cn.getStartTime()),Converters.date_to_t12(cn.getEndTime()), cn.getSubjCode(), cn.getLabel(), cn.getId(), cn.getDay()));
        }
    //    TextView description=convertView.findViewById(R.id.tt_dayreview);
    //    description.setText(scheduleEntities.size()+" classes from "+Converters.date_to(scheduleEntities.get(0).getStartTime(),"hh:mm a")+" to "+Converters.date_to(scheduleEntities.get(scheduleEntities.size()-1).getEndTime(),"hh:mm a"));
        ScheduleAdapter adapter=new ScheduleAdapter(context,sch);
        ListView listView=convertView.findViewById(R.id.list_item_timetable);
        listView.setAdapter(adapter);
    }

}