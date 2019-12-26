package com.nirmalhk7.nirmalhk7.entrydisplay;

import android.Manifest;
import androidx.room.Room;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.core.widget.TextViewCompat;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
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

import com.leinardi.android.speeddial.SpeedDialView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.examholidays.ExamholidaysDAO;
import com.nirmalhk7.nirmalhk7.examholidays.examHolidays;
import com.nirmalhk7.nirmalhk7.timetable.timetable;
import com.nirmalhk7.nirmalhk7.timetable.TimetableDAO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
    private String api_link;
    private AirLocation airLocation;
    public double longitude;
    public double latitude;

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
                        summaryText.setTextColor(getActivity().getResources().getColor(R.color.colorFontLight));
                        summaryText.setTextSize(15);
                        tempT.setTextColor(getActivity().getResources().getColor(R.color.colorFontLight));
                        tempT.setTextSize(12);
                        dailyProbability.setTextColor(getActivity().getResources().getColor(R.color.colorFontLight));
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
        assistantLocation = new File(getActivity().getFilesDir(), "snips");
        //extractAssistantIfNeeded(assistantLocation);
        if (ensurePermissions()) {
          //  startSnips(assistantLocation);
        }

        api_link = "https://api.darksky.net/forecast/60569b87b5b2a6220c135e9b2e91646b/";
        SpeedDialView dial=getActivity().findViewById(R.id.speedDial);
        dial.setVisibility(View.INVISIBLE);
        CardView das=v.findViewById(R.id.dailySchedule);
        das.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                Fragment newFragment;
                newFragment = new timetable();
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
                newFragment = new examHolidays();
                transaction.replace(R.id.fullscreen, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        weather = getActivity().findViewById(R.id.weather);
        if (isOnline()) {
            airLocation = new AirLocation(getActivity(), true, true, new AirLocation.Callbacks() {
                @Override
                public void onSuccess(Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.d("AirLocation", "Coordinates LA:" + latitude + " + LO:" + longitude);
                    api_link = api_link.concat(Double.toString(latitude) + "," + Double.toString(longitude));
                    api_link = api_link.concat("?units=si");
                    Log.d("WeatherAPI", api_link);
                    requestData(api_link, "weather",v);

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
                    Toast.makeText(getActivity(), "LA:"+latitude+" LO:"+longitude+". MR1",
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

        DBGateway database = Room.databaseBuilder(getContext(), DBGateway.class, "finalDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();
        TimetableDAO SCHDAO=database.getScheduleDao();
        ExamholidaysDAO EHDAO=database.getEHDAO();

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
            //startSnips(assistantLocation);
        }
    }

   /* private void startSnips(File snipsDir) {
        SnipsPlatformClient client = createClient(snipsDir);
        client.connect(this.getActivity().getApplicationContext());
    }

    private void extractAssistantIfNeeded(File assistantLocation) {
        File versionFile = new File(assistantLocation,
                "android_version_" + BuildConfig.VERSION_NAME);

        if (versionFile.exists()) {
            return;
        }

        try {
            assistantLocation.delete();
            unzip(getActivity().getBaseContext().getAssets().open("assistant.zip"),
                    assistantLocation);
            versionFile.createNewFile();
        } catch (IOException e) {
            return;
        }
    }
*/
  /*  private SnipsPlatformClient createClient(File assistantLocation) {
        File assistantDir  = new File(assistantLocation, "assistant");

        final SnipsPlatformClient client =
                new SnipsPlatformClient.Builder(assistantDir)
                        .enableDialogue(true)
                        .enableHotword(true)
                        .enableSnipsWatchHtml(false)
                        .enableLogs(true)
                        .withHotwordSensitivity(0.5f)
                        .enableStreaming(false)
                        .enableInjection(false)
                        .build();

        client.setOnPlatformReady(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                Log.d(TAG, "Snips is ready. Say the wake word!");
                return null;
            }
        });

        client.setOnPlatformError(
                new Function1<SnipsPlatformClient.SnipsPlatformError, Unit>() {
                    @Override
                    public Unit invoke(final SnipsPlatformClient.SnipsPlatformError
                                               snipsPlatformError) {
                        // Handle error
                        Log.d(TAG, "Error: " + snipsPlatformError.getMessage());
                        return null;
                    }
                });

        client.setOnHotwordDetectedListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                // Wake word detected, start a dialog session
                Log.d(TAG, "Wake word detected!");
                client.startSession(null, new ArrayList<String>(),
                        false, null);
                return null;
            }
        });

        client.setOnIntentDetectedListener(new Function1<IntentMessage, Unit>() {
            @Override
            public Unit invoke(final IntentMessage intentMessage) {
                // Intent detected, so the dialog session ends here
                client.endSession(intentMessage.getSessionId(), null);
                Log.d(TAG, "Intent detected: " +
                        intentMessage.getIntent().getIntentName());
                return null;
            }
        });

        client.setOnSnipsWatchListener(new Function1<String, Unit>() {
            public Unit invoke(final String s) {
                Log.d(TAG, "Log: " + s);
                return null;
            }
        });

        return client;
    }
*/
    private void unzip(InputStream zipFile, File targetDirectory)
            throws IOException {
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(zipFile));
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to make directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            }
        } finally {
            zis.close();
        }
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
                requestData(api_link, "weather",rootview);
                pullToRefresh.setRefreshing(false);
            }
        });
    }

}
