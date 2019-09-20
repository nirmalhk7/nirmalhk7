package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.nirmalhk7.nirmalhk7.R;

import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DailySchedule.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DailySchedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailySchedule extends Fragment {
    //  Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String MODULE_TAG="DAS/";
    private String FILE_TAG="DAS";

    //  Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DailySchedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailySchedule.
     */
    //  Rename and change types and number of parameters
    public static DailySchedule newInstance(String param1, String param2) {
        DailySchedule fragment = new DailySchedule();
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
    public static ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_daily_schedule, container, false);

        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(7);

        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        Toolbar toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Your Schedule");

        viewPager= view.findViewById(R.id.view_pager);


        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int startat=21;
        switch (day) {
            case Calendar.MONDAY:
                viewPager.setCurrentItem(startat);
                break;
            case Calendar.TUESDAY:
                viewPager.setCurrentItem(startat+1);
                break;

            case Calendar.WEDNESDAY:
                viewPager.setCurrentItem(startat+2);
                break;

            case Calendar.THURSDAY:
                viewPager.setCurrentItem(startat+3);
                break;

            case Calendar.FRIDAY:
                viewPager.setCurrentItem(startat+4);
                break;

            case Calendar.SATURDAY:
                viewPager.setCurrentItem(startat+5);
                break;
            case Calendar.SUNDAY:
                viewPager.setCurrentItem(startat+6);
                break;
        }


        return view;

    }






    //  Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    // Instances of this class are fragments representing a single
// object in our collection.

/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/
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
}

