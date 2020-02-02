package com.nirmalhk7.nirmalhk7.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import com.nirmalhk7.nirmalhk7.Controllers.Converters;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.model.ExamholidaysEntity;
import com.nirmalhk7.nirmalhk7.model.TimetableEntity;

/**
 * Implementation of App Widget functionality.
 */
public class Main extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
        DBGateway dbGateway=DBGateway.getInstance(context);
        int today_no= Converters.day_to_dayno(Converters.today_get("EEE"));
        String time=Converters.today_get("hh:mm a");
        TimetableEntity timetableEntity=dbGateway.getTTDao().getScheduleByDayAndTime(0,Converters.to_date(time,"hh:mm a"));
        ExamholidaysEntity exam=dbGateway.getEHDAO().getNextEvent(
                Converters.to_date(
                        Converters.today_get("dd MM yyyy"),
                        "dd MM yyyy"),
                1
        );
        ExamholidaysEntity holiday=dbGateway.getEHDAO().getNextEvent(
                Converters.to_date(
                        Converters.today_get("dd MM yyyy"),
                        "dd MM yyyy"),
                2
        );
        try{
            Log.d("CHECKX","Next "+timetableEntity.getTask());
            views.setTextViewText(R.id.widgetClassName,timetableEntity.getTask());
            views.setTextViewText(R.id.widgetClassTime,Converters.date_to(timetableEntity.getStartTime(),"hh:mm a"));
            views.setTextViewText(R.id.widgetClassAttendance,"ATTENDANCE");
        }catch (Exception e)
        {
            Log.e("WIDGET",e.getMessage());
            views.setTextViewText(R.id.widgetClassName,"No More Classes!");
        }
        try{
            Log.d("CHECKXE","Next "+exam.getmName());
            views.setTextViewText(R.id.widgetExamName,exam.getmName());
            views.setTextViewText(R.id.widgetExamDate,Converters.date_to(exam.getStart(),"MMM dd"));
        }catch(NullPointerException e)
        {
            Log.e("WIDGET",e.getMessage());
        }
        try{
            views.setTextViewText(R.id.widgetExamName,holiday.getmName());
            views.setTextViewText(R.id.widgetExamDate,Converters.date_to(holiday.getStart(),"MMM dd"));
        }catch(NullPointerException e)
        {
            Log.e("WIDGET",e.getMessage());
        }
        //views.setTextViewText(R.id.appwidget_text, widgetText);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

