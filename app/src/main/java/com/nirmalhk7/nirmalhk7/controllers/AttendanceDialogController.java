package com.nirmalhk7.nirmalhk7.controllers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.room.Room;

import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.model.AttendanceDAO;
import com.nirmalhk7.nirmalhk7.model.AttendanceEntity;
import com.nirmalhk7.nirmalhk7.model.SubjectlogDAO;
import com.nirmalhk7.nirmalhk7.model.SubjectlogEntity;
import com.nirmalhk7.nirmalhk7.timetable.TimetableDAO;
import com.nirmalhk7.nirmalhk7.timetable.TimetableEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AttendanceDialogController implements DialogController {
    private Context mContext;
    private DBGateway database;
    private Activity mActivity;
    public AttendanceDialogController(Context context,Activity activity){
        mContext=context;
        mActivity=activity;
        database = Room.databaseBuilder(mContext, DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();
    }
    public String[] getSubjectsList()
    {
        TimetableDAO timetableDAO=database.getTTDao();
        List<TimetableEntity> timetableEntityList=timetableDAO.getUnqiueSubjects("College");
        String[] subject = new String[timetableEntityList.size()];
        Log.d("ATT/FSD", "." + timetableEntityList.size());
        int i = 0;
        for (TimetableEntity cn : timetableEntityList) {
            subject[i] = cn.getTask();
            Log.d("ATT/FSD/", subject[i]);
            ++i;
        }
        return subject;
    }

    @Override
    public void autocompleteSetup(String[] subject,AutoCompleteTextView autoTextView){
        getSubjectsList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (mContext, R.layout.partial_suggestion, subject);
        autoTextView.setThreshold(1); //will start working from first character
        autoTextView.setAdapter(adapter);
    }

    @Override
    public void onEditSetup(int dbNo, LinearLayout topIcons)
    {
        ImageView trash = new ImageView(mContext);
        trash.setImageResource(R.drawable.ic_trash);
        trash.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        int pxstd = mContext.getResources().getDimensionPixelSize(R.dimen.standard_dimen);
        trash.setPadding(pxstd, 0, pxstd, 0);

    }
    public void onClickSave(Bundle bundle, int present, int absent, String subj){
        AttendanceEntity x=new AttendanceEntity();
        AttendanceDAO attendanceDAO=database.getATTDao();
        if(bundle==null)
        {
            attendanceDAO.insertOnlySingleSubject(x);
        }
        //Editing Attendance Entry
        else
        {
            x = attendanceDAO.getSubjectbyId(bundle.getInt("key"));
            attendanceDAO.updateSubject(x);


            if(present!=bundle.getInt("present")||absent!=bundle.getInt("absent"))
            {
                updateSubjectLog(subj);
            }

            Toast.makeText(mActivity, "Refresh to Update",
                    Toast.LENGTH_LONG).show();

        }
        Log.d("ATT/FSD", "Editing data!" + x.getSubject() + x.getPresent() + x.getAbsent());
        x.setSubject(subj);
        x.setPresent(present);
        x.setAbsent(absent);
    }
    public void updateSubjectLog(String subj)
    {
        SubjectlogDAO SLDAO=database.getSALDAO();
        SubjectlogEntity sle=new SubjectlogEntity();
        sle.setPrabca(-1);
        DateFormat dtf = new SimpleDateFormat("dd MMM yyyy EEE hh:mm a");
        Date curdate= Converters.to_date(dtf.format(Calendar.getInstance().getTime()),"dd MMMM yyyy hh:mm a");
        sle.setDaytime(curdate);
        sle.setSubject(subj);
        SLDAO.insertLog(sle);
    }
}
