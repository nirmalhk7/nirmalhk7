package com.nirmalhk7.nirmalhk7;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String api_link;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public LinearLayout weather;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        api_link = "https://api.darksky.net/forecast/60569b87b5b2a6220c135e9b2e91646b/";
        weather=getActivity().findViewById(R.id.weather);
        if (isOnline()) {
            double longitude = 27.004;
            double latitude = 49.627;
            api_link = api_link.concat(Double.toString(longitude) + "," + Double.toString(latitude));
            api_link = api_link.concat("?units=si");
            Log.d("WeatherAPI",api_link);
            requestData(api_link, "weather");


        } else {
            TextView error=new TextView(getContext());
            error.setText("Phone not connected");
            weather.addView(error);
        }
    }
    public void requestData(String url, final String category) {
        //Everything below is part of the Android Asynchronous HTTP Client

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d("WeatherAPI", "JSON: " + response.toString());

                try {
                    if(category.equals("weather")) {

                        String icon = response.getJSONObject("currently").getString("icon");
                        String temp = response.getJSONObject("currently").getString("temperature");
                        String summary = response.getJSONObject("currently").getString("summary");
                        String rainWeek=response.getJSONObject("currently").getString("precipProbability");
                        Log.d("WeatherAPI",icon+"with"+temp);
                        weather=getActivity().findViewById(R.id.weather);
                        ImageView weatherIcon=new ImageView(getContext());



                        if (icon.equals("clear-day")) {
                            weatherIcon.setImageResource(R.drawable.ic_iconfinder_sunny);
                            weather.addView(weatherIcon);
                        } else if (icon.equals("clear-night")) {
                            weatherIcon.setImageResource(R.drawable.ic_iconfinder_moony);
                            weather.addView(weatherIcon);
                        } else if (icon.equals("rain")) {
                            weatherIcon.setImageResource(R.drawable.ic_iconfinder_rainy);
                            weather.addView(weatherIcon);
                        } else if (icon.equals("wind")) {
                            weatherIcon.setImageResource(R.drawable.ic_iconfinder_windy);
                            weather.addView(weatherIcon);
                        } else if (icon.equals("fog")) {
                            weatherIcon.setImageResource(R.drawable.ic_iconfinder_foggy);
                            weather.addView(weatherIcon);
                        } else if (icon.equals("cloudy")) {
                            weatherIcon.setImageResource(R.drawable.ic_iconfinder_cloudy);
                            weather.addView(weatherIcon);
                        } else if (icon.equals("partly-cloudy-day")) {
                            weatherIcon.setImageResource(R.drawable.ic_iconfinder_partly_cloudy_sunny);
                            weather.addView(weatherIcon);
                        } else if (icon.equals("partly-cloudy-night")) {
                            weatherIcon.setImageResource(R.drawable.ic_iconfinder_moony);
                            weather.addView(weatherIcon);
                        }
                        Log.d("APIAnswer", icon + temp);
                        weatherIcon.getLayoutParams().height=200;
                        weatherIcon.getLayoutParams().width=200;
                        LinearLayout weatherDesc=new LinearLayout(getContext());
                        weatherDesc.setOrientation(LinearLayout.VERTICAL);
                        TextView summaryText=new TextView(getContext());
                        summaryText.setText(summary+". Temperature "+temp+"C");
                        TextView dailyProbability=new TextView(getContext());
                        if(Integer.parseInt(rainWeek)>0.5)
                        {
                            dailyProbability.setText(Integer.parseInt(rainWeek)*100+"% chance of rain!");
                        }
                        else if(Integer.parseInt(rainWeek)<0.5&&Integer.parseInt(rainWeek)>0.2)
                        {
                            dailyProbability.setText("Small probability of rain!");
                        }
                        else{
                           dailyProbability.setText("Expect no rain!");
                        }




                        weatherDesc.addView(summaryText);
                        weatherDesc.addView(dailyProbability);
                        weatherDesc.setVerticalGravity(Gravity.CENTER);
                        weather.addView(weatherDesc);
                    }

                } catch (Exception e) {
                    Log.e("DarkSky :", e.toString());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e,
                                  JSONObject response) {

                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                Log.d("Bitcoin r", "Request fail! Status code: " + statusCode);
                Log.d("Bitcoin z", "Fail response: " + response);
                Log.e("ERROR", e.toString());
            }
        });
    }
    private boolean isOnline() {
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
        return inflater.inflate(R.layout.fragment_main, container, false);
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
