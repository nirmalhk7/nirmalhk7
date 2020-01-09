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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.controllers.TimetableController;
import com.nirmalhk7.nirmalhk7.controllers.Converters;

import java.util.ArrayList;
import java.util.List;

public class TimetableLoopingPagerAdapter extends LoopingPagerAdapter<Integer> {
    private static FragmentManager Fmgr;
    public static TimetableArrayAdapter ttadapter;
    private int mListPosition;
    TimetableLoopingPagerAdapter(Context context, ArrayList<Integer> itemList, boolean isInfinite, FragmentManager fmgr) {
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
        SwipeRefreshLayout pullToRefresh=convertView.findViewById(R.id.pullToRefresh);
        description.setText(Converters.dayno_to_day(listPosition));
        TimetableController ttControler=new TimetableController(convertView,context,listPosition);
        List<com.nirmalhk7.nirmalhk7.timetable.TimetableEntity> timetableEntities= ttControler.TTFetch();
        mListPosition=listPosition;

        ttadapter=new TimetableArrayAdapter(context,timetableEntities);
        ttadapter.setNotifyOnChange(true);
        final ListView listView=convertView.findViewById(R.id.list_item_timetable);

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
                args.putBoolean("editing",true);
                args.putInt("day", mListPosition);

                Log.d("DS", "Opening Dialog with DayNo:"+(mListPosition));
                newFragment.setArguments(args);
                FragmentTransaction transaction = Fmgr.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
                return false;
            }
        });

        listView.setAdapter(ttadapter);
        ttControler.swipeToRefresh(pullToRefresh,ttControler,ttadapter);


    }

    public int getmListPosition() {
        return mListPosition;
    }





}