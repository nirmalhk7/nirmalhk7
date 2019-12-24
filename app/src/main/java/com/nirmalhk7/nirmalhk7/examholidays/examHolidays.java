package com.nirmalhk7.nirmalhk7.examholidays;

import android.arch.persistence.room.Room;
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
import android.widget.ListView;
import android.widget.TextView;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.nirmalhk7.nirmalhk7.Converters;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.utility.MyBottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import static com.nirmalhk7.nirmalhk7.attendance.subjectAttendanceLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link examHolidays.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link examHolidays#newInstance} factory method to
 * create an instance of this fragment.
 */
public class examHolidays extends Fragment {
    //  Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static ExamHolidayAdapter adapter;
    //  Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public examHolidays() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment examHolidays.
     */
    //  Rename and change types and number of parameters
    public static examHolidays newInstance(String param1, String param2) {
        examHolidays fragment = new examHolidays();
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
        final View rootView = inflater.inflate(R.layout.fragment_exam_holidays, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("nirmalhk7");
        EAHfetchDB(rootView);
        DSLonRefresh(rootView);

        // Make the {@link ListView} use the {@link ScheduleAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link scheduleItem} in the list.

        ListView listView = rootView.findViewById(R.id.list_item_examholiday);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView HorE=view.findViewById(R.id.holidayExam);
                TextView title=(view.findViewById(R.id.holidayExam_name));
                TextView id=view.findViewById(R.id.holidayExam_id);

                MyBottomSheetDialogFragment mySheetDialog = new MyBottomSheetDialogFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Bundle b=new Bundle();
                b.putInt("module",1);

                Log.d("EAH/EAH","Clicked "+HorE.getText().toString());
                if(HorE.getText().toString().equals("HOLIDAY"))
                {
                    //Holiday
                    b.putInt("holidayorexam",0);
                }
                else
                {
                    b.putInt("holidayorexam",1);
                }
                b.putString("dbkey",id.getText().toString());
                b.putString("title",title.getText().toString());

                mySheetDialog.setArguments(b);
                mySheetDialog.show(fm, "modalSheetDialog");
            }
        });


        SpeedDialView speed = getActivity().findViewById(R.id.speedDial);
        speed.show();
        speed.clearActionItems();
        speed.addActionItem(
                new SpeedDialActionItem.Builder(R.id.content, R.drawable.ic_examholidays)
                        .setLabel("Add Exam")
                        .setLabelColor(Color.WHITE)
                        .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorWarning, getActivity().getTheme()))
                        .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorLightDark, getActivity().getTheme()))
                        .create()
        );
        speed.addActionItem(new SpeedDialActionItem.Builder(R.id.label_container, R.drawable.ic_examholidays)
                .setLabel("Add Holiday")
                .setLabelColor(Color.WHITE)
                .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorGreen, getActivity().getTheme()))
                .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorLightDark, getActivity().getTheme()))
                .create()
        );

        speed.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fsdExam newFragment = new fsdExam();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                Bundle b = new Bundle();
                switch (speedDialActionItem.getId()) {
                    case R.id.content:
                        Log.d("ATT/ALS", "Select Exams");

                        b.putInt("key", 0);
                        newFragment.setArguments(b);
                        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
                        return false;
                    case R.id.label_container:
                        Log.d("ATT/ALS", "Select Holidays");
                        b.putInt("key", 1);
                        newFragment.setArguments(b);
                        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
                        return false; // true to keep the Speed Dial open
                    default:
                        return false;
                }
            }
        });


        return rootView;
    }

    //  Rename method, update argument and hook method into UI event
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
        //  Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void EAHfetchDB(View rootView) {
        DBGateway database = Room.databaseBuilder(getContext(), DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        ehDAO ehDAO = database.getEHDAO();
        ArrayList<heItem> hs = new ArrayList<heItem>();
        List<ehEntity> list = ehDAO.getItems();

        for (ehEntity cn : list) {
            Log.d("EAH/EAH", cn.getmType() + " ");
            if (cn.getStart().equals(cn.getEnd())) {
                hs.add(new heItem(cn.getId(), cn.getHolexa(), cn.getmName(), Converters.date_to_Dt(cn.getStart()), cn.getmType()));
            } else {
                hs.add(new heItem(cn.getId(), cn.getHolexa(), cn.getmName(), Converters.date_to_Dt(cn.getStart()) + " - " + Converters.date_to_Dt(cn.getEnd()), cn.getmType()));
            }
        }


        adapter = new ExamHolidayAdapter(getContext(), hs);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = rootView.findViewById(R.id.list_item_examholiday);
        listView.setAdapter(adapter);
        
    }

    SwipeRefreshLayout pullToRefresh;

    public void DSLonRefresh(final View rootview) {
        pullToRefresh = rootview.findViewById(R.id.pullToRefresh);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                Log.d("ATT/ALS", "Refreshing");
                EAHfetchDB(rootview);
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            Log.d("EAH/EAH", "Visible!!");
            adapter.notifyDataSetChanged();
        } else {

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            Log.d(TAG, ((Object) this).getClass().getSimpleName() + " is NOT on screen");
        } else {
            Log.d(TAG, ((Object) this).getClass().getSimpleName() + " is on screen");
        }
    }

}
