package com.nirmalhk7.nirmalhk7.attendance;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nirmalhk7.nirmalhk7.MainActivity;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.settings.SettingsActivity;

import java.util.ArrayList;

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
    private boolean isFABOpen;
    public FloatingActionButton fab;

    public FloatingActionButton fab1;
    public FloatingActionButton fab2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_attendance_all_subjects, container, false);
        View tbV= getLayoutInflater().inflate(R.layout.app_bar_main, null);

        ImageButton c= getActivity().findViewById(R.id.toolbarButton1);
        if(c==null){
            Log.d("ATT","NULL");
        }
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ATT","Subject List clicked");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment newFragment;
                newFragment = new AllSubjects();
                transaction.replace(R.id.fullscreen, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        LinearLayout pending= rootView.findViewById(R.id.pendingSubjects_subject);
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
        ArrayList<attendanceItem> SubjectItem = new ArrayList<attendanceItem>();
        SubjectItem.add(new attendanceItem("Subject 1",2,1));

        SubjectItem.add(new attendanceItem("Subject 2",4,2));

        SubjectItem.add(new attendanceItem("Subject 3",6,3));

        SubjectItem.add(new attendanceItem("Subject 4",8,4));




        // Create an {@link attendanceAdapter}, whose data source is a list of {@link attendanceItem}s. The
        // adapter knows how to create list items for each item in the list.
        attendanceAdapter adapter = new attendanceAdapter(getContext(), SubjectItem,1);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) rootView.findViewById(R.id.list_item_allsubjects);

        // Make the {@link ListView} use the {@link attendanceAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link attendanceItem} in the list.
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getActivity(), SingleSubjectActivity.class);

                i.putExtra("subj", "HELLO");
                startActivity(i);
            }
        });
        fab=getActivity().findViewById(R.id.fab);
        fab1 = (FloatingActionButton) getActivity().findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) getActivity().findViewById(R.id.fab2);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }

            }
        });
        fab1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(isFABOpen){

                }
            }
        });

        return rootView;
    }
    private void showFABMenu(){
        isFABOpen=true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));

    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
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
