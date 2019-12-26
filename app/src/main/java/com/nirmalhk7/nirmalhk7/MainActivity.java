package com.nirmalhk7.nirmalhk7;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nirmalhk7.nirmalhk7.academics.Academics;
import com.nirmalhk7.nirmalhk7.attendance.AllSubjects;
import com.nirmalhk7.nirmalhk7.backuprestore.BackupRestore;
import com.nirmalhk7.nirmalhk7.callmanager.CallMgrFragment;
import com.nirmalhk7.nirmalhk7.cpschedule.CPSched;
import com.nirmalhk7.nirmalhk7.entrydisplay.MainFragment;
import com.nirmalhk7.nirmalhk7.examholidays.examHolidays;
import com.nirmalhk7.nirmalhk7.playground.Playground;
import com.nirmalhk7.nirmalhk7.settings.SettingsActivity;
import com.nirmalhk7.nirmalhk7.timetable.Timetable;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String res="HELLO";
        Log.d("CONVC",""+Converters.day_to_dayno(Converters.today_get("EEEE")));

        String curDate1 = "23 Jul 2019 Tue 1:30 PM";
        SimpleDateFormat curDateFormat = new SimpleDateFormat("dd MMM yyyy EEE hh:mm a");
        try {
            Date date = curDateFormat.parse(curDate1);
            Log.d("CONVERX",date.getTime()+"");
            Log.d("CONVERX",Converters.date_to_day(date)+ " xx "+Converters.date_to_t12(date)+" yy "+Converters.date_to_Dt(date));
            // you can use this date string now
        } catch (Exception e) {
            Log.e("ACTMAIN","TimeErr "+e.getMessage());
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
        NavigationView navigationView = findViewById(R.id.nav_view);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i=new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
            return true;
        }

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
        } else if (id == R.id.nav_manage) {
            Intent i=new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_schedule) {
            newFragment = new Timetable();
            transaction.replace(R.id.fullscreen, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_academics) {
            newFragment = new Academics();
            transaction.replace(R.id.fullscreen, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if(id==R.id.nav_callManager)
        {
            newFragment = new CallMgrFragment();
            transaction.replace(R.id.fullscreen, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id==R.id.nav_attendance)
        {
            newFragment = new AllSubjects();
            transaction.replace(R.id.fullscreen, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(id==R.id.nav_examholidays)
        {
            newFragment = new examHolidays();
            transaction.replace(R.id.fullscreen, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(id==R.id.nav_playground)
        {
            newFragment = new Playground();
            transaction.replace(R.id.fullscreen, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(id==R.id.nav_backup)
        {
            newFragment=new BackupRestore();
            transaction.replace(R.id.fullscreen, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(id==R.id.CPSched)
        {
            newFragment=new CPSched();
            transaction.replace(R.id.fullscreen,newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}