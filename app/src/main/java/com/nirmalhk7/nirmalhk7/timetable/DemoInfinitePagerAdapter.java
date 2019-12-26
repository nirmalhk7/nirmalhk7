package com.nirmalhk7.nirmalhk7.timetable;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
    public static FragmentManager Fmgr;
    private static Context context;
    private static int listpos;
    public DemoInfinitePagerAdapter(Context ctext, ArrayList<Integer> itemList, boolean isInfinite, FragmentManager fmgr) {
        super(ctext, itemList, isInfinite);
         Fmgr=fmgr;
         context=ctext;
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
        ArrayList<scheduleItem> sch= TTFetch();


        ScheduleAdapter adapter=new ScheduleAdapter(context,sch);
        ListView listView=convertView.findViewById(R.id.list_item_timetable);
        listpos=listPosition;
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DAS/DSL", "LongClicked!");

                TextView title = view.findViewById(R.id.miwok_text_view);
                TextView starttime = view.findViewById(R.id.start_time);
                TextView endTime = view.findViewById(R.id.end_time);
                TextView idx = view.findViewById(R.id.itemid);

                Log.d("CONVERTX", endTime + "-" + starttime);


                FullScreenDialog newFragment = new FullScreenDialog();
                // FragmentManager fragmentManager = ctvt.getSupportFragmentManager();


                Bundle args = new Bundle();
                args.putInt("key", Integer.parseInt(idx.getText().toString()));
                args.putString("title", title.getText().toString());
                args.putString("starttime", starttime.getText().toString());
                args.putString("endtime", endTime.getText().toString());
                args.putInt("day", listpos);

                Log.d("DS", "PSN:" + Integer.toString(position));
                newFragment.setArguments(args);
                FragmentTransaction transaction = Fmgr.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
                return false;
            }
        });

        listView.setAdapter(adapter);

    }
    private SwipeRefreshLayout pullToRefresh;


    public static ArrayList<scheduleItem> TTFetch()
    {
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");

        ArrayList<scheduleItem> sch = new ArrayList<scheduleItem>();

        DBGateway database = Room.databaseBuilder(context, DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        scheduleDAO SDAO=database.getScheduleDao();
       List<ScheduleEntity> scheduleEntities = SDAO.getScheduleByDay(listpos);
        for (ScheduleEntity cn : scheduleEntities) {

            Log.d("DAS/DSL", "Printing: Task " + cn.getTask() + " Time " + cn.getStartTime() + cn.getEndTime() + " Label " + cn.getLabel());
            sch.add(new scheduleItem(cn.getTask(), Converters.date_to_t12(cn.getStartTime()),Converters.date_to_t12(cn.getEndTime()), cn.getSubjCode(), cn.getLabel(), cn.getId(), cn.getDay()));
        }
        return sch;
    }

}