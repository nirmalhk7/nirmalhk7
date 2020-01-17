package com.nirmalhk7.nirmalhk7.examholidays;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.leinardi.android.speeddial.SpeedDialView;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.controllers.ExamHolidaysController;
import com.nirmalhk7.nirmalhk7.util.MyBottomSheetDialogFragment;

import static com.nirmalhk7.nirmalhk7.attendance.SubjectLogDialogFragment.TAG;

public class ExamHolidayFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static ExamHolidayArrayAdapter adapter;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_exam_holidays, container, false);
        ListView listView = rootView.findViewById(R.id.list_item_examholiday);
        SpeedDialView speed = getActivity().findViewById(R.id.speedDial);
        ExamHolidaysController ehController=new ExamHolidaysController(rootView,getContext(),listView);

        try{
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("nirmalhk7");
        }catch (NullPointerException e)
        {
            Log.e(getClass().getName(),e.getMessage());
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView HorE=view.findViewById(R.id.holidayExam);
                TextView title=(view.findViewById(R.id.holidayExam_name));
                TextView id=view.findViewById(R.id.holidayExam_id);

                MyBottomSheetDialogFragment mySheetDialog = new MyBottomSheetDialogFragment();
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
                mySheetDialog.show(getFragmentManager(), "modalSheetDialog");
            }
        });

        speed.show();
        speed.clearActionItems();
        ehController.addSpeedDialOptions(speed,getResources(),getActivity().getTheme());
        ehController.speedDialOnClick(speed,getActivity().getSupportFragmentManager());
        ehController.swipeToRefresh((SwipeRefreshLayout)rootView.findViewById(R.id.pullToRefresh));
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

    public interface OnFragmentInteractionListener {
        //  Update argument type and name
        void onFragmentInteraction(Uri uri);
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
