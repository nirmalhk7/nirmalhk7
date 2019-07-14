package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.convert;

import java.util.ArrayList;
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
        View rootView = inflater.inflate(R.layout.full_screen_layout, container, false);
        final Bundle bundle = this.getArguments();
        //If editing

        if (bundle != null) {
            String title = bundle.getString("title");
            String label = bundle.getString("label");
            String startTime = bundle.getString("starttime");
            String endtime = bundle.getString("endtime");
            int rday = bundle.getInt("day");
            Log.d("ddd",rday+"");
            dbNo = bundle.getInt("key");

            //Pass title,label and time value to EditText
            AutoCompleteTextView taskNameEdit = rootView.findViewById(R.id.taskName);
            taskNameEdit.setText(title);
            EditText taskLabelEdit = rootView.findViewById(R.id.taskLabel);
            taskLabelEdit.setText(label);


            EditText taskTimeStartEdit = rootView.findViewById(R.id.taskStart);
            EditText taskTimeEndEdit = rootView.findViewById(R.id.taskEnd);
            Log.d("CONVERTXX", convert.normaltorail(startTime) + ".." + convert.normaltorail(endtime));
            taskTimeStartEdit.setText(convert.normaltorail(startTime));
            taskTimeEndEdit.setText(convert.normaltorail(endtime));

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
                    DBGateway database = Room.databaseBuilder(getContext(), DBGateway.class, "mydb")
                            .allowMainThreadQueries().fallbackToDestructiveMigration()
                            .build();

                    scheduleDAO scheduleDAO = database.getScheduleDao();
                    Log.d("DAS/FSD/ID", Integer.toString(dbNo));
                    scheduleDAO.deleteSchedule(scheduleDAO.getScheduleById(dbNo));
                    dismiss();
                }
            });

        }


        // Spinner element

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



        DBGateway database = Room.databaseBuilder(getContext(), DBGateway.class, "mydb")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        scheduleDAO scheduleDAO = database.getScheduleDao();

        List<Schedule> x = scheduleDAO.getSubjects("College");
        String[] subject = new String[x.size()];
        int i = 0;

        for (Schedule cn : x) {
            subject[i] = cn.getTask();
            Log.d("ATT/FSD/", subject[i]);
            ++i;
        }
        final AppCompatAutoCompleteTextView autoTextView;
        autoTextView = (AppCompatAutoCompleteTextView) rootView.findViewById(R.id.taskName);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, subject);
        autoTextView.setThreshold(1); //will start working from first character
        autoTextView.setAdapter(adapter);


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
                dialogTimePicker(1, startTime);

            }
        });


        //Timepicker end time dialog
        final EditText endTime = rootView.findViewById(R.id.taskEnd);
        endTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogTimePicker(2, endTime);

            }
        });


        //On Click SAVE
        (rootView.findViewById(R.id.button_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("DailyScheduler", "Saving data!");

                AutoCompleteTextView taskNameEdit = getActivity().findViewById(R.id.taskName);
                EditText taskLabelEdit = getActivity().findViewById(R.id.taskLabel);
                EditText taskTimeStartEdit = getActivity().findViewById(R.id.taskStart);
                EditText taskTimeEndEdit = getActivity().findViewById(R.id.taskEnd);


                String task = taskNameEdit.getText().toString();
                String label = taskLabelEdit.getText().toString();
                String time = taskTimeStartEdit.getText().toString() + "-" + taskTimeEndEdit.getText().toString();
                //Validation
                boolean required = (task == null) || (label == null) || (time == null);
                if (required == true) {
                    Log.i("DAS/FSD", "Validation required");
                }
                Log.d("DIALOG", "Time " + time);
                Log.d("Name:", "H " + taskNameEdit.getText().toString() + taskLabelEdit.getText().toString() + taskTimeEndEdit.getText().toString());

                //  db.addSchedule(new Schedule("Task 1","Label 1","Time 1"));

                DBGateway database = Room.databaseBuilder(getContext(), DBGateway.class, "mydb")
                        .allowMainThreadQueries().fallbackToDestructiveMigration()
                        .build();
                scheduleDAO scheduleDAO = database.getScheduleDao();

                if (bundle != null) {
                    Schedule schedule = scheduleDAO.getScheduleById(dbNo);

                    schedule.setTask(task);
                    schedule.setLabel(label);
                    schedule.setStartTime(taskTimeStartEdit.getText().toString());
                    schedule.setEndTime(taskTimeEndEdit.getText().toString());
                    schedule.setDay(mday);
                    scheduleDAO.updateSchedule(schedule);

                } else {
                    Schedule schedule = new Schedule();
                    schedule.setTask(task);
                    schedule.setLabel(label);
                    schedule.setStartTime(taskTimeStartEdit.getText().toString());
                    schedule.setEndTime(taskTimeEndEdit.getText().toString());
                    schedule.setDay(mday);
                    scheduleDAO.insertOnlySingleSchedule(schedule);
                }


                dismiss();
            }
        });
        return rootView;
    }

    public void dialogTimePicker(final int whatTimeSelected, final EditText Time) {
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int Mhour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int Mminute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (selectedHour < 10) {

                    if (selectedMinute < 10) {
                        Time.setText(selectedHour + "0" + selectedMinute);
                    }
                    else{
                        Time.setText("0" + selectedHour + "" + selectedMinute);
                    }
                } else if (selectedHour >= 10) {

                    if (selectedMinute < 10) {
                        Time.setText(selectedHour + "0" + selectedMinute);
                    }
                    else {
                        Time.setText(selectedHour + "" + selectedMinute);

                    }
                }
            }
        }, Mhour, Mminute, true);//yes 12 hour time


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

}
