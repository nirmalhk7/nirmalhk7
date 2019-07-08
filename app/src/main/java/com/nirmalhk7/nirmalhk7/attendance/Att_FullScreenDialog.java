package com.nirmalhk7.nirmalhk7.attendance;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.dailyscheduler.Schedule;
import com.nirmalhk7.nirmalhk7.dailyscheduler.scheduleDAO;
import com.nirmalhk7.nirmalhk7.dailyscheduler.scheduleDatabase;
import com.nirmalhk7.nirmalhk7.dailyscheduler.scheduleItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

public class Att_FullScreenDialog extends DialogFragment {
    public int key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.attendance_fullscreen, container, false);
        Bundle bundle = this.getArguments();
        //If editing
        if (bundle != null) {


        }


        scheduleDatabase database1 = Room.databaseBuilder(getContext(), scheduleDatabase.class, "mydb")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        scheduleDAO scheduleDAO = database1.getScheduleDao();

        attendanceDatabase database2 = Room.databaseBuilder(getContext(), attendanceDatabase.class, "mydbx")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        final attendanceDAO attendanceDAO = database2.getAttendanceDAO();


        List<Schedule> schedules=scheduleDAO.getSubjects("College");
        String[] subject=new String[schedules.size()];
        int i=0;
        for (Schedule cn : schedules) {
            subject[0]=cn.getTask();
            ++i;
            Log.d("ATT/FSD/ScheduleScan","Printing: Task "+subject[i]);

        }


        final AppCompatAutoCompleteTextView autoTextView;
        autoTextView = (AppCompatAutoCompleteTextView) rootView.findViewById(R.id.attendance_task);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, subject);
        autoTextView.setThreshold(1); //will start working from first character
        autoTextView.setAdapter(adapter);

        final EditText Present=rootView.findViewById(R.id.present_fsd);
        final EditText Absent=rootView.findViewById(R.id.absent_fsd);




        //Close button action
        (rootView.findViewById(R.id.button_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });



        //On Click SAVE
        Button b=rootView.findViewById(R.id.ATT_button_save);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String subj=autoTextView.getText().toString();
                try{
                    int present=Integer.valueOf(Present.getText().toString());
                    int absent=Integer.valueOf(Absent.getText().toString());
                    attendanceEntity x=new attendanceEntity();
                    x.setSubject(subj);
                    x.setPresent(present);
                    x.setAbsent(absent);
                    attendanceDAO.insertOnlySingleSubject(x);
                    Log.d("ATT/FSD", "Saving data!"+x.getSubject()+x.getPresent()+x.getAbsent());
                    dismiss();
                }
                catch(NumberFormatException ex){ // handle your exception
                    Log.d("ATT/FSD","java.lang.NumberFormatException");
                }





            }
        });
        return rootView;
    }

    public void dialogTimePicker(int whatTimeSelected, final EditText Time) {
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int Mhour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int Mminute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Time.setText(selectedHour + ":" + selectedMinute);
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
