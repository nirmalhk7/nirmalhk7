package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nirmalhk7.nirmalhk7.R;

import java.util.ArrayList;
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
        DatabaseHandler db = new DatabaseHandler(getContext());

        // Inserting Schedules
        Log.d("Insert: ", "Inserting ..");
     /*
        db.addSchedule(new Schedule("Task 1","Label 1","Time 1"));
        db.addSchedule(new Schedule("Task 2","Label 2","Time 2"));
        db.addSchedule(new Schedule("Task 3","Label 3","Time 3"));
        db.addSchedule(new Schedule("Task 4","Label 4","Time 4"));
*/
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Schedule> scheduleOP = db.getAllSchedules();

        ArrayList<scheduleItem> sch = new ArrayList<scheduleItem>();
        for (Schedule cn : scheduleOP) {
            String log = "Task: " + cn.getTask() + " ,Time: " + cn.getTime() + " ,Label: " +
                    cn.getLabel();
            // Writing Contacts to log
            Log.d("Name: ", log);
            sch.add(new scheduleItem(cn.getTask(), cn.getTime(),cn.getLabel()));
        }

        // Create an {@link ScheduleAdapter}, whose data source is a list of {@link scheduleItem}s. The
        // adapter knows how to create list items for each item in the list.
        ScheduleAdapter adapter = new ScheduleAdapter(getContext(), sch);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = view.findViewById(R.id.list_item);

        // Make the {@link ListView} use the {@link ScheduleAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link scheduleItem} in the list.
        listView.setAdapter(adapter);

        final ListView singleItem=view.findViewById(R.id.list_item);
        singleItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DAILYSCHEDULE","LongClicked!");
                FullScreenDialog newFragment = new FullScreenDialog();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                Bundle args = new Bundle();
                args.putInt("key", 10);
                Log.d("DS","PSN:"+Integer.toString(position));
                newFragment.setArguments(args);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
                return false;
            }
        });
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
