package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nirmalhk7.nirmalhk7.R;

import java.util.List;

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
    private Integer mday;

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
        Schedule sch;
        Schedule scha;
        AppDatabase database = AppDatabase.getDatabase(getContext());
        // cleanup for testing some initial data
        database.scheduleDao().removeAllUsers();
        List<Schedule> users = database.scheduleDao().getAll();
        if (users.size()==0) {
            database.scheduleDao().insert(new Schedule("Task 1","StartTime 1","Label 1"));
            database.scheduleDao().insert(new Schedule("Task Z","StartTime Z","Label Z"));

            sch = database.scheduleDao().getAll().get(0);
            scha = database.scheduleDao().getAll().get(1);
            Log.d("ROOM","Ans- "+sch.tasksDB);

            Log.d("ROOM","Ans- "+scha.tasksDB);
        }





        return view;
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
}
