package com.nirmalhk7.nirmalhk7.academics;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.nirmalhk7.nirmalhk7.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Academics.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Academics#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Academics extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Academics() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Academics.
     */
    // TODO: Rename and change types and number of parameters
    public static Academics newInstance(String param1, String param2) {
        Academics fragment = new Academics();
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
        View view= inflater.inflate(R.layout.fragment_academics, container, false);

        EditText[] sgpa=new EditText[8];
        EditText[] credits=new EditText[8];
        LinearLayout semPtr=view.findViewById(R.id.semPtr);
        LinearLayout semCred=view.findViewById(R.id.semCred);
        for(int i=1;i<=8;++i)
        {


            sgpa[(i-1)]=new EditText(getContext());
            sgpa[(i-1)].setInputType(InputType.TYPE_CLASS_NUMBER);
            semPtr.addView(sgpa[(i-1)]);

            credits[(i-1)]=new EditText(getContext());
            credits[(i-1)].setInputType(InputType.TYPE_CLASS_NUMBER);
            semCred.addView(credits[(i-1)]);

        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
        getUserVisibleHint();
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
