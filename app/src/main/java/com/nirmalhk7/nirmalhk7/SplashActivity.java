package com.nirmalhk7.nirmalhk7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.nirmalhk7.nirmalhk7.dailyscheduler.DailySchedule.MODULE_TAG;

public class SplashActivity extends AppCompatActivity {
    public String WEATHER_LINK = "https://api.darksky.net/forecast/60569b87b5b2a6220c135e9b2e91646b/";

    public String rainWeek, summary, temp, icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_LINK, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d(MODULE_TAG+"WeatherAPI", "JSON: " + response.toString());

                try {



                    icon = response.getJSONObject("currently").getString("icon");

                    temp = response.getJSONObject("currently").getString("temperature");

                    summary = response.getJSONObject("currently").getString("summary");

                    rainWeek = response.getJSONObject("currently").getString("precipProbability");
                    Log.d(MODULE_TAG+"WeatherAPI", icon + "with" + temp);

                    Log.d("Splash/WeatherAPI", icon + temp+rainWeek+summary);

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

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("icon",icon);
        intent.putExtra("rainWeek",rainWeek);
        intent.putExtra("temp",temp);
        intent.putExtra("summary",summary);
        startActivity(intent);

        finish();
    }
}
