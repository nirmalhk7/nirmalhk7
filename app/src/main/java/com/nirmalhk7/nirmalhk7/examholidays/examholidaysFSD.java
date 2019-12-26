package com.nirmalhk7.nirmalhk7.examholidays;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;

import androidx.room.Room;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nirmalhk7.nirmalhk7.utility.Converters;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;

import java.util.Calendar;

public class examholidaysFSD extends DialogFragment {
    public int key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private int mYear, mMonth, mDay, mHour, mMinute;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.examholiday_fullscreen, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ImageView trash = new ImageView(getContext());
            trash.setImageResource(R.drawable.ic_trash);
            trash.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            int pxstd = getContext().getResources().getDimensionPixelSize(R.dimen.standard_dimen);
            trash.setPadding(pxstd, 0, pxstd, 0);
            LinearLayout topIcons = rootView.findViewById(R.id.examHolidayDialog);
            topIcons.addView(trash);


        }

        (rootView.findViewById(R.id.button_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        RadioButton exam = rootView.findViewById(R.id.examRadio);
        RadioButton holiday=rootView.findViewById(R.id.holidayRadio);
        Bundle b=getArguments();
        if(b.getInt("key")==0)
        {
            exam.setChecked(true);
            EditText type= rootView.findViewById(R.id.examHoliday_type);
            EditText desc = rootView.findViewById(R.id.examHoliday_description);
            type.setVisibility(View.VISIBLE);
            desc.setVisibility(View.VISIBLE);
        }
        else{
            holiday.setChecked(true);
            EditText type = rootView.findViewById(R.id.examHoliday_type);
            EditText desc = rootView.findViewById(R.id.examHoliday_description);
            type.setVisibility(View.INVISIBLE);
            desc.setVisibility(View.INVISIBLE);
        }

        RadioGroup rdg=rootView.findViewById(R.id.radioGrp);
        rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.examRadio){
                    Log.d("EAH/FSD","EXAM");
                    EditText type= rootView.findViewById(R.id.examHoliday_type);
                    EditText desc = rootView.findViewById(R.id.examHoliday_description);
                    type.setVisibility(View.VISIBLE);
                    desc.setVisibility(View.VISIBLE);
                }
                else
                {
                    Log.d("EAH/FSD","HOLIDAY");
                    EditText type = rootView.findViewById(R.id.examHoliday_type);
                    EditText desc = rootView.findViewById(R.id.examHoliday_description);
                    type.setVisibility(View.INVISIBLE);
                    desc.setVisibility(View.INVISIBLE);

                }
            }
        });


        (rootView.findViewById(R.id.button_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Validation(rootView))
                {
                   Log.d("EAH/FSD","Validation Successful");
                    saveDialog(rootView);
                }
                Log.d("EAH/FSD","Validation Unsuccessful");
            }
        });

        final EditText startdate = rootView.findViewById(R.id.examHoliday_startDate);
        final EditText enddate = rootView.findViewById(R.id.examHoliday_endDate);
        datelistener(startdate,enddate,1);
        datelistener(startdate,enddate,2);

        return rootView;
    }

    void saveDialog(View view)
    {
        EditText name=rootView.findViewById(R.id.examHoliday_name);
        EditText startdate=rootView.findViewById(R.id.examHoliday_startDate);
        EditText enddate=rootView.findViewById(R.id.examHoliday_endDate);
        EditText type=rootView.findViewById(R.id.examHoliday_type);
        EditText desc=rootView.findViewById(R.id.examHoliday_description);
        RadioButton exam=rootView.findViewById(R.id.examRadio);

        DBGateway database = Room.databaseBuilder(getContext(), DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        ExamholidaysDAO EHDAO = database.getEHDAO();

        ExamholidaysEntity entity=new ExamholidaysEntity();
        entity.setmName(name.getText().toString());
        entity.setStart(Converters.to_date(startdate.getText().toString(),"MMM d, yyyy"));
        entity.setEnd(Converters.to_date(enddate.getText().toString(),"MMM d, yyyy"));
        entity.setmType(type.getText().toString());
        entity.setmDescription(desc.getText().toString());
        if(exam.isChecked()){
            entity.setHolexa(1);
        }
        else{
            entity.setHolexa(2);
        }
        EHDAO.insertOnlySingleEvent(entity);
        examHolidays.adapter.notifyDataSetChanged();
        dismiss();

    }
    void datelistener(final EditText startdate,final EditText enddate,final int i){
        EditText date=new EditText(getContext());
        if(i==1)
        {
            date=startdate;
        }
        else if(i==2)
        {
            date=enddate;
        }
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                final Calendar date=Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String dsel="";
                                if(dayOfMonth<10)
                                {
                                    dsel+="0";
                                }
                                dsel+=dayOfMonth+"/";
                                if(monthOfYear<10)
                                {
                                    dsel+="0";
                                }
                                dsel+=(monthOfYear+1)+"/"+year;
                                dsel=Converters.dtConverter(dsel,"dd/MM/yyyy","MMM d, yyyy");

                                if(i==1)
                                {
                                    startdate.setText(dsel);
                                    enddate.setText(dsel);
                                }
                                else if(i==2)
                                {
                                    enddate.setText(dsel);
                                }
                                date.set(dayOfMonth,(monthOfYear + 1), year);

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();


                if(i==1)
                {
                    datePickerDialog.setTitle("Start Date");
                    datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
                }
                else{
                    datePickerDialog.setTitle("End Date");

                    datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
                }

            }
        });
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public boolean Validation(View rootview){
        EditText name=rootview.findViewById(R.id.examHoliday_name);
        EditText type=rootview.findViewById(R.id.examHoliday_type);
        EditText endDate=rootview.findViewById(R.id.examHoliday_endDate);
        EditText startDate=rootview.findViewById(R.id.examHoliday_startDate);
        String Rname=name.getText().toString(),
                Rtype=type.getText().toString(),
                RendDate=endDate.getText().toString(),
                RstartDate=startDate.getText().toString();

        rootview.findViewById(R.id.examHoliday_startDate);
        if(((RadioButton)rootview.findViewById(R.id.examRadio)).isChecked())
        {
            if(Rtype.isEmpty())
            {
                Log.d("EAH/FSD","Validation startDate");
                endDate.setError("Required");
                return false;
            }
        }
        if(Rname.isEmpty()||RendDate.isEmpty()||RstartDate.isEmpty())
        {
            if(Rname.isEmpty())
            {
                Log.d("EAH/FSD","Validation Name");
                name.setError("Required");
            }
            if(RendDate.isEmpty())
            {
                Log.d("EAH/FSD","Validation endDate");
                endDate.setError("Required");

            }
            if(RstartDate.isEmpty())
            {
                Log.d("EAH/FSD","Validation startDate");
                endDate.setError("Required");

            }
            return false;
        }

        return true;

    }

}
