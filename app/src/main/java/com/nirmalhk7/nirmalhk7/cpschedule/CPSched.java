package com.nirmalhk7.nirmalhk7.cpschedule;

import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nirmalhk7.nirmalhk7.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Queue;
import java.util.Stack;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CPSched.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CPSched#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CPSched extends Fragment {

    private String MODULE_TAG="CPSCH/";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CPSched() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CPSched.
     */
    // TODO: Rename and change types and number of parameters
    public static CPSched newInstance(String param1, String param2) {
        CPSched fragment = new CPSched();
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
    public class event{
        public String ContestName;
        public String HostName;
        public String ContestURL;
        public String ContestStart;
        public String ContestDuration;
        event(String cname,String hname,String curl,String cstr,String cdur)
        {
            ContestName=cname;
            HostName=hname;
            ContestURL=curl;
            ContestStart=cstr;
            ContestDuration=cdur;
        }

    }
    public Queue<event> ContestsU;
    public void requestData(String url, final String category, final View rootview) {
        //Everything below is part of the Android Asynchronous HTTP Client

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d(MODULE_TAG+"CPAPI", "JSON: " + response.toString());

                try {
                    if (category.equals("cp")) {

                        JSONObject contests=response.getJSONObject("contests");
                        JSONArray upcomingContests=contests.getJSONArray("upcoming");
                        for(int i=0;i<upcomingContests.length();++i)
                        {
                            JSONObject cp=upcomingContests.getJSONObject(i);
                            Log.d(MODULE_TAG,cp.getString("contest_name")+"");
                            ContestsU.add(new event(cp.getString("contest_name"),cp.getString("host_name"),cp.getString("contest_url"),cp.getString("start"),cp.getString("duration")));

                        }





                    }

                } catch (JSONException e) {
                    Log.e(MODULE_TAG+"CPAPI :", e.toString());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e,
                                  JSONObject response) {

                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                Log.d("Ripple r", "Request fail! Status code: " + statusCode);
                Log.d("Ripple z", "Fail response: " + response);
                Log.e("ERROR", e.toString());
            }
        });
    }
    public boolean isOnline() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) getActivity().getSystemService(getActivity().getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            return true;
        } else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED) {

            return false;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_cpsched, container, false);
        if(isOnline())
        {
            requestData("http://testchallengehunt.appspot.com/v1/all","cp",rootView);
        }

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
