package com.nirmalhk7.nirmalhk7.attendance;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.controllers.Converters;
import com.nirmalhk7.nirmalhk7.model.AttendanceListItem;
import com.nirmalhk7.nirmalhk7.model.SubjectlogDAO;
import com.nirmalhk7.nirmalhk7.model.SubjectlogEntity;
import com.nirmalhk7.nirmalhk7.model.TimetableDAO;
import com.nirmalhk7.nirmalhk7.model.TimetableEntity;

import java.util.ArrayList;
import java.util.List;

public class SubjectLogDialogFragment extends DialogFragment {

    public static String TAG = "TimetableDialog";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_attendance_subject_log_real, container, false);

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
        DBGateway database2 = DBGateway.getInstance(getContext());

        TimetableDAO SDAO=database2.getTTDao();
        List<TimetableEntity> x=SDAO.getScheduleCodeByTaskName(subjName.getText().toString());
        if(x.size()==1)
        {
            TextView code=rootView.findViewById(R.id.sal_code);
            code.setText(x.get(0).getSubjCode());
        }


        SALFetch(rootView,subject);
        swiperefresh(rootView,subject);
        return rootView;

    }
    private void onLongClickDelete(View rootView)
    {

        ListView listView = rootView.findViewById(R.id.list_item_subjects_log);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to delete attendance record of this subject on this day?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Clicked YES
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                return false;
            }
        });
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
        DBGateway database2 = DBGateway.getInstance(getContext());
        SubjectlogDAO SLDAO = database2.getSALDAO();
        List<SubjectlogEntity> subjLog=SLDAO.getAllLog(subj);


        ArrayList<AttendanceListItem> SubjectItem = new ArrayList<>();
        Log.d("ATT/ALS","Count"+subjLog.size());

        for (SubjectlogEntity cn : subjLog) {
         //   Log.d("ATT/ALS", "Printing: Task "+cn.getSubject()+" P "+cn.getPresent()+" A "+cn.getAbsent());
            SubjectItem.add(new AttendanceListItem(Converters.date_to(cn.getDaytime(),"dd MMM yyyy"), Converters.date_to(cn.getDaytime(),"EEE")+", "+ Converters.date_to(cn.getDaytime(),"hh:mm a"),cn.getPrabca(),cn.getId()));
        }
        AttendanceArrayAdapter adapter = new AttendanceArrayAdapter(getContext(), SubjectItem,2);

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
