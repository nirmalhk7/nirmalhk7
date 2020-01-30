package com.nirmalhk7.nirmalhk7.Controllers;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.TextViewCompat;

import com.leinardi.android.speeddial.SpeedDialView;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.model.ExamholidaysEntity;
import com.nirmalhk7.nirmalhk7.model.TimetableEntity;

public class MainFragmentController {
    private Context mContext;
    private View mRootView;
    private String mAPILink;
    private Activity mActivity;
    private DBGateway database;
    private double mLatitude,mLongitude;
    public MainFragmentController(Context context,View view,Activity activity) {
        mContext = context;
        mRootView=view;
        mActivity=activity;
        database = DBGateway.getInstance(mContext);
    }
    public void setSpeedDial(SpeedDialView speedDial){
        speedDial.setVisibility(View.INVISIBLE);
    }
    public void fillEntryDisplay(TextView nextClass,TextView nextTime,TextView nextExam,TextView nextHoliday,TextView nextExamDate,TextView nextHolidayDate)
    {
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
            nextClass.setText(timetableEntities.getTask());
            nextTime.setText(Converters.date_to(timetableEntities.getStartTime(),"hh:mm a"));
        }catch (NullPointerException e)
        {
            Log.e(getClass().getName(),e.getMessage());
            nextClass.setText("Done for the day!");
        }
        try{
            Log.d("CHECKXE","Next "+exam.getmName());
            nextExam.setText(exam.getmName());
            nextExamDate.setText(Converters.date_to(exam.getStart(),"MMM dd"));
        }catch(NullPointerException e)
        {
            Log.e(getClass().getName(),e.getMessage());
        }
        try{
            Log.d("CHECKXH","Next "+holiday.getmName());
            nextHoliday.setText(holiday.getmName());
            nextHolidayDate.setText(Converters.date_to(holiday.getStart(),"MMM dd"));
        }catch(NullPointerException e)
        {
            Log.e(getClass().getName(),e.getMessage());
        }
    }
    public boolean isOnline() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) mActivity.getSystemService(mActivity.getBaseContext().CONNECTIVITY_SERVICE);
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
//    public void getWeather(String APILink){
//        mAPILink=APILink;
//        if (isOnline()) {
//
//            AirLocation mAirLocation = new AirLocation(mActivity, true, true, new AirLocation.Callbacks() {
//                @Override
//                public void onSuccess(Location location) {
//                    mLatitude = location.getLatitude();
//                    mLongitude = location.getLongitude();
//                    Log.d("AirLocation", "Coordinates LA:" + mLatitude + " + LO:" + mLongitude);
//                    mAPILink = mAPILink.concat(mLatitude + "," + mLongitude);
//                    mAPILink = mAPILink.concat("?units=si");
//                    Log.d("WeatherAPI", mAPILink);
//                    requestData(mAPILink, "weather",mRootView);
//                }
//
//                @Override
//                public void onFailed(AirLocation.LocationFailedEnum locationFailedEnum) {
//                    Log.e("AirLocation", "Location untraceable");
//                }
//            });
//            LinearLayout weather=mRootView.findViewById(R.id.weather);
//            weather.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View mRootView) {
//                    Toast.makeText(mActivity, "LA:"+ mLatitude +" LO:"+ mLongitude +". MR1",
//                            Toast.LENGTH_SHORT).show();
//                }
//            });
//
//
//        } else {
//            Log.d(getClass().getName()+"API","No Internet connection");
//            TextView display=new TextView(mContext);
//            ImageView i=mRootView.findViewById(R.id.weatherIcon);
//            i.setImageResource(R.drawable.ic_signal_wifi_off);
//            display.setText("Phone is not connected");
//            display.setTextColor(mActivity.getResources().getColor(R.color.colorFontLight));
//            LinearLayout l=mRootView.findViewById(R.id.weatherDesc);
//            l.addView(display);
//
//        }
//    }
//
//    public void requestData(String url, final String category, final View rootview) {
//        //Everything below is part of the Android Asynchronous HTTP Client
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(url, new JsonHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers,
//                                  JSONObject response) {
//                // called when response HTTP status is "200 OK"
//                Log.d(getClass().getName()+"WeatherAPI", "JSON: " + response.toString());
//
//                try {
//                    if (category.equals("weather")) {
//
//                        MainFragment.icon = response.getJSONObject("currently").getString("icon");
//                        MainFragment.temp = response.getJSONObject("currently").getString("temperature");
//                        MainFragment.summary = response.getJSONObject("currently").getString("summary");
//                        MainFragment.rainWeek = response.getJSONObject("currently").getString("precipProbability");
//                        Log.d(getClass().getName()+"WeatherAPI", MainFragment.icon + "with" + MainFragment.temp);
//
//                    }
//
//                } catch (JSONException e) {
//                    Log.e(getClass().getName()+"DarkSky :", e.toString());
//                }
//
//            }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable e,
//                                  JSONObject response) {
//
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//
//                Log.d("Bitcoin r", "Request fail! Status code: " + statusCode);
//                Log.d("Bitcoin z", "Fail response: " + response);
//                Log.e("ERROR", e.toString());
//            }
//        });
//
//    }
    public void setWeather(String icon,String temp,String summary,String rainWeek,ImageView weatherIcon, LinearLayout weatherDesc){
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
        Log.d(getClass().getName()+"WeatherAPI", icon + temp);



        TextView summaryText = new TextView(mContext);
        summaryText.setText(summary + "." );
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(summaryText,1,20,1, TypedValue.COMPLEX_UNIT_DIP);

        TextView tempT=new TextView(mContext);
        tempT.setText(temp+"C");
        TextView dailyProbability = new TextView(mContext);
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
        summaryText.setTextColor(mActivity.getResources().getColor(R.color.colorFontLight));
        summaryText.setTextSize(15);
        tempT.setTextColor(mActivity.getResources().getColor(R.color.colorFontLight));
        tempT.setTextSize(12);
        dailyProbability.setTextColor(mActivity.getResources().getColor(R.color.colorFontLight));
    }

}
