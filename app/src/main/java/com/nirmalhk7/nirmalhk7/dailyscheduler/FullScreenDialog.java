package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toolbar;

import com.nirmalhk7.nirmalhk7.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FullScreenDialog extends DialogFragment{
    public int key;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private int stHr,stM,endH,endM;
    private int day;

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
            int day=bundle.getInt("day");
            int dbNo=bundle.getInt("key");

            //Pass title,label and time value to EditText
            EditText taskNameEdit=rootView.findViewById(R.id.taskName);
            taskNameEdit.setText(title);
            EditText taskLabelEdit=rootView.findViewById(R.id.taskLabel);
            taskLabelEdit.setText(label);




            Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
            switch(day)
            {
                case 0:spinner.setSelection(6);break;
                case 1:spinner.setSelection(1);break;
                case 2:spinner.setSelection(2);break;
                case 3:spinner.setSelection(3);break;
                case 4:spinner.setSelection(4);break;
                case 5:spinner.setSelection(5);break;
                case 6:spinner.setSelection(6);break;
            }

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


        // Spinner element
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DAS/FSD/Spn","L"+position);
                day=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Monday");
        categories.add("Tuesday");
        categories.add("Wednesday");
        categories.add("Thursday");
        categories.add("Friday");
        categories.add("Saturday");
        categories.add("Sunday");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

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
                db.addSchedule(new Schedule(task,label,time,day));
              //  db.addSchedule(new Schedule("Task 1","Label 1","Time 1"));
                dismiss();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment newFragment;
                newFragment = new DailySchedule();
                transaction.replace(R.id.fullscreen, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return rootView;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

}
