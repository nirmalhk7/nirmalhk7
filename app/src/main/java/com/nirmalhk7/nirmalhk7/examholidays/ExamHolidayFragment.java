package com.nirmalhk7.nirmalhk7.examholidays;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.util.converter;
import com.nirmalhk7.nirmalhk7.util.MyBottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import static com.nirmalhk7.nirmalhk7.attendance.SubjectLogDialogFragment.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExamHolidayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExamHolidayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExamHolidayFragment extends Fragment {
    //  Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static ExamHolidayArrayAdapter adapter;
    //  Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ExamHolidayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExamHolidayFragment.
     */
    //  Rename and change types and number of parameters
    public static ExamHolidayFragment newInstance(String param1, String param2) {
        ExamHolidayFragment fragment = new ExamHolidayFragment();
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

        // Make the {@link ListView} use the {@link TimetableArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link TimetableListItem} in the list.

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

                ExamHolidaysDialog newFragment = new ExamHolidaysDialog();
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

        ExamholidaysDAO examholidaysDAO = database.getEHDAO();
        ArrayList<ExamHolidaysListItem> hs = new ArrayList<ExamHolidaysListItem>();
        List<ExamholidaysEntity> list = examholidaysDAO.getEventsOrdered();

        for (ExamholidaysEntity cn : list) {
            Log.d("EAH/EAH", cn.getmType() + " ");
            if (cn.getStart().equals(cn.getEnd())) {
                hs.add(new ExamHolidaysListItem(cn.getId(), cn.getHolexa(), cn.getmName(), converter.date_to(cn.getStart(),"dd MMM yyyy"), cn.getmType()));
            } else {
                hs.add(new ExamHolidaysListItem(cn.getId(), cn.getHolexa(), cn.getmName(), converter.date_to(cn.getStart(),"dd MMM yyyy") + " - " + converter.date_to(cn.getEnd(),"dd MMM yyyy"), cn.getmType()));
            }
        }


        adapter = new ExamHolidayArrayAdapter(getContext(), hs);

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
