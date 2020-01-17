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

import com.google.android.material.snackbar.Snackbar;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.common;
import com.nirmalhk7.nirmalhk7.controllers.Converters;
import com.nirmalhk7.nirmalhk7.controllers.TimetableController;
import com.nirmalhk7.nirmalhk7.controllers.TimetableDialogControllerInterface;

import java.util.Calendar;

public class TimetableDialog extends DialogFragment {
    public int key;
    private String PAGE_TAG;
    private TimetableArrayAdapter ttadapter;
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

        AutoCompleteTextView taskNameEdit = rootView.findViewById(R.id.taskName);
        EditText subjCode=rootView.findViewById(R.id.subjCode);
        final EditText taskTimeStartEdit = rootView.findViewById(R.id.taskStart);
        final EditText taskTimeEndEdit = rootView.findViewById(R.id.taskEnd);
        RadioGroup dayrg=rootView.findViewById(R.id.rgDay);

        final TimetableController ttController=new TimetableController(rootView,getContext());
        final TimetableDialogControllerInterface ttdController=new TimetableDialogControllerInterface(taskNameEdit,subjCode,
                taskTimeStartEdit,taskTimeEndEdit,dayrg,getContext());
        final common mCommon=new common(getContext());
        //If editing
        if (bundle.getBoolean("editing")) {
            //trash is the trashbox for deleting;
            LinearLayout topIcons = rootView.findViewById(R.id.scheduleDialog);
            ImageView trash=ttdController.onEditSetup(dbNo,topIcons);
            trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(getClass().getName(), "Delete Button Clicked!");

                    ttdController.deleteEntry(dbNo);
                    Snackbar.make(rootView,"Please Refresh to Load",Snackbar.LENGTH_SHORT);

                    dismiss();
                }
            });

            ttdController.onLongClick(bundle,trash,rootView);
        }


        else
        {
           ttdController.onAddNew(rootView,bundle);
        }

        ttdController.dialogListen();
        String[] subject=ttdController.autocompleteSetup();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), R.layout.partial_suggestion, subject);
        taskNameEdit.setThreshold(1); //will start working from first character
        taskNameEdit.setAdapter(adapter);

        //Close button action
        (rootView.findViewById(R.id.button_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommon.hideKeyboard(rootView);
                dismiss();
            }
        });

        //Timepicker start dialog onclick
        taskTimeStartEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogTimePicker(rootView,1, taskTimeStartEdit);

            }
        });

        //Timepicker end time dialog
        taskTimeEndEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogTimePicker(rootView,2, taskTimeEndEdit);
            }
        });

        taskTimeStartEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(taskTimeEndEdit.getText().toString()!=null)
                    taskTimeEndEdit.setText(Converters.date_to(Converters.time_add(Converters.to_date(editable.toString(),"hh:mm a"),55*60*1000),"hh:mm a"));
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
                RadioGroup day=getActivity().findViewById(R.id.rgDay);
                if(Validation(rootView))
                    ttdController.saveTimetable(taskNameEdit.getText().toString(),SubjCode.getText().toString(),taskTimeStartEdit.getText().toString(),
                            taskTimeEndEdit.getText().toString(),
                            getDayChecked(rootView,day),bundle.getBoolean("editing"),dbNo);
                ttController.refreshOnSave(TimetableLoopingPagerAdapter.ttadapter,ttController);

                mCommon.hideKeyboard(rootView);
                dismiss();
            }
        });
        return rootView;
    }

    private void dialogTimePicker(final View rv,final int whatTimeSelected, final EditText TimeEdt) {
        //  Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int Mhour,Mminute;
        Mhour=Mminute=0;
        if(!TimeEdt.getText().toString().matches("")) {
            Mhour = Integer.parseInt(Converters.dtConverter(TimeEdt.getText().toString(), "hh:mm a", "H"));
            Mminute = Integer.parseInt(Converters.dtConverter(TimeEdt.getText().toString(), "hh:mm a", "mm"));
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
                TimeEdt.setText(Converters.dtConverter(time,"HH:mm","hh:mm a"));



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

    private boolean Validation(View rootview){
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
