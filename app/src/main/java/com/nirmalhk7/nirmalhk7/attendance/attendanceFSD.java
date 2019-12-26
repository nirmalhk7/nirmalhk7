package com.nirmalhk7.nirmalhk7.attendance;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.timetable.TimetableDAO;
import com.nirmalhk7.nirmalhk7.timetable.TimetableEntity;

import java.util.Calendar;
import java.util.List;

public class attendanceFSD extends DialogFragment {
    public int key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.attendance_fsd, container, false);

        //If editing
        DBGateway database1 = Room.databaseBuilder(getContext(), DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        TimetableDAO timetableDAO = database1.getTTDao();

        DBGateway database2 = Room.databaseBuilder(getContext(), DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        final AttendanceDAO attendanceDAO = database2.getATTDao();
        final Bundle bundle = this.getArguments();
        if (bundle != null) {
            final int dbNo=bundle.getInt("key");
            Log.d("ATT/FSD","Bundle Passed: "+dbNo);
            EditText Present = rootView.findViewById(R.id.present_fsd);
            Present.setText(bundle.getInt("present")+"");
            EditText Absent = rootView.findViewById(R.id.absent_fsd);
            Absent.setText(bundle.getInt("absent")+"");
            AppCompatAutoCompleteTextView autoTextView=
                    rootView.findViewById(R.id.attendance_task);
            autoTextView.setText(bundle.getString("subject")+"");
            ImageView trash = new ImageView(getContext());
            trash.setImageResource(R.drawable.ic_trash);
            trash.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            int pxstd = getContext().getResources().getDimensionPixelSize(R.dimen.standard_dimen);
            trash.setPadding(pxstd, 0, pxstd, 0);
            LinearLayout topIcons = rootView.findViewById(R.id.attendanceDialog);
            topIcons.addView(trash);
            trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("DAS/FullDialog", "Delete Button");
                    DBGateway database = Room.databaseBuilder(getContext(), DBGateway.class, "finalDB")
                            .allowMainThreadQueries().fallbackToDestructiveMigration()
                            .build();
                    AttendanceDAO attDAO=database.getATTDao();
                    attDAO.deleteSchedule(attDAO.getSubjectbyId(dbNo));
                    dismiss();
                }
            });

        }





        List<TimetableEntity> scheduleEntities = timetableDAO.getSubjects("College");

        String[] subject = new String[scheduleEntities.size()];
        Log.d("ATT/FSD", "." + scheduleEntities.size());
        int i = 0;
        for (TimetableEntity cn : scheduleEntities) {
            subject[i] = cn.getTask();
            Log.d("ATT/FSD/", subject[i]);
            ++i;
        }


        final AppCompatAutoCompleteTextView autoTextView;
        autoTextView = rootView.findViewById(R.id.attendance_task);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, subject);
        autoTextView.setThreshold(1); //will start working from first character
        autoTextView.setAdapter(adapter);

        final EditText Present = rootView.findViewById(R.id.present_fsd);
        final EditText Absent = rootView.findViewById(R.id.absent_fsd);
        changeTotal(rootView, Present, Absent);


        //Close button action
        (rootView.findViewById(R.id.button_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        //On Click SAVE
        Button b = rootView.findViewById(R.id.ATT_button_save);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation(rootView)) {

                    if(bundle==null)
                    {
                        String subj = autoTextView.getText().toString();
                        try {
                            int present = Integer.valueOf(Present.getText().toString());
                            int absent = Integer.valueOf(Absent.getText().toString());
                            AttendanceEntity x = new AttendanceEntity();
                            x.setSubject(subj);
                            x.setPresent(present);
                            x.setAbsent(absent);
                            attendanceDAO.insertOnlySingleSubject(x);
                            Log.d("ATT/FSD", "Saving data!" + x.getSubject() + x.getPresent() + x.getAbsent());
                            dismiss();
                        } catch (NumberFormatException ex) { // handle your exception
                            Log.d("ATT/FSD", "java.lang.NumberFormatException");
                        }
                    }
                    //Editing Attendance Entry
                    else
                    {
                        String subj = autoTextView.getText().toString();
                        try {
                            int present = Integer.valueOf(Present.getText().toString());
                            int absent = Integer.valueOf(Absent.getText().toString());
                            AttendanceEntity x = attendanceDAO.getSubjectbyId(bundle.getInt("key"));
                            x.setSubject(subj);
                            x.setPresent(present);
                            x.setAbsent(absent);
                            attendanceDAO.updateSubject(x);
                            Toast.makeText(getActivity(), "Refresh to Update",
                                    Toast.LENGTH_LONG).show();
                            Log.d("ATT/FSD", "Editing data!" + x.getSubject() + x.getPresent() + x.getAbsent());
                            dismiss();
                        } catch (NumberFormatException ex) { // handle your exception
                            Log.d("ATT/FSD", "java.lang.NumberFormatException");
                        }
                    }
                }
            }
        });
        return rootView;
    }

    public void dialogTimePicker(int whatTimeSelected, final EditText Time) {
        //  Auto-generated method stub
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

    public void changeTotal(View rootView, final EditText pr, final EditText ab) {

        final TextView Total = rootView.findViewById(R.id.total_fsd);
        pr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int p = Integer.parseInt(s.toString());
                    int a = Integer.parseInt(ab.getText().toString());
                    Total.setText("Total Classes: " + Integer.toString(p + a));
                    Log.d("ATT/FSD", "TotalTV " + Total.getText());

                } catch (NumberFormatException e) {
                    Log.e("ATT/FSD", e.getMessage());
                }

            }
        });
        ab.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int a = Integer.parseInt(s.toString());
                    int p = Integer.parseInt(pr.getText().toString());
                    Total.setText("Total Classes: " + Integer.toString(p + a));
                    Log.d("ATT/FSD", "TotalTV " + Total.getText());

                } catch (NumberFormatException e) {
                    Log.e("ATT/FSD", e.getMessage());
                }

            }
        });

    }

    public boolean Validation(View rootview) {
        AppCompatAutoCompleteTextView EtaskName = rootview.findViewById(R.id.attendance_task);
        String taskName = EtaskName.getText().toString();
        if (taskName.isEmpty()) {
            Log.d("ATT/FSD", "Validation SubjectName");
            EtaskName.setError("Required");
            return false;
        }
        return true;

    }
}
