package com.nirmalhk7.nirmalhk7.controllers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.model.AttendanceDAO;
import com.nirmalhk7.nirmalhk7.model.AttendanceEntity;
import com.nirmalhk7.nirmalhk7.timetable.TimetableDAO;
import com.nirmalhk7.nirmalhk7.timetable.TimetableEntity;

import java.util.List;

public class TimetableDialogController {

    private Context mContext;
    private AutoCompleteTextView mTaskName;
    private EditText mTaskCode, mStartTime,mEndTime;
    private RadioGroup mDayRadioGroup;
    public TimetableDialogController(AutoCompleteTextView taskName, EditText taskCode, EditText taskStartTime,
                                     EditText taskEndTime, RadioGroup dayrg, Context context)
    {

        mContext=context;
        mTaskName=taskName;
        mTaskCode=taskCode;
        mEndTime=taskEndTime;
        mStartTime=taskStartTime;
        mDayRadioGroup=dayrg;
    }
    public void onLongClick(Bundle bundle,ImageView trash,View rootView)
    {
        String title = bundle.getString("title");
        String label = bundle.getString("label");
        String startTime = bundle.getString("starttime");
        String endtime = bundle.getString("endtime");
        String subjcode=bundle.getString("subjcode");
        int rday = bundle.getInt("day")-1;
        Log.d(getClass().getName(),rday+" Provided by TT");
        final int dbNo = bundle.getInt("key");

        mTaskName.setText(title);
        mTaskCode.setText(subjcode);
        mStartTime.setText(startTime);
        mEndTime.setText(endtime);
        ((RadioButton)mDayRadioGroup.getChildAt(rday)).setChecked(true);


    }
    public void deleteTimetableEntry(int dbNo)
    {
        DBGateway database = DBGateway.getInstance(mContext);

        com.nirmalhk7.nirmalhk7.timetable.TimetableDAO SDAO=database.getTTDao();
        Log.d(getClass().getName(), dbNo+" DB Deleted");
        SDAO.deleteSchedule(SDAO.getScheduleById(dbNo));
    }

    public void onAddNew(View rootView,Bundle bundle)
    {
        mDayRadioGroup=rootView.findViewById(R.id.rgDay);
        ((RadioButton)mDayRadioGroup.getChildAt(bundle.getInt("day-selected")-1)).setChecked(true);
    }
    private void listenRadioGroup()
    {
        mDayRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId)
                {
                    case R.id.rbMon:
                        Log.d(getClass().getName(),"Day Selected: Monday");
                        mday= Converters.day_to_dayno("Mon");
                        break;
                    case R.id.rbTue:
                        Log.d(getClass().getName(),"Day Selected: Tuesday");
                        mday= Converters.day_to_dayno("Tue");
                        break;
                    case R.id.rbWed:
                        Log.d(getClass().getName(),"Day Selected: Wednesday");
                        mday= Converters.day_to_dayno("Wed");
                        break;
                    case R.id.rbThu:
                        Log.d(getClass().getName(),"Day Selected: Thursday");
                        mday= Converters.day_to_dayno("Thu");
                        break;
                    case R.id.rbFriday:
                        Log.d(getClass().getName(),"Day Selected: Friday");
                        mday= Converters.day_to_dayno("Fri");
                        break;
                    case R.id.rbSaturday:
                        Log.d(getClass().getName(),"Day Selected: Saturday");
                        mday= Converters.day_to_dayno("Sat");
                        break;


                }
            }
        });
    }
    private void listenAutoTextView()
    {
        String[] subject=insertSuggestions();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (mContext, R.layout.partial_suggestion, subject);
        mTaskName.setThreshold(1); //will start working from first character
        mTaskName.setAdapter(adapter);

    }
    private int mday;
    public void dialogListen()
    {
        listenRadioGroup();
        listenAutoTextView();
    }
    private String[] insertSuggestions(){
        DBGateway database = DBGateway.getInstance(mContext);

        final TimetableDAO SDAO = database.getTTDao();
        AttendanceDAO attendanceDAO=database.getATTDao();

        List<TimetableEntity> x = SDAO.getSubjects("College");

        List<AttendanceEntity> z = attendanceDAO.getSubjectNames();
        String[] subject = new String[x.size()+z.size()];

        int i = 0;

        for (TimetableEntity cn : x) {
            subject[i] = cn.getTask();
            Log.d(getClass().getName(), "Autocomplete Local "+subject[i]);
            ++i;
        }
        for (AttendanceEntity cn: z)
        {
            subject[i]=cn.getSubject();
            Log.d(getClass().getName(),"Autocomplete From Attendance: "+subject[i]);
            ++i;
        }
        return subject;
    }
    public void saveTimetable(String taskName,String taskCode,String startTime,String endTime,int day,
                              boolean editingOrNot,int dbNo){



        //  db.addSchedule(new TimetableEntity("Task 1","Label 1","Time 1"));

        DBGateway database = DBGateway.getInstance(mContext);
        TimetableDAO SDAO = database.getTTDao();

        TimetableEntity scheduleEntity;
        if (editingOrNot) {
            scheduleEntity = SDAO.getScheduleById(dbNo);




        } else {
            scheduleEntity = new TimetableEntity();
        }
        scheduleEntity.setTask(taskName);
        scheduleEntity.setSubjCode(taskCode);
        scheduleEntity.setStartTime(Converters.to_date(startTime,"hh:mm a"));
        scheduleEntity.setEndTime(Converters.to_date(endTime,"hh:mm a"));
        scheduleEntity.setDay(day);
        Log.d(getClass().getName(),"Day saved: "+day);
        if(editingOrNot)
            SDAO.updateSchedule(scheduleEntity);
        else
            SDAO.insertOnlySingleSchedule(scheduleEntity);

    }

}
