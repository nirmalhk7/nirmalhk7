package com.nirmalhk7.nirmalhk7.utility;

import android.util.Log;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static String t24_to_t12(String t24)
    {
        SimpleDateFormat curDateFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat desiredDateFormat = new SimpleDateFormat("hh:mm a");
        String t12="";
        try {
            Date date = curDateFormat.parse(t24);
            t12 = desiredDateFormat.format(date);
        } catch (Exception e) {
            Log.e("CONVERTERS","TimeErr");
        }
        return t12;
    }

    public static String dtConverter(String t24,String convertFrom,String convertTo)
    {
        SimpleDateFormat curDateFormat = new SimpleDateFormat(convertFrom);
        SimpleDateFormat desiredDateFormat = new SimpleDateFormat(convertTo);
        String t12="";
        try {
            Date date = curDateFormat.parse(t24);
            t12 = desiredDateFormat.format(date);
        } catch (Exception e) {
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

        } catch (Exception e) {
            Log.e("ACTMAIN","TimeErr TDMYT "+e.getMessage());
        }
        return date;
    }

    public static String date_to(Date d,String simpledtformat)
    {
        SimpleDateFormat df= new SimpleDateFormat(simpledtformat);
        String ans="";
        try{
            ans=df.format(d);
        }catch (Exception e)
        {
            Log.e("CONVERTERS","Excpn "+e.getMessage());
        }
        return ans;
    }

    public static String date_to_t12(Date d)
    {
        SimpleDateFormat desiredFormat = new SimpleDateFormat("hh:mm a");
        String t12="";
        try{
            t12=desiredFormat.format(d);
        }catch (Exception e)
        {
            Log.e("CONVERTERS","TimeErr T12");
        }
        return t12;
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
        if(x.matches("Monday"))
            return 0;
        else if(x.matches("Tuesday"))
            return 1;
        else if(x.matches("Wednesday"))
            return 2;
        else if(x.matches("Thursday"))
            return 3;
        else if(x.matches("Friday"))
            return 4;
        else if(x.matches("Saturday"))
            return 5;
        return Integer.MAX_VALUE;
    }
    public static String today_get(String format)
    {
        Date d=new Date(Calendar.getInstance().getTimeInMillis());
        return date_to(d,format);
    }



}
