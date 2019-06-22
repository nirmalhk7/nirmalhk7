package com.nirmalhk7.nirmalhk7;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nirmalhk7.nirmalhk7.academics.Academics;
import com.nirmalhk7.nirmalhk7.callmanager.callManagerFragment;
import com.nirmalhk7.nirmalhk7.dailyscheduler.DailySchedule;
import com.nirmalhk7.nirmalhk7.dailyscheduler.DailyScheduleList;
import com.nirmalhk7.nirmalhk7.settings.SettingsActivity;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.hide();
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
            newFragment = new DailySchedule();
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
            newFragment = new callManagerFragment();
            transaction.replace(R.id.fullscreen, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(id==R.id.nav_tsks)
        {
            newFragment = new DailyScheduleList();
            transaction.replace(R.id.fullscreen, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}