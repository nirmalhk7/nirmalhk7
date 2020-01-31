package com.nirmalhk7.nirmalhk7.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.leinardi.android.speeddial.SpeedDialView;
import com.nirmalhk7.nirmalhk7.Controllers.AttendanceController;
import com.nirmalhk7.nirmalhk7.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AttendanceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AttendanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttendanceFragment extends Fragment {
    //  Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //  Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AttendanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttendanceFragment.
     */
    //  Rename and change types and number of parameters
    public static AttendanceFragment newInstance(String param1, String param2) {
        AttendanceFragment fragment = new AttendanceFragment();
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
        toolbar.setTitle("Attendance");

        AttendanceController attController=new AttendanceController(getContext(),getActivity());

        ListView listView = rootView.findViewById(R.id.list_item_allsubjects);

        attController.attachAdapter(listView);

        attController.swipeToRefresh((SwipeRefreshLayout) rootView.findViewById(R.id.pullToRefresh));

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("ATT/ALS","LongClick!");
//                SubjectLogDialogFragment x=new SubjectLogDialogFragment();
//                TextView subject=view.findViewById(R.id.subjName_subject);
//                TextView percent=view.findViewById(R.id.percent_subject);
//                TextView prabca=view.findViewById(R.id.presentabsent_subject);
//
//                String zpercent=percent.getText().toString().substring(8,percent.getText().toString().indexOf('%'));
//                String absent=prabca.getText().toString().substring(prabca.getText().toString().indexOf('A')+7);
//                String present=prabca.getText().toString().substring(8,prabca.getText().toString().indexOf('/')-1);
//
//
//
//                Log.d("ATT/ALS","OnLongItemListener "+zpercent+" / "+absent+" / "+present);
//                Bundle bundle=new Bundle();
//                bundle.putString("subject",subject.getText().toString());
//                bundle.putInt("present",Integer.parseInt(present));
//                bundle.putInt("absent",Integer.parseInt(absent));
//
//                bundle.putString("percent",zpercent);
//                x.setArguments(bundle);
//
//
//                FragmentTransaction ft=getFragmentManager().beginTransaction();
//                x.show(ft, SubjectLogDialogFragment.TAG);
//            }
//        });
        SpeedDialView speedDialView =getActivity().findViewById(R.id.speedDial);
        speedDialView.setVisibility(View.VISIBLE);
        speedDialView.clearActionItems();

        attController.addSpeedDialOptions(speedDialView,getResources(),getActivity().getTheme());
        attController.speedDialOnClick(speedDialView,getActivity().getSupportFragmentManager());



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


}
