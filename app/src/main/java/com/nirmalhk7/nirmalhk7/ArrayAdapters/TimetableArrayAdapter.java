package com.nirmalhk7.nirmalhk7.ArrayAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.Controllers.Converters;
import com.nirmalhk7.nirmalhk7.model.AttendanceEntity;
import com.nirmalhk7.nirmalhk7.model.TimetableEntity;

import java.util.List;

public class TimetableArrayAdapter extends ArrayAdapter<TimetableEntity> {

    /** Resource ID for the background color for this list of words */


    public TimetableArrayAdapter(Context context, List<TimetableEntity> words) {
        super(context, 0, words);
    }

    private double attendanceMinimum;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_timetable, parent, false);
        }

        // Get the {@link TimetableListItem} object located at this position in the list
        TimetableEntity currentWord = getItem(position);

        // Find the TextView in the dailyschedule_list_itemdule_list_item.xml layout with the ID miwok_text_view.
        TextView miwokTextView = listItemView.findViewById(R.id.miwok_text_view);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.
        try{
            miwokTextView.setText(currentWord.getTask());

        }catch (NullPointerException npe)
        {
            Log.e(getClass().getName(),npe.getMessage());
        }

        // Find the TextView in the item_timetable.xml_list_item.xml layout with the ID default_text_view.

        TextView startTime = listItemView.findViewById(R.id.start_time);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        startTime.setText(Converters.date_to(currentWord.getStartTime(),"hh:mm a"));

        TextView endTime= listItemView.findViewById(R.id.end_time);
        endTime.setText(Converters.date_to(currentWord.getEndTime(),"hh:mm a"));

        TextView subjcode=listItemView.findViewById(R.id.fsd_subjabbr);
        subjcode.setText(currentWord.getSubjCode());

        TextView mandatory=listItemView.findViewById(R.id.mandatory);
        if(currentWord.getDay()==Converters.day_to_dayno(Converters.today_get("EEE")))
        {
            DBGateway dbGateway=DBGateway.getInstance(getContext());
            try{
                AttendanceEntity attendanceEntity=dbGateway.getATTDao().getSubjectbyName(currentWord.getTask());
                int present=attendanceEntity.getPresent();
                int absent=attendanceEntity.getAbsent();
                attendanceMinimum=0.80;
                Log.d(getClass().getName(),"Calculating for "+attendanceEntity.getSubject()+" P "+attendanceEntity.getPresent()+" A "+attendanceEntity.getAbsent());
                float ifskipped=(float)present/(present+absent+1);
                if(ifskipped>attendanceMinimum)
                {
                    float skipPossibility=1;
                    int canbunk;
                    for(canbunk=0;skipPossibility>attendanceMinimum;++canbunk)
                    {
                        skipPossibility=(float)present/(present+absent+canbunk);
                    }
                    Log.d("CLASSNOTFOUNDX",currentWord.getTask()+" Possibility "+skipPossibility+" canBunk "+canbunk);
                    if(canbunk>1)
                    {
                        mandatory.setText("Can skip "+canbunk+" classes");
                    }
                }
                else
                {
//                    float upAttendance=ifskipped;
//                    int mustAttend;
//                    for(mustAttend=0;upAttendance<attendanceMinimum;++mustAttend)
//                    {
//                        upAttendance=(present+mustAttend)/(present+mustAttend+absent);
//
//                    }
                    mandatory.setText("Must attend this class");
                }
            }
            catch (NullPointerException e)
            {
                Log.d(getClass().getName()+" CLASSNOTFOUND ",currentWord.getTask()+" -"+e.getMessage());
            }

        }
        TextView id=listItemView.findViewById(R.id.itemid);
        id.setText(String.valueOf(currentWord.getId()));

//        DBGateway database= Room.databaseBuilder(getContext(),DBGateway.class,"finalDB")
//                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
//        AttendanceDAO ATTDAO=database.getAttendanceDao();
//        AttendanceEntity attent=ATTDAO.getSubjectbyName(currentWord.getScheduleTitle());
//        if(attent!=null)
//        {
//            TextView prab=listItemView.findViewById(R.id.mandatory);
//            int attfab=(attent.getPresent()-1)/(attent.getPresent()+attent.getAbsent());
//            prab.setText(attfab+"");
//        }

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}