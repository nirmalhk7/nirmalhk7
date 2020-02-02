package com.nirmalhk7.nirmalhk7.Fragments;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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
import androidx.core.app.NotificationCompat;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.leinardi.android.speeddial.SpeedDialView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nirmalhk7.nirmalhk7.Controllers.Converters;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.MainActivity;
import com.nirmalhk7.nirmalhk7.NotificationController;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.SplashActivity;
import com.nirmalhk7.nirmalhk7.common;
import com.nirmalhk7.nirmalhk7.model.ExamholidaysEntity;
import com.nirmalhk7.nirmalhk7.model.TimetableEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.Header;
import mumayank.com.airlocationlibrary.AirLocation;

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


    public void requestData(String url, final String category, final View rootview) {
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
                        Log.d(MODULE_TAG+"WeatherAPI", icon + "with" + temp);
                        weather = rootview.findViewById(R.id.weather);
                        ImageView weatherIcon = rootview.findViewById(R.id.weatherIcon);


                        if (icon.equals("clear-day")) {
                            weatherIcon.setImageResource(R.drawable.ic_iconfinder_sunny);
                        } else if (icon.equals("clear-night")) {
                            weatherIcon.setImageResource(R.drawable.ic_iconfinder_moony);
                             
                        } else if (icon.equals("rain")) {
                            weatherIcon.setImageResource(R.drawable.ic_iconfinder_rainy);
                            
                        } else if (icon.equals("wind")) {
                            weatherIcon.setImageResource(R.drawable.ic_iconfinder_windy);
                            
                        } else if (icon.equals("fog")) {
                            weatherIcon.setImageResource(R.drawable.ic_iconfinder_foggy);
                            
                        } else if (icon.equals("cloudy")) {
                            weatherIcon.setImageResource(R.drawable.ic_iconfinder_cloudy);
                            
                        } else if (icon.equals("partly-cloudy-day")) {
                            weatherIcon.setImageResource(R.drawable.ic_iconfinder_partly_cloudy_sunny);
                            
                        } else if (icon.equals("partly-cloudy-night")) {
                            weatherIcon.setImageResource(R.drawable.ic_iconfinder_moony);
                            
                        }
                        Log.d(MODULE_TAG+"WeatherAPI", icon + temp);

                        LinearLayout weatherDesc = rootview.findViewById(R.id.weatherDesc);

                        TextView summaryText = new TextView(getContext());
                        summaryText.setText(summary + "." );
                        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(summaryText,1,20,1,TypedValue.COMPLEX_UNIT_DIP);

                        TextView tempT=new TextView(getContext());
                        tempT.setText(temp+"C");
                        TextView dailyProbability = new TextView(getContext());
                        if (Float.parseFloat(rainWeek) > 0.5) {
                            dailyProbability.setText(Float.parseFloat(rainWeek) * 100 + "% chance of rain!");
                        } else if (Float.parseFloat(rainWeek) < 0.5 && Float.parseFloat(rainWeek) > 0.2) {
                            dailyProbability.setText("Small probability of rain!");
                        } else {
                            dailyProbability.setText("Expect no rain!");
                        }
                        dailyProbability.setAllCaps(true);
                        dailyProbability.setTextSize(12);
                        weatherDesc.addView(summaryText);
                        weatherDesc.addView(tempT);
                        weatherDesc.addView(dailyProbability);
                        summaryText.setTextSize(15);
                        tempT.setTextSize(12);
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
    private File assistantLocation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_main, container, false);
        Toolbar toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");
        common C=new common(getContext());

        Intent notificationIntent = new Intent(getContext(), TimetableFragment.class);
        notificationIntent.putExtra("NIRMALHK7", 1);
//        notificationIntent.putExtra(TimetableFragment.NOTIFICATION, notification);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        long futureInMillis = SystemClock.elapsedRealtime() + 50000;
//        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);


        assistantLocation = new File(getActivity().getFilesDir(), "snips");
        //extractAssistantIfNeeded(assistantLocation);
        if (ensurePermissions()) {
          //  startSnips(assistantLocation);
        }

        mAPILink = "https://api.darksky.net/forecast/60569b87b5b2a6220c135e9b2e91646b/";
        SpeedDialView dial=getActivity().findViewById(R.id.speedDial);
        dial.setVisibility(View.INVISIBLE);
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
        if (isOnline()) {
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
            i.setImageResource(R.drawable.ic_signal_wifi_off);
            display.setText("Phone is not connected");
            display.setTextColor(getActivity().getResources().getColor(R.color.colorFontLight));
            LinearLayout l=v.findViewById(R.id.weatherDesc);
            l.addView(display);

        }

        fillEntryDisplay(v);
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

        String CHANNEL_ID="channelID";
        Intent snoozeIntent= new Intent(getContext(), SplashActivity.class);
        snoozeIntent.setAction("Present");
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(getContext(), 0, snoozeIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_app)
                .setContentTitle("Demo Notification")
                .setChannelId(CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setContentText("Demo Content")
                .setColor(getResources().getColor(R.color.colorDark,getActivity().getTheme()))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_app,"Present",snoozePendingIntent)
                .addAction(R.drawable.ic_app,"Absent",snoozePendingIntent);

        NotificationController notificationManager=new NotificationController();
        notificationManager.createNotificationChannel(getContext(),CHANNEL_ID);
        notificationManager.showNotification(getContext(),0,builder);

        return v;
    }

    private void fillEntryDisplay(View v)
    {
        DBGateway database = DBGateway.getInstance(getContext());
        TimetableEntity timetableEntities=database.getTTDao().getScheduleByDayAndTime(
                Converters.day_to_dayno(
                        Converters.today_get("EEE")
                ),
                Converters.to_date(
                        Converters.today_get("hh:mm a"),
                        "hh:mm a"));
        ExamholidaysEntity exam=database.getEHDAO().getNextEvent(
                Converters.to_date(
                        Converters.today_get("dd MM yyyy"),
                        "dd MM yyyy"),
                1
        );
        ExamholidaysEntity holiday=database.getEHDAO().getNextEvent(
                Converters.to_date(
                        Converters.today_get("dd MM yyyy"),
                        "dd MM yyyy"),
                2
        );

        try{
            Log.d("CHECKX","Next "+timetableEntities.getTask());
            TextView nextClass=v.findViewById(R.id.nextClassName);
            nextClass.setText(timetableEntities.getTask());
            TextView nextTime=v.findViewById(R.id.nextClassTime);
            if(timetableEntities.getDay()!=Converters.day_to_dayno(Converters.today_get("EEE")))
            {
                TextView nextClassNotToday=v.findViewById(R.id.nextClassNotToday);
                nextClassNotToday.setText("Next Class ("+Converters.dayno_to_day(timetableEntities.getDay())+")");
            }
            nextTime.setText(Converters.date_to(timetableEntities.getStartTime(),"hh:mm a"));
        }catch (Exception e)
        {
            Log.e(getClass().getName(),e.getMessage());
            TextView nextClass=v.findViewById(R.id.nextClassName);
            nextClass.setText("Done for the day!");
        }
        try{
            Log.d("CHECKXE","Next "+exam.getmName());
            TextView nextExam=v.findViewById(R.id.nextExam);
            nextExam.setText(exam.getmName());
            TextView nextExamDate=v.findViewById(R.id.nextExamDate);
            nextExamDate.setText(Converters.date_to(exam.getStart(),"MMM dd"));
        }catch(NullPointerException e)
        {
            Log.e(getClass().getName(),e.getMessage());
        }
        try{
            Log.d("CHECKXH","Next "+holiday.getmName());
            TextView nextHoliday=v.findViewById(R.id.nextHoliday);
            nextHoliday.setText(holiday.getmName());
            TextView nextHolidayDate=v.findViewById(R.id.nextHolidayDate);
            nextHolidayDate.setText(Converters.date_to(holiday.getStart(),"MMM dd"));
        }catch(NullPointerException e)
        {
            Log.e(getClass().getName(),e.getMessage());
        }
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
                requestData(mAPILink, "weather",rootview);
                fillEntryDisplay(rootview);
                pullToRefresh.setRefreshing(false);
            }
        });
    }

}
