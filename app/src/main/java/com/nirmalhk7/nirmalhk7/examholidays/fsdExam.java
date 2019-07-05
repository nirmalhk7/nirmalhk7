package com.nirmalhk7.nirmalhk7.examholidays;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.nirmalhk7.nirmalhk7.R;

import java.util.Calendar;

public class fsdExam extends DialogFragment{
    public int key;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.examholiday_fullscreen, container, false);
        Bundle bundle=this.getArguments();
        if(bundle!=null)
        {
            ImageView trash=new ImageView(getContext());
            trash.setImageResource(R.drawable.ic_trash);
            trash.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            int pxstd=getContext().getResources().getDimensionPixelSize(R.dimen.standard_dimen);
            trash.setPadding(pxstd,0,pxstd,0);
            LinearLayout topIcons=rootView.findViewById(R.id.examHolidayDialog);
            topIcons.addView(trash);

        }

        (rootView.findViewById(R.id.button_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        RadioButton exam=rootView.findViewById(R.id.examRadio);
        exam.setChecked(true);

        if(exam.isChecked())
        {

            EditText label=rootView.findViewById(R.id.examHoliday_label);
            label.setEnabled(true);
            EditText desc=rootView.findViewById(R.id.examHoliday_description);
            desc.setEnabled(true);

            final EditText startdate=rootView.findViewById(R.id.examHoliday_startDate);
            final EditText enddate=rootView.findViewById(R.id.examHoliday_endDate);
            startdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    startdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    enddate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
                    datePickerDialog.show();
                }
            });

            enddate.setOnClickListener(new View.OnClickListener() {
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

                                    enddate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);

                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

                }
            });
        }
        else
        {
            EditText label=rootView.findViewById(R.id.examHoliday_label);
            label.setEnabled(false);
            EditText desc=rootView.findViewById(R.id.examHoliday_description);
            desc.setEnabled(false);
        }

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
