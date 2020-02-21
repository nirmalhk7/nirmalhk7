package com.nirmalhk7.nirmalhk7.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.nirmalhk7.nirmalhk7.Controllers.Converters;
import com.nirmalhk7.nirmalhk7.Controllers.WidgetController;
import com.nirmalhk7.nirmalhk7.R;

public class WidgetService extends Service {
    public WidgetService() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int serv= Service.START_NOT_STICKY;
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.main);
        //view.setTextViewText(R.id.txt_widget, time);

        WidgetController widgetController=new WidgetController(this);
        String[] exam=widgetController.getEventDetails(1);
        String[] holiday=widgetController.getEventDetails(2);
        String[] classDetails=widgetController.getClassDetails();
        if(classDetails[0]!=null)
        {
            float attPerc=widgetController.getAttendance(classDetails[0]);
            if(attPerc!=-1000)
                views.setTextViewText(R.id.widgetClassAttendance,"Attendance "+attPerc+"%");
        }

        views.setTextViewText(R.id.widgetLastUp, Converters.today_get("hh:mm a"));
        views.setTextViewText(R.id.widgetClassName,classDetails[0]);
        views.setTextViewText(R.id.widgetClassTime,classDetails[1]);
        views.setTextViewText(R.id.widgetClassAttendance,classDetails[2]);
        views.setTextViewText(R.id.widgetExamName,exam[0]);
        if(exam[0].equals("No more exams!"))
        {
            views.setTextViewTextSize(R.id.widgetExamName, TypedValue.COMPLEX_UNIT_SP,20);
        }
        views.setTextViewText(R.id.widgetExamDate,exam[1]);
        views.setTextViewText(R.id.widgetHolidayName,holiday[0]);
        if(holiday[0].equals("No more holidays."))
        {
            views.setTextViewTextSize(R.id.widgetExamName, TypedValue.COMPLEX_UNIT_SP,20);
        }
        views.setTextViewText(R.id.widgetHolidayTime,holiday[1]);
        ComponentName theWidget = new ComponentName(this, Main.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(theWidget, views);
        Log.d("SERVICE","SET");
        Toast.makeText(this,"Your widget has been refreshed!",Toast.LENGTH_SHORT);
        return serv;
    }
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
