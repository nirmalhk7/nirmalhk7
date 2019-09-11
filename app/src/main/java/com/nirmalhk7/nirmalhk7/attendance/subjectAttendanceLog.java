package com.nirmalhk7.nirmalhk7.attendance;

import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.dailyscheduler.Schedule;
import com.nirmalhk7.nirmalhk7.dailyscheduler.scheduleDAO;

import java.util.ArrayList;
import java.util.List;

public class subjectAttendanceLog extends DialogFragment {

    public static String TAG = "FullScreenDialog";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.attendance_subjectattendancelog, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow);

        //Close button action
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        toolbar.setTitle("Subject Log");
        Bundle b=this.getArguments();
        String subject=b.getString("subject");
        int p=b.getInt("present");
        int a=b.getInt("absent");
        int c=b.getInt("cancelled");

        TextView subjName=rootView.findViewById(R.id.sal_subject);
        subjName.setText(subject);
        TextView present=rootView.findViewById(R.id.sal_p);
        present.setText(Integer.toString(p));
        TextView absent=rootView.findViewById(R.id.sal_a);
        absent.setText(Integer.toString(a));
        TextView cancel=rootView.findViewById(R.id.sal_c);
        cancel.setText(Integer.toString(c));
        DBGateway database2 = Room.databaseBuilder(getContext(), DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        scheduleDAO SDAO=database2.getScheduleDao();
        List<Schedule> x=SDAO.getScheduleCodeByTaskName(subjName.getText().toString());
        if(x.size()==1)
        {
            TextView code=rootView.findViewById(R.id.sal_code);
            code.setText(x.get(0).getSubjCode());
        }


        SALFetch(rootView,subject);
        swiperefresh(rootView,subject);
        return rootView;

    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    private void SALFetch(View rootView,String subj)
    {
        DBGateway database2 = Room.databaseBuilder(getContext(), DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();
        subjectlogDAO SLDAO = database2.getSALDAO();
        List<subjectlogEntity> subjLog=SLDAO.getAllLog(subj);


        ArrayList<attendanceItem> SubjectItem = new ArrayList<>();
        Log.d("ATT/ALS","Count"+subjLog.size());

        for (subjectlogEntity cn : subjLog) {
         //   Log.d("ATT/ALS", "Printing: Task "+cn.getSubject()+" P "+cn.getPresent()+" A "+cn.getAbsent());
            SubjectItem.add(new attendanceItem(cn.getDateAdded(),cn.getDayTime(),cn.getPrabca(),cn.getId()));
        }
        attendanceAdapter adapter = new attendanceAdapter(getContext(), SubjectItem,2);

        ListView listView = rootView.findViewById(R.id.list_item_subjects_log);
        listView.setAdapter(adapter);
    }
    private SwipeRefreshLayout pullToRefresh;
    void swiperefresh(final View rootview,final String subj){
        pullToRefresh = rootview.findViewById(R.id.pullToRefresh);
        pullToRefresh.setEnabled(true);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                Log.d("ATT/ALS","Refreshing");
                SALFetch(rootview,subj);
                pullToRefresh.setRefreshing(false);
            }
        });
    }
}
