package com.nirmalhk7.nirmalhk7.attendance;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toolbar;

import com.nirmalhk7.nirmalhk7.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthChangeListener;
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
        final MCalendarView calendarView = (findViewById(R.id.calendar_subject));

        calendarView.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                Log.d("ATT/SSA",month+".");
                calendarView.markDate(
                        new DateData(2019, month, 1).setMarkStyle(new MarkStyle(MarkStyle.DOT, Color.GREEN)));

                calendarDatabase database2 = Room.databaseBuilder(getBaseContext(), calendarDatabase.class, "mydby")
                        .allowMainThreadQueries().fallbackToDestructiveMigration()
                        .build();

                calendarDAO CalendarDAO = database2.getCalendarDAO();

                ArrayList<attendanceItem> SubjectItem = new ArrayList<>();



                // Create an {@link attendanceAdapter}, whose data source is a list of {@link attendanceItem}s. The
                // adapter knows how to create list items for each item in the list.
                attendanceAdapter adapter = new attendanceAdapter(getBaseContext(), SubjectItem,1);


                // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
                // There should be a {@link ListView} with the view ID called list, which is declared in the
                // word_list.xml layout file.
                ListView listView = (ListView) findViewById(R.id.list_single_subject_calendar);

                // Make the {@link ListView} use the {@link attendanceAdapter} we created above, so that the
                // {@link ListView} will display list items for each {@link attendanceItem} in the list.
                listView.setAdapter(adapter);

            }
        });

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
