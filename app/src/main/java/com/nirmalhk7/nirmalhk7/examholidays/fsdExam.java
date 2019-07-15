package com.nirmalhk7.nirmalhk7.examholidays;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
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

import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;

import java.util.Calendar;

public class fsdExam extends DialogFragment {
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
        exam.setChecked(true);

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

                EditText name=rootView.findViewById(R.id.examHoliday_name);
                EditText startdate=rootView.findViewById(R.id.examHoliday_startDate);
                EditText enddate=rootView.findViewById(R.id.examHoliday_endDate);
                EditText type=rootView.findViewById(R.id.examHoliday_type);
                EditText desc=rootView.findViewById(R.id.examHoliday_description);
                RadioButton exam=rootView.findViewById(R.id.examRadio);
                
                DBGateway database = Room.databaseBuilder(getContext(), DBGateway.class, "mydbz")
                        .allowMainThreadQueries().fallbackToDestructiveMigration()
                        .build();

                ehDAO EHDAO = database.getEHDAO();
                
                ehEntity entity=new ehEntity();
                entity.setmName(name.getText().toString());
                entity.setmDateStart(startdate.getText().toString());
                entity.setmDateEnd(enddate.getText().toString());
                entity.setmType(type.getText().toString());
                entity.setmDescription(desc.getText().toString());
                if(exam.isChecked()){
                    entity.setHolexa(1);
                }
                else{
                    entity.setHolexa(2);
                }
                EHDAO.insertOnlySingleMovie(entity);
                dismiss();
            }
        });

        final EditText startdate = rootView.findViewById(R.id.examHoliday_startDate);
        final EditText enddate = rootView.findViewById(R.id.examHoliday_endDate);
        datelistener(startdate,enddate,1);
        datelistener(startdate,enddate,2);

        return rootView;
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                if(i==1)
                                {
                                    startdate.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                                    enddate.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                                }
                                else if(i==2)
                                {
                                    enddate.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                                }

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
                if(i==1)
                {
                    datePickerDialog.setTitle("Start Date");
                }
                else{
                    datePickerDialog.setTitle("End Date");
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

}
