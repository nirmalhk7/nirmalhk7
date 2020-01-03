package com.nirmalhk7.nirmalhk7.timetable;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.util.timeconv;

import java.util.ArrayList;
import java.util.List;

public class TimetableLoopingPagerAdapter extends LoopingPagerAdapter<Integer> {
    public static FragmentManager Fmgr;
    public static TimetableArrayAdapter ttadapter;
    public TimetableLoopingPagerAdapter(Context context, ArrayList<Integer> itemList, boolean isInfinite, FragmentManager fmgr) {
        super(context, itemList, isInfinite);
         Fmgr=fmgr;
    }

    //This method will be triggered if the item View has not been inflated before.
    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {
        View listview= LayoutInflater.from(context).inflate(R.layout.fragment_timetable_list, container, false);
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
        description.setText(timeconv.dayno_to_day(listPosition));
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
    public static void TTFetch(View convertView, final Context context, final int listPosition)
    {
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");

        ArrayList<TimetableListItem> sch = new ArrayList<TimetableListItem>();

        DBGateway database = Room.databaseBuilder(context, DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        //TimetableDAO TimetableDAO = database.getScheduleDao();
        TimetableDAO SDAO=database.getTTDao();
        //Log.d("DAS/DS/X", "xx" + bundle.getInt("key"));
        List<TimetableEntity> scheduleEntities = SDAO.getScheduleByDay(listPosition);
        for (TimetableEntity cn : scheduleEntities) {

            Log.d("DAS/DSL", "Printing: Task " + cn.getTask() + " Time " + cn.getStartTime() + cn.getEndTime() + " Label " + cn.getLabel());
            sch.add(new TimetableListItem(cn.getTask(), timeconv.date_to(cn.getStartTime(),"hh:mm a"), timeconv.date_to(cn.getEndTime(),"hh:mm a"), cn.getSubjCode(), cn.getLabel(), cn.getId(), cn.getDay()));
        }
    //    TextView description=convertView.findViewById(R.id.tt_dayreview);
    //    description.setText(scheduleEntities.size()+" classes from "+timeconv.date_to(scheduleEntities.get(0).getStartTime(),"hh:mm a")+" to "+timeconv.date_to(scheduleEntities.get(scheduleEntities.size()-1).getEndTime(),"hh:mm a"));
        ttadapter=new TimetableArrayAdapter(context,sch);
        ListView listView=convertView.findViewById(R.id.list_item_timetable);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DAS/DSL", "LongClicked!");

                TextView title = view.findViewById(R.id.miwok_text_view);
                TextView starttime = view.findViewById(R.id.start_time);
                TextView endTime = view.findViewById(R.id.end_time);
                TextView idx = view.findViewById(R.id.itemid);
                TextView subjcode=view.findViewById(R.id.fsd_subjabbr);
                Log.d("CONVERTX", endTime + "-" + starttime);


                TimetableDialog newFragment = new TimetableDialog();
               // FragmentManager fragmentManager = ctvt.getSupportFragmentManager();


                Bundle args = new Bundle();
                args.putInt("key", Integer.parseInt(idx.getText().toString()));
                args.putString("title", title.getText().toString());
                args.putString("starttime", starttime.getText().toString());
                args.putString("endtime", endTime.getText().toString());
                args.putString("subjcode",subjcode.getText().toString());
                args.putInt("day", listPosition);

                Log.d("DS", "PSN:" + position);
                newFragment.setArguments(args);
                FragmentTransaction transaction = Fmgr.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
                return false;
            }
        });

        listView.setAdapter(ttadapter);
    }

}