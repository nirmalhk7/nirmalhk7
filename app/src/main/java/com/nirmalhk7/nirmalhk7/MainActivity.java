package com.nirmalhk7.nirmalhk7;

import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String weather_base = "https://api.darksky.net/forecast/60569b87b5b2a6220c135e9b2e91646b/";
    //12.9894,74.8006?units=si
    private TextView weatherTextView;
    public String apiRz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        weatherTextView=new TextView(getApplicationContext());
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        WeatherDisplay();

    }



    private boolean isOnline() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

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
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {

        } else if (id == R.id.nav_schedule) {

        } else if (id == R.id.nav_academics) {
            Intent i = new Intent(MainActivity.this, AcademicsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_academics) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void requestData(String url, final String field, final String query) {
        //Everything below is part of the Android Asynchronous HTTP Client
        
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d("Bitcoin", "JSON: " + response.toString());

                try {
                        apiRz = response.getJSONObject(field).getString(query);

                        LinearLayout weatherView=findViewById(R.id.weatherView);
                        ImageView iconWeather=new ImageView(getApplicationContext());
                        weatherView.addView(iconWeather);
                        iconWeather.setImageDrawable(getDrawable(R.drawable.ic_menu_camera));

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

                Toast.makeText(MainActivity.this, "Request Failed",
                        Toast.LENGTH_SHORT).show();
            }
            public String returnOP(String apiResult)
            {
                return apiResult;
            }
        });
    }
    public void WeatherDisplay()
    {
        if (isOnline()) {
            double longitude = 27.004;
            double latitude = 49.627;
            weather_base = weather_base.concat(Double.toString(longitude) + "," + Double.toString(latitude));
            weather_base = weather_base.concat("?units=si");
            Log.d("WeatherAPI",weather_base);

            requestData(weather_base, "currently", "icon");
            Log.d("APIAnswer",apiRz+" Hello");

        } else {
            LinearLayout weatherView=findViewById(R.id.weatherView);
            weatherView.addView(weatherTextView);
            weatherTextView.setText("Phone not connected");
        }
    }

}