package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toolbar;

import com.nirmalhk7.nirmalhk7.R;

public class FullScreenDialog extends DialogFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.full_screen_layout, container, false);
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
