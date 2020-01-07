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
import com.nirmalhk7.nirmalhk7.model.AttendanceDAO;
import com.nirmalhk7.nirmalhk7.model.AttendanceEntity;
import com.nirmalhk7.nirmalhk7.model.AttendanceListItem;
import com.nirmalhk7.nirmalhk7.model.SubjectlogDAO;
import com.nirmalhk7.nirmalhk7.model.SubjectlogEntity;
import com.nirmalhk7.nirmalhk7.util.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AttendanceArrayAdapter extends ArrayAdapter<AttendanceListItem> {
    
    private int mSubject;
    private Context mContext;
    private AttendanceListItem mCurrentItem;

    public AttendanceArrayAdapter(Context context, ArrayList<AttendanceListItem> attendanceListItem) {
        super(context, 0, attendanceListItem);
        mSubject =0;
        this.mContext=context;
    }

    public AttendanceArrayAdapter(Context context, ArrayList<AttendanceListItem> SubjectItem, int key) {
        super(context, 0, SubjectItem);
        mSubject = key;
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;


        if(mSubject ==1)
        {
            TextView subjectName;
            TextView attendanceCount;
            TextView id;
            float present,absent,result;

            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.item_attendance, parent, false);
            }


            mCurrentItem = getItem(position);

            subjectName = listItemView.findViewById(R.id.subjName_subject);
            subjectName.setText(mCurrentItem.getSubjName());

            attendanceCount = listItemView.findViewById(R.id.presentabsent_subject);

            attendanceCount.setText("Present "+ mCurrentItem.getmPresent()+" / Absent "+ mCurrentItem.getmAbsent());
            present = mCurrentItem.getmPresent();
            absent= mCurrentItem.getmAbsent();
            result=present/(present+absent);
            result = (float) Math.round(result * 100);

            TextView percentAttendance = listItemView.findViewById(R.id.percent_subject);
            percentAttendance.setText("Percent: "+result+"%");

            id=listItemView.findViewById(R.id.attendance_id);
            Log.d("XXXC",""+ mCurrentItem.getmId());
            id.setText(mCurrentItem.getmId()+"");

            btnlistener(listItemView,Integer.parseInt(id.getText().toString()));

            SwipeLayout swipeLayout = listItemView.findViewById(R.id.swipeswipe);
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

                    DBGateway database = Room.databaseBuilder(mContext, DBGateway.class, "finalDB")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();

                    final AttendanceDAO attendanceDAO = database.getATTDao();
                    deleteb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AttendanceEntity x=attendanceDAO.getSubjectbyId(mCurrentItem.getmId());
                            attendanceDAO.deleteSchedule(x);
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
        else if(mSubject ==2){
            //For Subject Attendance Log
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.item_subject_log, parent, false);
            }
            mCurrentItem = getItem(position);
            TextView date=listItemView.findViewById(R.id.subjName_calendar);
            date.setText(mCurrentItem.getDateAdded());

            TextView dt= listItemView.findViewById(R.id.dt_calendar);
            dt.setText(mCurrentItem.getDayTime());

            if(mCurrentItem.getmPRABCA()==1)
            {
                TextView pacp= listItemView.findViewById(R.id.pacp_calendar);
                pacp.setText("Present");
                pacp.setTextColor(Color.GREEN);
            }
            else if(mCurrentItem.getmPRABCA()==2)
            {
                TextView pacp= listItemView.findViewById(R.id.pacp_calendar);
                pacp.setText("ABSENT");
                pacp.setTextColor(Color.RED);
            }
            else if(mCurrentItem.getmPRABCA()==3)
            {
                TextView pacp= listItemView.findViewById(R.id.pacp_calendar);
                pacp.setText("CANCELLED");
                pacp.setTextColor(Color.YELLOW);
            }
            else if(mCurrentItem.getmPRABCA()==-1)
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
        final DBGateway database = Room.databaseBuilder(mContext, DBGateway.class, "finalDB")
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
                Date curdate= converter.to_date(dtf.format(Calendar.getInstance().getTime()),"dd MMMM yyyy hh:mm a");
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
                Date curdate= converter.to_date(dtf.format(Calendar.getInstance().getTime()),"dd MMMM yyyy hh:mm a");
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
                Date curdate= converter.to_date(dtf.format(Calendar.getInstance().getTime()),"dd MMMM yyyy hh:mm a");
                sl.setDaytime(curdate);


                Log.d("datex",dtf.format(Calendar.getInstance().getTime())+"//"+curdate.getTime());


                SLDAO.insertLog(sl);
            }
        });



    }
}
