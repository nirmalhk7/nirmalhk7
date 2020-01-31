package com.nirmalhk7.nirmalhk7.ArrayAdapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.room.Room;

import com.nirmalhk7.nirmalhk7.Controllers.Converters;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.model.AttendanceDAO;
import com.nirmalhk7.nirmalhk7.model.AttendanceEntity;
import com.nirmalhk7.nirmalhk7.model.AttendanceListItem;
import com.nirmalhk7.nirmalhk7.model.SubjectlogDAO;
import com.nirmalhk7.nirmalhk7.model.SubjectlogEntity;

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

        // 1 when showing Attendance list
        if(mSubject ==1)
        {
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.item_attendance, parent, false);
            }

            TextView subjectName=listItemView.findViewById(R.id.subjName_subject);;
            TextView attendanceCount=listItemView.findViewById(R.id.presentabsent_subject);
            TextView id;
            float present,absent,result;
            mCurrentItem = getItem(position);

            subjectName.setText(mCurrentItem.getSubjName());
            attendanceCount.setText("Pr."+ mCurrentItem.getmPresent()+" / Ab."+ mCurrentItem.getmAbsent());
            present = mCurrentItem.getmPresent();
            absent= mCurrentItem.getmAbsent();
            result=present/(present+absent);
            result = (float) Math.round(result * 100);

            TextView percentAttendance = listItemView.findViewById(R.id.percent_subject);
            percentAttendance.setText(result+"%");

            id=listItemView.findViewById(R.id.attendance_id);
            Log.d("XXXC",""+ mCurrentItem.getmId());
            id.setText(mCurrentItem.getmId()+"");

            btnlistener(listItemView,Integer.parseInt(id.getText().toString()));

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

        SubjectlogDAO SLDAO=database.getSALDAO();
        final SubjectlogEntity sl=new SubjectlogEntity();
        TextView subjName_subj = listitemview.findViewById(R.id.subjName_subject);
        sl.setSubject(subjName_subj.getText().toString());
        ImageButton p=listitemview.findViewById(R.id.present_btn);
        ImageButton a=listitemview.findViewById(R.id.absent_btn);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(getClass().getName(),"ATTARR Click Present!");
                int pr=att.getPresent();
                att.setPresent(++pr);
                sl.setPrabca(1);
                Toast.makeText(getContext(),"Present marked for "+sl.getSubject(),Toast.LENGTH_LONG).show();
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(getClass().getName(),"ATTARR Click Absent!");
                int ab=att.getAbsent();
                att.setAbsent(++ab);
                sl.setPrabca(2);
                Toast.makeText(getContext(),"Absent marked for "+sl.getSubject(),Toast.LENGTH_LONG).show();
            }
        });
        attendanceDAO.updateSubject(att);
        sl.setDaytime(Converters.to_date(
                Converters.today_get("dd MMM yyyy hh:mm a"),
                "dd MMM yyyy hh:mm a"));


        Log.d("datex","-"+Converters.today_get("dd MMM yyyy hh:mm a"));
        SLDAO.insertLog(sl);
        attendanceDAO.updateSubject(att);

    }
}
