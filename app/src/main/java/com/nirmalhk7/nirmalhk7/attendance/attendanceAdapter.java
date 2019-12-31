package com.nirmalhk7.nirmalhk7.attendance;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.daimajia.swipe.SwipeLayout;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.utility.Converters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class attendanceAdapter extends ArrayAdapter<attendanceListItem> {

    /**
     * Resource ID for the background color for this list of words
     */
    private int kSubject;
    private Context context;
    public attendanceAdapter(Context context, ArrayList<attendanceListItem> attendanceListItem) {
        super(context, 0, attendanceListItem);
        kSubject=0;
        this.context=context;
    }

    public attendanceAdapter(Context context, ArrayList<attendanceListItem> SubjectItem, int key) {
        super(context, 0, SubjectItem);
        kSubject = key;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if(kSubject==1)
        {
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.attendance_list_item, parent, false);
            }

            final attendanceListItem currentWord = getItem(position);

            TextView subjName_subj = listItemView.findViewById(R.id.subjName_subject);
            subjName_subj.setText(currentWord.getSubjName());

            TextView presentCount = listItemView.findViewById(R.id.presentabsent_subject);

            presentCount.setText("Present "+currentWord.getmPresent()+" / Absent "+currentWord.getmAbsent());
            float present=currentWord.getmPresent();
            float absent=currentWord.getmAbsent();
            float result=present/(present+absent);
            result = (float) Math.round(result * 100);

            TextView percent = listItemView.findViewById(R.id.percent_subject);

            percent.setText("Percent: "+result+"%");

            final TextView id=listItemView.findViewById(R.id.attendance_id);
            Log.d("XXXC",""+currentWord.getmId());
            id.setText(currentWord.getmId()+"");

            btnlistener(listItemView,Integer.parseInt(id.getText().toString()));

            SwipeLayout swipeLayout =  (SwipeLayout)listItemView.findViewById(R.id.swipeswipe);

//set show mode.
            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

//add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
            swipeLayout.addDrag(SwipeLayout.DragEdge.Right, listItemView.findViewById(R.id.bottom_wrapper));
            final ImageButton deleteb=listItemView.findViewById(R.id.trash_swipe);
            final ImageButton editb=listItemView.findViewById(R.id.edit_swipe);
            swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onStartOpen(SwipeLayout layout) {

                }

                @Override
                public void onOpen(SwipeLayout layout) {

                    DBGateway database = Room.databaseBuilder(context, DBGateway.class, "finalDB")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();

                    final AttendanceDAO attendanceDAO = database.getATTDao();
                    deleteb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AttendanceEntity x=attendanceDAO.getSubjectbyId(currentWord.getmId());
                            attendanceDAO.deleteSchedule(x);
                        }
                    });
                    editb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("ATT/ATA","Clicked");
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            attendanceFSD newFragment = new attendanceFSD();
                            Bundle bundle=new Bundle();
                            bundle.putInt("key",currentWord.getmId());
                            bundle.putInt("present",currentWord.getmPresent());
                            bundle.putInt("absent",currentWord.getmAbsent());
                            bundle.putString("subject",currentWord.getSubjName());
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
        else if(kSubject==2){
            //For Subject Attendance Log
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.attendance_subject_log_item, parent, false);
            }
            attendanceListItem currentWord = getItem(position);
            TextView date=listItemView.findViewById(R.id.subjName_calendar);
            date.setText(currentWord.getDateAdded());

            TextView dt= listItemView.findViewById(R.id.dt_calendar);
            dt.setText(currentWord.getDayTime());

            if(currentWord.getmPRABCA()==1)
            {
                TextView pacp= listItemView.findViewById(R.id.pacp_calendar);
                pacp.setText("Present");
                pacp.setTextColor(Color.GREEN);
            }
            else if(currentWord.getmPRABCA()==2)
            {
                TextView pacp= listItemView.findViewById(R.id.pacp_calendar);
                pacp.setText("ABSENT");
                pacp.setTextColor(Color.RED);
            }
            else if(currentWord.getmPRABCA()==3)
            {
                TextView pacp= listItemView.findViewById(R.id.pacp_calendar);
                pacp.setText("CANCELLED");
                pacp.setTextColor(Color.YELLOW);
            }
            else if(currentWord.getmPRABCA()==-1)
            {
                TextView pacp=listItemView.findViewById(R.id.pacp_calendar);
                pacp.setText("RESET");
                pacp.setTextColor(Color.BLUE);
            }



        }
        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }

    private void btnlistener(final View listitemview,final int id)
    {
        ImageButton p=listitemview.findViewById(R.id.present_btn);
        ImageButton a=listitemview.findViewById(R.id.absent_btn);
        ImageButton c=listitemview.findViewById(R.id.cancel_btn);
        final DBGateway database = Room.databaseBuilder(context, DBGateway.class, "finalDB")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        final AttendanceDAO attendanceDAO = database.getATTDao();

        Log.d("ATT/ALS","ID "+id);
        //final AttendanceEntity att=AttendanceDAO.getSubjectbyId(id);
        final AttendanceEntity att=attendanceDAO.getSubjectbyId(id);


        Date d=Calendar.getInstance().getTime();

        //sl.setDateAdded(d.getYear()+"/"+d.getMonth()+"/"+d.getDate());

        Log.d("ATT/ALS","Recieved "+att.getSubject()+" "+att.getPresent()+" "+att.getAbsent());

        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pr=att.getPresent();
                att.setPresent(++pr);
                attendanceDAO.updateSubject(att);

                SubjectlogDAO SLDAO=database.getSALDAO();
                final SubjectlogEntity sl=new SubjectlogEntity();
                TextView subjName_subj = listitemview.findViewById(R.id.subjName_subject);
                sl.setSubject(subjName_subj.getText().toString());
                sl.setPrabca(1);

                DateFormat dtf = new SimpleDateFormat("dd MMM yyyy EEE hh:mm a");
                Date curdate=Converters.to_date(dtf.format(Calendar.getInstance().getTime()),"dd MMMM yyyy hh:mm a");
                sl.setDaytime(curdate);


                Log.d("datex",dtf.format(Calendar.getInstance().getTime())+"//"+curdate.getTime());
                SLDAO.insertLog(sl);
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ab=att.getAbsent();
                att.setAbsent(++ab);
                attendanceDAO.updateSubject(att);

                SubjectlogDAO SLDAO=database.getSALDAO();
                final SubjectlogEntity sl=new SubjectlogEntity();
                TextView subjName_subj = listitemview.findViewById(R.id.subjName_subject);
                sl.setSubject(subjName_subj.getText().toString());

                DateFormat dtf = new SimpleDateFormat("dd MMM yyyy EEE hh:mm a");
                Date curdate=Converters.to_date(dtf.format(Calendar.getInstance().getTime()),"dd MMMM yyyy hh:mm a");
                sl.setDaytime(curdate);


                Log.d("datex",dtf.format(Calendar.getInstance().getTime())+"//"+curdate.getTime());

                SLDAO.insertLog(sl);
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ca=att.getCancelled();
                att.setCancelled(++ca);
                attendanceDAO.updateSubject(att);

                SubjectlogDAO SLDAO=database.getSALDAO();
                final SubjectlogEntity sl=new SubjectlogEntity();
                TextView subjName_subj = listitemview.findViewById(R.id.subjName_subject);
                sl.setSubject(subjName_subj.getText().toString());
                sl.setPrabca(3);

                DateFormat dtf = new SimpleDateFormat("dd MMM yyyy EEE hh:mm a");
                Date curdate=Converters.to_date(dtf.format(Calendar.getInstance().getTime()),"dd MMMM yyyy hh:mm a");
                sl.setDaytime(curdate);


                Log.d("datex",dtf.format(Calendar.getInstance().getTime())+"//"+curdate.getTime());


                SLDAO.insertLog(sl);
            }
        });



    }
}
