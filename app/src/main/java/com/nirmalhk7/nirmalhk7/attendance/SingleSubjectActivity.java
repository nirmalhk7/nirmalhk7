package com.nirmalhk7.nirmalhk7.attendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.nirmalhk7.nirmalhk7.R;


public class SingleSubjectActivity extends AppCompatActivity {

    public String subjName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_subject);

        Intent mIntent = getIntent();
        subjName= mIntent.getStringExtra("subj");
        Log.d("SSA/ATT",subjName);

    }

}
