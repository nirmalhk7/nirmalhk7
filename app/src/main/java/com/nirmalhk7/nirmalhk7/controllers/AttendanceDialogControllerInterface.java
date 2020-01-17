package com.nirmalhk7.nirmalhk7.controllers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class AttendanceDialogControllerInterface implements DialogControllerInterface {
    private Context mContext;
    private DBGateway database;
    private Activity mActivity;

    public AttendanceDialogControllerInterface(Context context, Activity activity){
        mContext=context;
        mActivity=activity;
        database = DBGateway.getInstance(mContext);
    }

    @Override
    public void deleteEntry(int dbNo)
    {

    }

    @Override
    public String[] autocompleteSetup(){
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
    public ImageView onEditSetup(int dbNo, LinearLayout topIcons)
    {
        ImageView trash = new ImageView(mContext);
        trash.setImageResource(R.drawable.ic_trash);
        trash.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        int pxstd = mContext.getResources().getDimensionPixelSize(R.dimen.standard_dimen);
        trash.setPadding(pxstd, 0, pxstd, 0);
        return trash;
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
    public void changeTotal(View rootView, final EditText pr, final EditText ab) {

        final TextView Total = rootView.findViewById(R.id.total_fsd);
        pr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int p = Integer.parseInt(s.toString());
                    int a = Integer.parseInt(ab.getText().toString());
                    Total.setText("Total Classes: " + (p + a));
                    Log.d("ATT/FSD", "TotalTV " + Total.getText());

                } catch (NumberFormatException e) {
                    Log.e("ATT/FSD", e.getMessage());
                }

            }
        });
        ab.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int a = Integer.parseInt(s.toString());
                    int p = Integer.parseInt(pr.getText().toString());
                    Total.setText("Total Classes: " + (p + a));
                    Log.d("ATT/FSD", "TotalTV " + Total.getText());

                } catch (NumberFormatException e) {
                    Log.e("ATT/FSD", e.getMessage());
                }

            }
        });

    }

}
