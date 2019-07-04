package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toolbar;

import com.nirmalhk7.nirmalhk7.R;

import java.sql.Time;
import java.util.Calendar;

public class FullScreenDialog extends DialogFragment{
    public int key;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private int stHr,stM,endH,endM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.full_screen_layout, container, false);
        Bundle bundle=this.getArguments();
        //If editing
        if(bundle!=null)
        {
            String title=bundle.getString("title");
            String label=bundle.getString("label");
            String time=bundle.getString("time");


            //Pass title,label and time value to EditText
            EditText taskNameEdit=rootView.findViewById(R.id.taskName);
            taskNameEdit.setText(title);
            EditText taskLabelEdit=rootView.findViewById(R.id.taskLabel);
            taskLabelEdit.setText(label);

            //trash is the trashbox for deleting;
            ImageView trash=new ImageView(getContext());
            trash.setImageResource(R.drawable.ic_trash);
            trash.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            int pxstd=getContext().getResources().getDimensionPixelSize(R.dimen.standard_dimen);
            trash.setPadding(pxstd,0,pxstd,0);
            LinearLayout topIcons=rootView.findViewById(R.id.scheduleDialog);
            topIcons.addView(trash);
            trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("DAS/FullDialog","Delete Button");

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
        final EditText startTime=rootView.findViewById(R.id.taskStart);
        startTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int Mhour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int Mminute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime.setText( selectedHour + ":" + selectedMinute);
                        stHr=selectedHour;
                        stM=selectedMinute;

                    }
                }, Mhour, Mminute, true);//No 12 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        //Timepicker end time dialog
        final EditText endTime=rootView.findViewById(R.id.taskEnd);
        endTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int Mhour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int Mminute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(),AlertDialog.THEME_DEVICE_DEFAULT_DARK, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, Mhour, Mminute, true);//yes 12 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        //On Click SAVE
        (rootView.findViewById(R.id.button_save)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Log.d("DailyScheduler","Saving data!");
                DatabaseHandler db = new DatabaseHandler(getContext());
                EditText taskNameEdit=getActivity().findViewById(R.id.taskName);
                EditText taskLabelEdit=getActivity().findViewById(R.id.taskLabel);
                EditText taskTimeStartEdit=getActivity().findViewById(R.id.taskStart);
                EditText taskTimeEndEdit=getActivity().findViewById(R.id.taskEnd);

                String task=taskNameEdit.getText().toString();
                String label=taskLabelEdit.getText().toString();
                String time=taskTimeStartEdit.getText().toString()+"-"+taskTimeEndEdit.getText().toString();
                //Validation
                boolean required=(task==null)||(label==null)||(time==null);
                if(required==true)
                {
                    Log.i("DAS/FSD","Validation required");
                }
                Log.d("DIALOG","Time "+time);
                Log.d("Name:","H "+taskNameEdit.getText().toString()+taskLabelEdit.getText().toString()+taskTimeEndEdit.getText().toString());
                db.addSchedule(new Schedule(task,label,time));
              //  db.addSchedule(new Schedule("Task 1","Label 1","Time 1"));
                dismiss();
            }
        });
        return rootView;
    }
    private void incrementEnd(int incrby){
        stHr=endH;
        stM=endM;
        endM+=incrby;
        while (endM>60){
            endM-=60;
            endH++;
        }
        if(endH>12){
            endH-=12;
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

}
