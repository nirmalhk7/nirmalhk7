package com.nirmalhk7.nirmalhk7.Fragments;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.leinardi.android.speeddial.SpeedDialView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nirmalhk7.nirmalhk7.Controllers.MainFragmentController;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.common;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import mumayank.com.airlocationlibrary.AirLocation;

import static android.content.Context.MODE_PRIVATE;

//import static androidx.constraintlayout.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    //  Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final String MODULE_TAG="END/";

    //  Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mAPILink;
    private AirLocation mAirLocation;
    public double mLongitude;
    public double mLatitude;

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
    //  Rename and change types and number of parameters
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

    }

    private MainFragmentController mainFragmentController;
    private void requestData(String url, final String category, final View rootview) {
        //Everything below is part of the Android Asynchronous HTTP Client

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d(MODULE_TAG+"WeatherAPI", "JSON: " + response.toString());

                try {
                    if (category.equals("weather")) {

                        String icon = response.getJSONObject("currently").getString("icon");
                        String temp = response.getJSONObject("currently").getString("temperature");
                        String summary = response.getJSONObject("currently").getString("summary");
                        String rainWeek = response.getJSONObject("currently").getString("precipProbability");
                        try{
                            SharedPreferences sharedPref = getActivity().getPreferences(MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("icon", icon);
                            editor.putString("temp", temp);
                            editor.putString("summary", summary);
                            editor.putString("rainWeek", rainWeek);
                            editor.apply();
                        }catch (NullPointerException e)
                        {
                            Log.d(getClass().getName(),e.getMessage());
                        }


                        Log.d(MODULE_TAG+"WeatherAPI", icon + "with" + temp);
                        ImageView weatherIcon = rootview.findViewById(R.id.weatherIcon);
                        LinearLayout weatherDesc = rootview.findViewById(R.id.weatherDesc);

                        mainFragmentController.setWeather(icon,temp,summary,rainWeek,weatherIcon,weatherDesc);
                        Log.d(MODULE_TAG+"END",weatherDesc.getChildCount()+" Count");
                    }

                } catch (JSONException e) {
                    Log.e(MODULE_TAG+"DarkSky :", e.toString());
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_main, container, false);
        Toolbar toolbar=getActivity().findViewById(R.id.toolbar);
        SharedPreferences sharedPref = getActivity().getPreferences(MODE_PRIVATE);

        toolbar.setTitle("Dashboard");
        common C=new common(getContext());
        mainFragmentController=new MainFragmentController(getContext(),v,getActivity());
        mAPILink = "https://api.darksky.net/forecast/60569b87b5b2a6220c135e9b2e91646b/";

//        Intent notificationIntent = new Intent(getContext(), TimetableFragment.class);
//        notificationIntent.putExtra("NIRMALHK7", 1);
//        notificationIntent.putExtra(TimetableFragment.NOTIFICATION, notification);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        long futureInMillis = SystemClock.elapsedRealtime() + 50000;
//        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);

        mainFragmentController.setSpeedDial((SpeedDialView)getActivity().findViewById(R.id.speedDial));

        CardView das=v.findViewById(R.id.dailySchedule);
        das.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                Fragment newFragment;
                newFragment = new TimetableFragment();
                transaction.replace(R.id.fullscreen, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        CardView eah=v.findViewById(R.id.examHolidayNext);
        eah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                Fragment newFragment;
                newFragment = new ExamHolidayFragment();
                transaction.replace(R.id.fullscreen, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        weather = getActivity().findViewById(R.id.weather);
        if (mainFragmentController.isOnline()) {
            mAirLocation = new AirLocation(getActivity(), true, true, new AirLocation.Callbacks() {
                @Override
                public void onSuccess(Location location) {
                    mLatitude = location.getLatitude();
                    mLongitude = location.getLongitude();
                    Log.d("AirLocation", "Coordinates LA:" + mLatitude + " + LO:" + mLongitude);
                    mAPILink = mAPILink.concat(mLatitude + "," + mLongitude);
                    mAPILink = mAPILink.concat("?units=si");
                    Log.d("WeatherAPI", mAPILink);
                    requestData(mAPILink, "weather",v);

                }

                @Override
                public void onFailed(AirLocation.LocationFailedEnum locationFailedEnum) {
                    Log.e("AirLocation", "Location untraceable");
                }
            });
            LinearLayout weather=v.findViewById(R.id.weather);
            weather.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "LA:"+ mLatitude +" LO:"+ mLongitude +". MR1",
                            Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Log.d(MODULE_TAG+"API","No Internet connection");
            TextView display=new TextView(getContext());
            ImageView i=v.findViewById(R.id.weatherIcon);
            LinearLayout l=v.findViewById(R.id.weatherDesc);
            SharedPreferences sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
            String icon=sharedPreferences.getString("icon","null");
            String temp=sharedPreferences.getString("temp","null");
            String summary=sharedPreferences.getString("summary","null")+" (Disconnected)";
            String rainWeek=sharedPreferences.getString("rainWeek","null");
            mainFragmentController.setWeather(icon,temp,summary,rainWeek,i,l);
        }
        TextView nextClass=v.findViewById(R.id.nextClassName);
        TextView nextTime=v.findViewById(R.id.nextClassTime);
        TextView nextExam=v.findViewById(R.id.nextExam);
        TextView nextExamDate=v.findViewById(R.id.nextExamDate);
        TextView nextHoliday=v.findViewById(R.id.nextHoliday);
        TextView nextHolidayDate=v.findViewById(R.id.nextHolidayDate);
        mainFragmentController.fillEntryDisplay(nextClass,nextTime,nextExam,nextHoliday,nextExamDate,nextHolidayDate);
        return v;
    }




    private boolean ensurePermissions() {
        int status = ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.RECORD_AUDIO);
        if (status != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 0);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {


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

    SwipeRefreshLayout pullToRefresh;
    ArrayAdapter adapter;
    public void DSLonRefresh(final View rootview){
        pullToRefresh = rootview.findViewById(R.id.pullToRefresh);
        final View v=rootview;
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                Log.d("DAS/DSL","Refreshing");
                pullToRefresh.setRefreshing(false);
            }
        });
    }

}
