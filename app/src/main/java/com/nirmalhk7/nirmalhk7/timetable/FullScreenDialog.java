package com.nirmalhk7.nirmalhk7.timetable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import androidx.room.Room;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.nirmalhk7.nirmalhk7.Converters;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.attendance.attendanceDAO;
import com.nirmalhk7.nirmalhk7.attendance.attendanceEntity;
import com.nirmalhk7.nirmalhk7.timetable.ScheduleEntity;
import com.nirmalhk7.nirmalhk7.timetable.scheduleDAO;

import java.util.Calendar;
import java.util.List;

public class FullScreenDialog extends DialogFragment {
    public int key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private int stHr, stM, endH, endM;
    private int mday;
    int dbNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.dailyschedule_fullscreenlayout, container, false);
        final Bundle bundle = this.getArguments();
        //If editing
        if (bundle != null) {

            //Toolbar toolbar=getActivity().findViewById(R.id.toolbar);
            //toolbar.setTitle("Edit Task");
            String title = bundle.getString("title");
            String label = bundle.getString("label");
            String startTime = bundle.getString("starttime");
            String endtime = bundle.getString("endtime");
            String subjcode=bundle.getString("subjcode");
            int rday = bundle.getInt("day");
            Log.d("ddd",rday+"");
            dbNo = bundle.getInt("key");

            //Pass title,label and time value to EditText
            AutoCompleteTextView taskNameEdit = rootView.findViewById(R.id.taskName);
            taskNameEdit.setText(title);
            EditText taskLabelEdit = rootView.findViewById(R.id.taskLabel);
            EditText subjCode=rootView.findViewById(R.id.subjCode);
            subjCode.setText(subjcode);

            EditText taskTimeStartEdit = rootView.findViewById(R.id.taskStart);
            EditText taskTimeEndEdit = rootView.findViewById(R.id.taskEnd);
            taskTimeStartEdit.setText(startTime);
            taskTimeEndEdit.setText(endtime);

            RadioGroup dayrg=rootView.findViewById(R.id.rgDay);
            ((RadioButton)dayrg.getChildAt(rday)).setChecked(true);

            //trash is the trashbox for deleting;
            ImageView trash = new ImageView(getContext());
            trash.setImageResource(R.drawable.ic_trash);
            trash.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            int pxstd = getContext().getResources().getDimensionPixelSize(R.dimen.standard_dimen);
            trash.setPadding(pxstd, 0, pxstd, 0);
            LinearLayout topIcons = rootView.findViewById(R.id.scheduleDialog);
            topIcons.addView(trash);
            trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("DAS/FullDialog", "Delete Button");
                    DBGateway database = Room.databaseBuilder(getContext(), DBGateway.class, "finalDB")
                            .allowMainThreadQueries().fallbackToDestructiveMigration()
                            .build();

                    scheduleDAO SDAO=database.getScheduleDao();
                    Log.d("DAS/FSD/ID", Integer.toString(dbNo));
                    SDAO.deleteSchedule(SDAO.getScheduleById(dbNo));
                    dismiss();
                }
            });

        }


        if(bundle==null)
        {
            RadioButton Monday=rootView.findViewById(R.id.rbMon);
            Monday.setChecked(true);
            EditText starttime=rootView.findViewById(R.id.taskStart);
            EditText endtime=rootView.findViewById(R.id.taskEnd);


        }

        RadioGroup day=rootView.findViewById(R.id.rgDay);

        day.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId)
                {
                    case R.id.rbMon:
                        Log.d("DAS/FSD","Day Selected: Monday");
                        mday=0;
                        break;
                    case R.id.rbTue:
                        Log.d("DAS/FSD","Day Selected: Tuesday");
                        mday=1;
                        break;
                    case R.id.rbWed:
                        Log.d("DAS/FSD","Day Selected: Wednesday");
                        mday=2;
                        break;
                    case R.id.rbThu:
                        Log.d("DAS/FSD","Day Selected: Thursday");
                        mday=3;
                        break;
                    case R.id.rbFriday:
                        Log.d("DAS/FSD","Day Selected: Friday");
                        mday=4;
                        break;
                    case R.id.rbSaturday:
                        Log.d("DAS/FSD","Day Selected: Saturday");
                        mday=5;
                        break;
                    case R.id.rbSunday:
                        Log.d("DAS/FSD","Day Selected: Sunday");
                        mday=6;
                        break;


                }
            }
        });



        DBGateway database = Room.databaseBuilder(getContext(), DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        final scheduleDAO SDAO = database.getScheduleDao();
        attendanceDAO attendanceDAO=database.getAttendanceDao();

        List<ScheduleEntity> x = SDAO.getSubjects("College");

        List<attendanceEntity> z = attendanceDAO.getSubjectNames();
        String[] subject = new String[x.size()+z.size()];
        int i = 0;

        for (ScheduleEntity cn : x) {
            subject[i] = cn.getTask();
            Log.d("ATT/FSD/", "Local "+subject[i]);
            ++i;
        }
        for (attendanceEntity cn: z)
        {
            subject[i]=cn.getSubject();
            Log.d("ATT/FSD/","From Attendance: "+subject[i]);
            ++i;
        }
        final AppCompatAutoCompleteTextView autoTextView;
        autoTextView = rootView.findViewById(R.id.taskName);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, subject);
        autoTextView.setThreshold(1); //will start working from first character
        autoTextView.setAdapter(adapter);

        if(x.size()==100)
        {

            autoTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    ScheduleEntity sc=SDAO.getScheduleDetails(s.toString());
                    EditText taskLabelEdit = rootView.findViewById(R.id.taskLabel);
                    taskLabelEdit.setText(sc.getLabel());
                    AutoCompleteTextView SubjCode=rootView.findViewById(R.id.subjCode);

                    SubjCode.setText(sc.getSubjCode());
                }
            });
        }

        //Close button action
        (rootView.findViewById(R.id.button_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //Timepicker start dialog onclick
        final EditText startTime = rootView.findViewById(R.id.taskStart);
        startTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogTimePicker(rootView,1, startTime);

            }
        });

        //Timepicker end time dialog
        final EditText endTime = rootView.findViewById(R.id.taskEnd);
        endTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogTimePicker(rootView,2, endTime);

            }
        });


        //On Click SAVE
        (rootView.findViewById(R.id.button_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("DailyScheduler", "Saving data!");

                AutoCompleteTextView taskNameEdit = getActivity().findViewById(R.id.taskName);
                EditText taskLabelEdit = getActivity().findViewById(R.id.taskLabel);
                AutoCompleteTextView SubjCode=getActivity().findViewById(R.id.subjCode);
                EditText taskTimeStartEdit = getActivity().findViewById(R.id.taskStart);
                EditText taskTimeEndEdit = getActivity().findViewById(R.id.taskEnd);


                //Validation

                Log.d("Name:", "H " + taskNameEdit.getText().toString() + taskLabelEdit.getText().toString() + taskTimeEndEdit.getText().toString());

                //  db.addSchedule(new ScheduleEntity("Task 1","Label 1","Time 1"));
                if(Validation(rootView))
                {
                    DBGateway database = Room.databaseBuilder(getContext(), DBGateway.class, "finalDB")
                            .allowMainThreadQueries().fallbackToDestructiveMigration()
                            .build();
                    scheduleDAO SDAO = database.getScheduleDao();

                    if (bundle != null) {
                        ScheduleEntity scheduleEntity = SDAO.getScheduleById(dbNo);

                        scheduleEntity.setTask(taskNameEdit.getText().toString());
                        scheduleEntity.setLabel(taskLabelEdit.getText().toString());
                        scheduleEntity.setSubjCode(SubjCode.getText().toString());
                        scheduleEntity.setStartTime(Converters.t12_to_date(taskTimeStartEdit.getText().toString()));
                        scheduleEntity.setEndTime(Converters.t12_to_date(taskTimeEndEdit.getText().toString()));
                        scheduleEntity.setDay(mday);
                        SDAO.updateSchedule(scheduleEntity);

                    } else {
                        ScheduleEntity scheduleEntity = new ScheduleEntity();
                        scheduleEntity.setTask(taskNameEdit.getText().toString());
                        scheduleEntity.setLabel(taskLabelEdit.getText().toString());
                        scheduleEntity.setSubjCode(SubjCode.getText().toString());
                        scheduleEntity.setStartTime(Converters.t12_to_date(taskTimeStartEdit.getText().toString()));
                        scheduleEntity.setEndTime(Converters.t12_to_date(taskTimeEndEdit.getText().toString()));
                        scheduleEntity.setDay(mday);
                        SDAO.insertOnlySingleSchedule(scheduleEntity);
                    }


                    adapter.notifyDataSetChanged();
                    dismiss();
                }

            }
        });
        return rootView;
    }
    private EditText endtime;
    public void dialogTimePicker(final View rv,final int whatTimeSelected, final EditText TimeEdt) {
        //  Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int Mhour = 0;
        int Mminute = 0;
        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String time="";
                if (selectedHour < 10) {
                    time="0";
                }
                time+=selectedHour+":";
                if(selectedMinute<10)
                {
                    time+="0";
                }
                time+=selectedMinute;
                TimeEdt.setText(Converters.t24_to_t12(time));

            }
        }, Mhour, Mminute, false);//yes 12 hour time


        if (whatTimeSelected == 2) {
            mTimePicker.setTitle("End Time");
        } else if (whatTimeSelected == 1) {
            mTimePicker.setTitle("Start Time");
        }
        mTimePicker.show();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public boolean Validation(View rootview){
        AppCompatAutoCompleteTextView EtaskName=rootview.findViewById(R.id.taskName);
        EditText EtaskStartTime=rootview.findViewById(R.id.taskStart);
        EditText EtaskEndTime=rootview.findViewById(R.id.taskEnd);
        EditText EtaskLabel=rootview.findViewById(R.id.taskLabel);
        String taskName=EtaskName.getText().toString();
        String taskLabel=EtaskLabel.getText().toString();
        String taskStartTime=EtaskStartTime.getText().toString();
        String taskEndTime=EtaskEndTime.getText().toString();
        if(taskName.isEmpty()||taskEndTime.isEmpty()||taskLabel.isEmpty()||taskStartTime.isEmpty())
        {
            if(taskName.isEmpty())
            {
                Log.d("DAS/FSD","Validation taskName");
                EtaskName.setError("Required");
            }
            if(taskStartTime.isEmpty())
            {
                Log.d("DAS/FSD","Validation taskName");
                EtaskStartTime.setError("Required");

            }
            if(taskEndTime.isEmpty())
            {
                Log.d("DAS/FSD","Validation taskName");
                EtaskEndTime.setError("Required");

            }
            if(taskLabel.isEmpty())
            {
                Log.d("DAS/FSD","Validation taskName");
                EtaskLabel.setError("Required");

            }
            return false;
        }

        return true;

    }

}
