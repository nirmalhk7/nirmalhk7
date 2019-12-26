package com.nirmalhk7.nirmalhk7.timetable;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asksira.loopingviewpager.LoopingViewPager;
import com.google.android.material.tabs.TabLayout;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.nirmalhk7.nirmalhk7.Converters;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.nirmalhk7.nirmalhk7.timetable.DemoInfinitePagerAdapter.ttadapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Timetable.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Timetable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Timetable extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Timetable() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Timetable.
     */
    // TODO: Rename and change types and number of parameters
    public static Timetable newInstance(String param1, String param2) {
        Timetable fragment = new Timetable();
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
    public static LoopingViewPager vpgr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_timetable, container, false);
        vpgr=rootView.findViewById(R.id.viewpager);
        vpgr.setCurrentItem(3);
        Log.d("CONVCC",""+vpgr.getCurrentItem());
        Toolbar toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Your Timetable");
        FragmentManager fmgr=getActivity().getSupportFragmentManager();
        ArrayList<Integer> dataItems=new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        DemoInfinitePagerAdapter adapter=new DemoInfinitePagerAdapter(getContext(),dataItems,true,fmgr);
        vpgr.setAdapter(adapter);

        SpeedDialView speedDialView = getActivity().findViewById(R.id.speedDial);
        speedDialView.setVisibility(View.VISIBLE);
        speedDialView.clearActionItems();

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
                Log.d("xxx", "yyy");
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

}
