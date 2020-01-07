package com.nirmalhk7.nirmalhk7.util;

import android.util.Log;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class converter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static String dtConverter(String t24,String convertFrom,String convertTo)
    {
        SimpleDateFormat curDateFormat = new SimpleDateFormat(convertFrom);
        SimpleDateFormat desiredDateFormat = new SimpleDateFormat(convertTo);
        String t12="";
        try {
            Date date = curDateFormat.parse(t24);
            t12 = desiredDateFormat.format(date);
        } catch (ParseException e) {
            Log.e("CONVERTERS","TimeErr x");
        }
        return t12;
    }

    public static Date to_date(String d,String simpledtformat)
    {
        SimpleDateFormat curDateFormat = new SimpleDateFormat(simpledtformat);
        Date date=new Date();
        try {
            date = curDateFormat.parse(d);

        } catch (ParseException e) {
            Log.e("ACTMAIN","TimeErr TDMYT "+e.getMessage());
        }
        return date;
    }

    public static String date_to(Date d,String simpledtformat)
    {
        SimpleDateFormat df= new SimpleDateFormat(simpledtformat);
        String ans="";
        ans=df.format(d);
        return ans;
    }
    public static String dayno_to_day(int x)
    {
        switch(x)
        {
            case 0:return "Monday";
            case 1:return "Tuesday";
            case 2:return "Wednesday";
            case 3:return "Thursday";
            case 4:return "Friday";
            case 5:return "Saturday";
            default: return null;
        }
    }
    public static int day_to_dayno(String x)
    {
        if(x.matches("Mon"))
            return 0;
        else if(x.matches("Tues"))
            return 1;
        else if(x.matches("Wed"))
            return 2;
        else if(x.matches("Thu"))
            return 3;
        else if(x.matches("Fri"))
            return 4;
        else if(x.matches("Sat"))
            return 5;
        return Integer.MIN_VALUE;
    }
    public static String today_get(String format)
    {
        Date d=new Date(Calendar.getInstance().getTimeInMillis());
        return date_to(d,format)+"";
    }
    public static Date time_add(Date time1,int secondsToAdd)
    {
        Long timestamp=dateToTimestamp(time1)+secondsToAdd;
        return fromTimestamp(timestamp);
    }


}
