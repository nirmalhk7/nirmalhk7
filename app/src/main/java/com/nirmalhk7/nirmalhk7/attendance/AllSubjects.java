package com.nirmalhk7.nirmalhk7.attendance;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.MainActivity;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.academics.Academics;
import com.nirmalhk7.nirmalhk7.dailyscheduler.FullScreenDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllSubjects.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllSubjects#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllSubjects extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AllSubjects() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllSubjects.
     */
    // TODO: Rename and change types and number of parameters
    public static AllSubjects newInstance(String param1, String param2) {
        AllSubjects fragment = new AllSubjects();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView= inflater.inflate(R.layout.fragment_attendance_all_subjects, container, false);
        Toolbar toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("nirmalhk7");
        View tbV= getLayoutInflater().inflate(R.layout.app_bar_main, null);

     /*   LinearLayout pending= rootView.findViewById(R.id.pendingSubjects_subject);
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ATT","Pending View clicked");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment newFragment;
                newFragment = new Attendance();
                transaction.replace(R.id.fullscreen, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
*/
        swiperefresh(rootView);

        ALSfetchDB(rootView);

        ListView listView = rootView.findViewById(R.id.list_item_allsubjects);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ATT/ALS","LongClick!");
                subjectAttendanceLog x=new subjectAttendanceLog();
                TextView subject=view.findViewById(R.id.subjName_subject);
                TextView percent=view.findViewById(R.id.percent_subject);
                TextView prabca=view.findViewById(R.id.presentabsent_subject);

                /*
                presentCount.setText("Present "+currentWord.getmPresent()+" / Absent "+currentWord.getmAbsent());
                percent.setText("Percent: "+result+"%");
                */

                String zpercent=percent.getText().toString().substring(8,percent.getText().toString().indexOf('%'));
                String absent=prabca.getText().toString().substring(prabca.getText().toString().indexOf('A')+7,prabca.getText().toString().length());
                String present=prabca.getText().toString().substring(8,prabca.getText().toString().indexOf('/')-1);



                Log.d("ATT/ALS","OnLongItemListener "+zpercent+" / "+absent+" / "+present);
                Bundle bundle=new Bundle();
                bundle.putString("subject",subject.getText().toString());
                bundle.putInt("present",Integer.parseInt(present));
                bundle.putInt("absent",Integer.parseInt(absent));

                bundle.putString("percent",zpercent);
                x.setArguments(bundle);


                FragmentTransaction ft=getFragmentManager().beginTransaction();
                x.show(ft,subjectAttendanceLog.TAG);
                return false;
            }
        });
        SpeedDialView speedDialView =getActivity().findViewById(R.id.speedDial);
        speedDialView.setVisibility(View.VISIBLE);
        speedDialView.setOnChangeListener(new SpeedDialView.OnChangeListener() {
            @Override
            public boolean onMainActionSelected() {
                return false;
            }

            @Override
            public void onToggleChanged(boolean isOpen) {

            }
        });
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.content, R.drawable.ic_examholidays)
                        .setLabel("Add Subject")
                        .setLabelColor(Color.WHITE)
                        .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorLightDark, getActivity().getTheme()))
                        .create()
        );
        speedDialView.setOnActionSelectedListener( new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.content:
                        Log.d("ATT/ALS","Selct");
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                        Att_FullScreenDialog newFragment = new Att_FullScreenDialog();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
                        return false; // true to keep the Speed Dial open
                    default:
                        return false;
                }
            }
        });



        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private SwipeRefreshLayout pullToRefresh;
    void swiperefresh(final View rootview){
        pullToRefresh = rootview.findViewById(R.id.pullToRefresh);
        pullToRefresh.setEnabled(true);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                Log.d("ATT/ALS","Refreshing");
                ALSfetchDB(rootview);
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    void ALSfetchDB(View rootView){

        DBGateway database2 = Room.databaseBuilder(getContext(), DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        attendanceDAO AttendanceDAO = database2.getAttendanceDao();

        List<attendanceEntity> attendance=AttendanceDAO.getAllSubject();
        ArrayList<attendanceItem> SubjectItem = new ArrayList<>();
        Log.d("ATT/ALS","Count"+attendance.size());
        
        for (attendanceEntity cn : attendance) {
            Log.d("ATT/ALS", "Printing: Task "+cn.getSubject()+" P "+cn.getPresent()+" A "+cn.getAbsent());
            SubjectItem.add(new attendanceItem(cn.getSubject(), cn.getPresent(),cn.getAbsent(),cn.getId()));
        }

        attendanceAdapter adapter = new attendanceAdapter(getContext(), SubjectItem,1);

        ListView listView = rootView.findViewById(R.id.list_item_allsubjects);
        listView.setAdapter(adapter);
    }




}
