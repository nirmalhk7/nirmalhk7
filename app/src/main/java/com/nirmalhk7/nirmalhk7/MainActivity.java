package com.nirmalhk7.nirmalhk7;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.nirmalhk7.nirmalhk7.Controllers.Converters;
import com.nirmalhk7.nirmalhk7.Fragments.AttendanceFragment;
import com.nirmalhk7.nirmalhk7.Fragments.CallManagerFragment;
import com.nirmalhk7.nirmalhk7.Fragments.CpScheduleFragment;
import com.nirmalhk7.nirmalhk7.Fragments.ExamHolidayFragment;
import com.nirmalhk7.nirmalhk7.Fragments.MainFragment;
import com.nirmalhk7.nirmalhk7.Fragments.TimetableFragment;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date today= Converters.to_date(Converters.today_get("dd MMM yyyy"),"dd MMM yyyy");
        Calendar cal=Calendar.getInstance();
        cal.setTime(today);
        Log.d("CHECX",cal.getTime()+" TODYA");
        DBGateway database=DBGateway.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.versionName);
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            navUsername.setText(version+" ("+getString(R.string.gitBranch)+")");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        /*speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.nav_attendance, R.drawable.ic_attendance)
                        .setLabelColor(Color.WHIT
                        .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorLightDark, getTheme()))
                        .create()

        );*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.nav_dashboard, 0);
        }
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

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent i=new Intent(MainActivity.this, SettingsActivity.class);
//            startActivity(i);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        Fragment newFragment;
        if (id == R.id.nav_dashboard) {

            newFragment = new MainFragment();
            transaction.replace(R.id.fullscreen, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_schedule) {
            newFragment = new TimetableFragment();
            transaction.replace(R.id.fullscreen, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id==R.id.nav_callManager)
        {
            newFragment = new CallManagerFragment();
            transaction.replace(R.id.fullscreen, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id==R.id.nav_attendance)
        {
            newFragment = new AttendanceFragment();
            transaction.replace(R.id.fullscreen, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(id==R.id.nav_examholidays)
        {
            newFragment = new ExamHolidayFragment();
            transaction.replace(R.id.fullscreen, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(id==R.id.CPSched)
        {
            newFragment=new CpScheduleFragment();
            transaction.replace(R.id.fullscreen,newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}