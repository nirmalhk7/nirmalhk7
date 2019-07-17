package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.leinardi.android.speeddial.SpeedDialView;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.convert;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import static android.app.Notification.VISIBILITY_PUBLIC;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DailyScheduleList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DailyScheduleList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyScheduleList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static int mday;
    public static int GTD(){
        return mday;
    }

    public String scheduleTitle;
    public String scheduleLabel;
    public String scheduleTime;
    private OnFragmentInteractionListener mListener;



    public DailyScheduleList() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyScheduleList.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyScheduleList newInstance(String param1, String param2) {
        DailyScheduleList fragment = new DailyScheduleList();
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
        View view = inflater.inflate(R.layout.fragment_daily_schedule_list, container, false);

        Bundle bundle=this.getArguments();
        if(bundle!=null)
        {
            mday=bundle.getInt("day");
            Log.d("DAS/DSL/","Bundle!:"+mday);
        }
        SpeedDialView speedDialView = getActivity().findViewById(R.id.speedDial);
        speedDialView.setVisibility(View.VISIBLE);


        speedDialView.setOnChangeListener(new SpeedDialView.OnChangeListener() {
            @Override
            public boolean onMainActionSelected() {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                FullScreenDialog newFragment = new FullScreenDialog();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
                return false;

            }

            @Override
            public void onToggleChanged(boolean isOpen) {
                Log.d("xxx","yyy");
            }
        });
        Log.d("DAS/DSL/Ta", "Tab " + DailySchedule.tabPosition);


        // Inserting Schedules
        Log.d("Insert: ", "Inserting ..");
        DSLfetchDB(view);

        DSLonRefresh(view);

        final ListView singleSchedule = view.findViewById(R.id.list_item);
        singleSchedule.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DAS/DSL", "LongClicked!");

                TextView title = view.findViewById(R.id.miwok_text_view);
                TextView label = view.findViewById(R.id.default_text_view);
                TextView time = view.findViewById(R.id.default_time);
                TextView idx=view.findViewById(R.id.itemid);
                String starttime=time.getText().toString().substring(0,time.getText().toString().indexOf('-')-1);
                String endtime=time.getText().toString().substring(time.getText().toString().indexOf('-')+2,time.getText().toString().length());
                Log.d("CONVERTX",endtime+"-"+starttime);



                FullScreenDialog newFragment = new FullScreenDialog();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                callNotification(title.getText().toString(), time.getText().toString());

                Bundle args = new Bundle();
                args.putInt("key", Integer.parseInt(idx.getText().toString()));
                args.putString("title", title.getText().toString());
                args.putString("label", label.getText().toString());
                args.putString("starttime", starttime);
                args.putString("endtime",endtime);
                args.putInt("day",mday);

                Log.d("DS", "PSN:" + Integer.toString(position));
                newFragment.setArguments(args);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
                return false;
            }
        });
        return view;
    }


    private void callNotification(String heading, String title) {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "0")
                .setSmallIcon(R.drawable.ic_stat_daily_scheduler)
                .setContentTitle(heading)
                .setContentText(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the intent that will fire when the user taps the notification
                .setAutoCancel(true)
                .addAction(R.drawable.ic_attendance, "PRESENT",
                        pendingIntent)
                .addAction(R.drawable.ic_attendance, "ABSENT",
                        pendingIntent)
                .addAction(R.drawable.ic_attendance, "CLASS CANCELLED",
                        pendingIntent)
                .setVisibility(VISIBILITY_PUBLIC);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

// notificationId is a unique inthist for each notification that you must define
        notificationManager.notify(0, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.appwidget_text);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("0", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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

    public void DSLfetchDB(View view)
    {

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");

        ArrayList<scheduleItem> sch = new ArrayList<scheduleItem>();

        DBGateway database = Room.databaseBuilder(getContext(), DBGateway.class, "mydb")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        scheduleDAO scheduleDAO = database.getScheduleDao();
        Log.d("DAS/DSL/Tabs","xx"+mday);
        List<Schedule> schedules = scheduleDAO.getScheduleByDay(mday);
        for (Schedule cn : schedules) {

            Log.d("DAS/DSL", "Printing: Task "+cn.getTask()+" Time "+cn.getStartTime()+cn.getEndTime()+" Label "+cn.getLabel());
            sch.add(new scheduleItem(cn.getTask(), convert.railtonormal(cn.getStartTime()),convert.railtonormal(cn.getEndTime()),cn.getLabel(),cn.getId(),cn.getDay()));
        }

        ScheduleAdapter adapter = new ScheduleAdapter(getContext(), sch);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = view.findViewById(R.id.list_item);

        // Make the {@link ListView} use the {@link ScheduleAdapter} we created above, so that the
        // {@link ListView} will display list schedules for each {@link scheduleSchedule} in the list.
        listView.setAdapter(adapter);

    }
    SwipeRefreshLayout pullToRefresh;
    ArrayAdapter adapter;
    public void DSLonRefresh(View rootview){
        pullToRefresh = rootview.findViewById(R.id.pullToRefresh);
        final View v=rootview;
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                Log.d("DAS/DSL","Refreshing");
                DSLfetchDB(v);
                pullToRefresh.setRefreshing(false);
            }
        });
    }


}
