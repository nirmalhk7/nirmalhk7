package com.nirmalhk7.nirmalhk7;

import androidx.room.TypeConverter;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static Date t12_to_date(String t12)
    {
        SimpleDateFormat curDateFormat = new SimpleDateFormat("hh:mm a");
        Date d=new Date();
        try{
            d= curDateFormat.parse(t12);

        } catch (ParseException e)
        {
            Log.e("CONVERTERS","ParseExcepn "+e);
        }
        return d;
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
    public static String date_to_day(Date d)
    {
        SimpleDateFormat desiredFormat=new SimpleDateFormat("EEE");
        String day="";
        try{
            day=desiredFormat.format(d);

        }catch(Exception e)
        {
            Log.e("CONVERTERS","Timeerr td");
        }
        return day;
    }

    public static String date_to_Dt(Date d)
    {
        SimpleDateFormat desiredFormat=new SimpleDateFormat("dd MMM yyyy");
        String day="";
        try{
            day=desiredFormat.format(d);

        }catch(Exception e)
        {
            Log.e("CONVERTERS","Timeerr td");
        }
        return day;
    }
    public static Date dmy_to_date(String ddmmyy)
    {
        SimpleDateFormat curDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Date date=new Date();
        try {
            date = curDateFormat.parse(ddmmyy);

        } catch (Exception e) {
            Log.e("ACTMAIN","TimeErr TDMYT "+e.getMessage());
        }
        return date;
    }

    public static Date dmyt_to_date(String ddmmyytt)
    {
        SimpleDateFormat curDateFormat = new SimpleDateFormat("dd MMMM yyyy hh:mm a");
        Date date=new Date();
        try {
            date = curDateFormat.parse(ddmmyytt);

        } catch (Exception e) {
            Log.e("ACTMAIN","TimeErr TDMYT");
        }
        return date;
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



}
