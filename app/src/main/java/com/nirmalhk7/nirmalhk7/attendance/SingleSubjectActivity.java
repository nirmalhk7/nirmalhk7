package com.nirmalhk7.nirmalhk7.attendance;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toolbar;

import com.nirmalhk7.nirmalhk7.R;

import java.util.ArrayList;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class SingleSubjectActivity extends AppCompatActivity {

    public String subjName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_subject);


        Intent mIntent = getIntent();
        subjName= mIntent.getStringExtra("subj");
        Log.d("SSA/ATT",subjName);

        MCalendarView calendarView = (findViewById(R.id.calendar_subject));
        calendarView.markDate(
                new DateData(2019, 7, 1).setMarkStyle(new MarkStyle(MarkStyle.DOT, Color.GREEN)));
        calendarView.markDate(
                new DateData(2019, 7, 2).setMarkStyle(new MarkStyle(MarkStyle.DOT, Color.RED)));
        calendarView.markDate(
                new DateData(2019, 7, 3).setMarkStyle(new MarkStyle(MarkStyle.DOT, Color.BLUE)));

        calendarView.markDate(
                new DateData(2019, 7, 4).setMarkStyle(new MarkStyle(MarkStyle.DOT, Color.YELLOW)));




        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.

        calendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                Log.i("SSA/ATT",date.getMonth()+"/"+date.getDay()+"/"+date.getYear());
            }
        });
    }
}
