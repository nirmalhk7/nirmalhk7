package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.app.Dialog;
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
import android.widget.TimePicker;
import android.widget.Toolbar;

import com.nirmalhk7.nirmalhk7.R;

import java.sql.Time;
public class FullScreenDialog extends DialogFragment{
    public int key;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.full_screen_layout, container, false);
        Bundle bundle=this.getArguments();
        if(bundle!=null)
        {
            ImageView trash=new ImageView(getContext());
            trash.setImageResource(R.drawable.ic_trash);
            trash.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            trash.setPadding(pxDP.DpToPx(getContext(),10),0,pxDP.DpToPx(getContext(),10),0);
            LinearLayout topIcons=rootView.findViewById(R.id.scheduleDialog);
            topIcons.addView(trash);

        }

        (rootView.findViewById(R.id.button_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        EditText label=rootView.findViewById(R.id.taskLabel);
        (rootView.findViewById(R.id.button_save)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Log.d("DailyScheduler","Saving data!");
                DatabaseHandler db = new DatabaseHandler(getContext());
                EditText taskNameEdit=getActivity().findViewById(R.id.taskName);
                EditText taskLabelEdit=getActivity().findViewById(R.id.taskLabel);
                EditText taskTimeEdit=getActivity().findViewById(R.id.taskTime);


                String task=taskNameEdit.getText().toString();
                String label=taskLabelEdit.getText().toString();
                TimePicker t=getActivity().findViewById(R.id.timeset);
                int HH=t.getHour();
                int MM=t.getMinute();
                String time=Integer.toString(HH)+Integer.toString(MM);


                boolean required=(task==null)||(label==null)||(time==null);
                if(required==true)
                {
                    
                }
                Log.d("DIALOG","Time "+time);
                //  Log.d("Name:","H "+taskNameEdit.getText().toString()+taskLabelEdit.getText().toString()+taskTimeEdit.getText().toString());
                db.addSchedule(new Schedule(task,label,time));
              //  db.addSchedule(new Schedule("Task 1","Label 1","Time 1"));
                dismiss();
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
