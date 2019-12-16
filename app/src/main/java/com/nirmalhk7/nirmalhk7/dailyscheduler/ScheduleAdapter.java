package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.attendance.attendanceDAO;
import com.nirmalhk7.nirmalhk7.attendance.attendanceEntity;

import java.util.ArrayList;

public class ScheduleAdapter extends ArrayAdapter<scheduleItem> {

    /** Resource ID for the background color for this list of words */
    private int mColorResourceId;


    public ScheduleAdapter(Context context, ArrayList<scheduleItem> words) {
        super(context, 0, words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.dailyschedule_list_item, parent, false);
        }

        // Get the {@link scheduleItem} object located at this position in the list
        scheduleItem currentWord = getItem(position);

        // Find the TextView in the dailyschedule_list_itemdule_list_item.xml layout with the ID miwok_text_view.
        TextView miwokTextView = listItemView.findViewById(R.id.miwok_text_view);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.
        miwokTextView.setText(currentWord.getScheduleTitle());

        // Find the TextView in the dailyschedule_list_item.xml_list_item.xml layout with the ID default_text_view.
        if(!currentWord.getScheduleLabel().equals("College")){    TextView defaultTextView = listItemView.findViewById(R.id.default_text_view);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        defaultTextView.setText(currentWord.getScheduleLabel());
    }

        TextView startTime = listItemView.findViewById(R.id.start_time);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        startTime.setText(currentWord.getmStartTime());

        TextView endTime= listItemView.findViewById(R.id.end_time);
        endTime.setText(currentWord.getmEndTime());

        TextView subjcode=listItemView.findViewById(R.id.fsd_subjabbr);
        subjcode.setText(currentWord.getmSubjCode());


        TextView id=listItemView.findViewById(R.id.itemid);
        id.setText(String.valueOf(currentWord.getScheduleId()));

        DBGateway database= Room.databaseBuilder(getContext(),DBGateway.class,"finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        attendanceDAO ATTDAO=database.getAttendanceDao();
        attendanceEntity attent=ATTDAO.getSubjectbyName(currentWord.getScheduleTitle());
        if(attent!=null)
        {
            TextView prab=listItemView.findViewById(R.id.mandatory);
            int attfab=(attent.getPresent()-1)/(attent.getPresent()+attent.getAbsent());
            prab.setText(attfab+"");
        }

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}