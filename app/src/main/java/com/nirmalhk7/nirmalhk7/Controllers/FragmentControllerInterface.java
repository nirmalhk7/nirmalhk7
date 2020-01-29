package com.nirmalhk7.nirmalhk7.Controllers;

import android.content.res.Resources;
import android.widget.ListView;

import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.leinardi.android.speeddial.SpeedDialView;

public interface FragmentControllerInterface {
    void swipeToRefresh(final SwipeRefreshLayout pullToRefresh);
    void speedDialOnClick(SpeedDialView speed, final FragmentManager fragmentManager);
    void addSpeedDialOptions(SpeedDialView speed, Resources resources, Resources.Theme theme);
    void attachAdapter(ListView listView);
}
