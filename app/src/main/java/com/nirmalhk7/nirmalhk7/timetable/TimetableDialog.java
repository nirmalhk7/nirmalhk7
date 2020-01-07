package com.nirmalhk7.nirmalhk7.timetable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.DialogFragment;

import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.controllers.TimetableController;
import com.nirmalhk7.nirmalhk7.util.converter;

import java.util.Calendar;

public class TimetableDialog extends DialogFragment {
    public int key;
    public String PAGE_TAG;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private int stHr, stM, endH, endM;
    private int mday;
    int dbNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.dialog_timetable, container, false);
        final Bundle bundle = this.getArguments();
        final TimetableController ttController=new TimetableController(rootView,getContext());

        //If editing
        if (bundle.getBoolean("editing")) {

            //Toolbar toolbar=getActivity().findViewById(R.id.toolbar);
            //toolbar.setTitle("Edit Task");
            String title = bundle.getString("title");
            String label = bundle.getString("label");
            String startTime = bundle.getString("starttime");
            String endtime = bundle.getString("endtime");
            String subjcode=bundle.getString("subjcode");
            int rday = bundle.getInt("day");
            Log.d(PAGE_TAG,rday+" Provided by TT");
            dbNo = bundle.getInt("key");

            AutoCompleteTextView taskNameEdit = rootView.findViewById(R.id.taskName);
            EditText subjCode=rootView.findViewById(R.id.subjCode);
            EditText taskTimeStartEdit = rootView.findViewById(R.id.taskStart);
            EditText taskTimeEndEdit = rootView.findViewById(R.id.taskEnd);
            RadioGroup dayrg=rootView.findViewById(R.id.rgDay);

            taskNameEdit.setText(title);
            subjCode.setText(subjcode);
            taskTimeStartEdit.setText(startTime);
            taskTimeEndEdit.setText(endtime);
            ((RadioButton)dayrg.getChildAt(rday)).setChecked(true);

            //trash is the trashbox for deleting;
            int pxstd = getContext().getResources().getDimensionPixelSize(R.dimen.standard_dimen);
            ImageView trash = new ImageView(getContext());
            trash.setImageResource(R.drawable.ic_trash);
            trash.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            trash.setPadding(pxstd, 0, pxstd, 0);
            LinearLayout topIcons = rootView.findViewById(R.id.scheduleDialog);
            topIcons.addView(trash);
            trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(PAGE_TAG, "Delete Button Clicked!");
                    ttController.deleteTimetableEntry(dbNo);
                    dismiss();
                }
            });

        }


        else
        {
            RadioGroup dayrg=rootView.findViewById(R.id.rgDay);
            ((RadioButton)dayrg.getChildAt(bundle.getInt("day-selected")-1)).setChecked(true);
            EditText starttime=rootView.findViewById(R.id.taskStart);
            EditText endtime=rootView.findViewById(R.id.taskEnd);


        }

        final RadioGroup day=rootView.findViewById(R.id.rgDay);
        day.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId)
                {
                    case R.id.rbMon:
                        Log.d(PAGE_TAG,"Day Selected: Monday");
                        mday= converter.day_to_dayno("Mon");
                        break;
                    case R.id.rbTue:
                        Log.d(PAGE_TAG,"Day Selected: Tuesday");
                        mday= converter.day_to_dayno("Tue");
                        break;
                    case R.id.rbWed:
                        Log.d(PAGE_TAG,"Day Selected: Wednesday");
                        mday= converter.day_to_dayno("Wed");
                        break;
                    case R.id.rbThu:
                        Log.d(PAGE_TAG,"Day Selected: Thursday");
                        mday= converter.day_to_dayno("Thu");
                        break;
                    case R.id.rbFriday:
                        Log.d(PAGE_TAG,"Day Selected: Friday");
                        mday= converter.day_to_dayno("Fri");
                        break;
                    case R.id.rbSaturday:
                        Log.d(PAGE_TAG,"Day Selected: Saturday");
                        mday= converter.day_to_dayno("Sat");
                        break;


                }
            }
        });


        String[] subject=ttController.insertSuggestions();
        final AppCompatAutoCompleteTextView autoTextView;
        autoTextView = rootView.findViewById(R.id.taskName);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), R.layout.partial_suggestion, subject);
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

        startTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(endTime.getText().toString()!=null)
                    endTime.setText(converter.date_to(converter.time_add(converter.to_date(editable.toString(),"hh:mm a"),55*60*1000),"hh:mm a"));
            }
        });



        //On Click SAVE
        (rootView.findViewById(R.id.button_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("DailyScheduler", "Saving data!");

                AutoCompleteTextView taskNameEdit = getActivity().findViewById(R.id.taskName);
                AutoCompleteTextView SubjCode=getActivity().findViewById(R.id.subjCode);
                EditText taskTimeStartEdit = getActivity().findViewById(R.id.taskStart);
                EditText taskTimeEndEdit = getActivity().findViewById(R.id.taskEnd);
                if(Validation(rootView))
                    ttController.saveTimetable(taskNameEdit.getText().toString(),SubjCode.getText().toString(),taskTimeStartEdit.getText().toString(),taskTimeEndEdit.getText().toString(),getDayChecked(rootView,day),bundle.getBoolean("editing"),dbNo);
                dismiss();
            }
        });
        return rootView;
    }
    private EditText endtime;
    public void dialogTimePicker(final View rv,final int whatTimeSelected, final EditText TimeEdt) {
        //  Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int Mhour,Mminute;
        Mhour=Mminute=0;
        if(!TimeEdt.getText().toString().matches("")) {
            Mhour = Integer.parseInt(converter.dtConverter(TimeEdt.getText().toString(), "hh:mm a", "H"));
            Mminute = Integer.parseInt(converter.dtConverter(TimeEdt.getText().toString(), "hh:mm a", "mm"));
        }
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
                TimeEdt.setText(converter.dtConverter(time,"HH:mm","hh:mm a"));



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
        String taskName=EtaskName.getText().toString();
        String taskStartTime=EtaskStartTime.getText().toString();
        String taskEndTime=EtaskEndTime.getText().toString();
        if(taskName.isEmpty()||taskEndTime.isEmpty()||taskStartTime.isEmpty())
        {
            if(taskName.isEmpty())
            {
                Log.d(PAGE_TAG,"Validation taskName");
                EtaskName.setError("Required");
            }
            if(taskStartTime.isEmpty())
            {
                Log.d(PAGE_TAG,"Validation taskName");
                EtaskStartTime.setError("Required");

            }
            if(taskEndTime.isEmpty())
            {
                Log.d(PAGE_TAG,"Validation taskName");
                EtaskEndTime.setError("Required");

            }
            return false;
        }

        return true;

    }
    public int getDayChecked(View rootview,RadioGroup day)
    {

        RadioButton selected=rootview.findViewById(day.getCheckedRadioButtonId());
        mday=day.indexOfChild(selected);
        Log.d(PAGE_TAG,"getDayChecked Function "+mday);
        return mday;
    }


}
