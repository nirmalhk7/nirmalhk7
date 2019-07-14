package com.nirmalhk7.nirmalhk7;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class convert {
    public static String railtonormal(String railTime) {
        String day="AM";
        String hh = railTime.substring(0, 2);
        int h = Integer.parseInt(hh);
        if (h > 12) {
            h -= 12;
            hh = Integer.toString(h);
            day="PM";
        }
        else if(h==12)
        {
            day="PM";
        }
        String mm = railTime.substring(2, 4);
        String result = hh + ":" + mm +" "+day ;

        return result;
    }

    public static String normaltorail(String normaltime) {
        String result;
        String hh = normaltime.substring(0, normaltime.indexOf(':'));
        String mm = normaltime.substring(normaltime.indexOf(':') + 1, normaltime.indexOf(' '));
        String day = normaltime.substring(normaltime.indexOf(' ') + 1, normaltime.length());

        if (day.equals("PM")) {
            int h = Integer.parseInt(hh);
            h += 12;
            hh = Integer.toString(h);
        }
        result = hh + mm;
        return result;

    }
}
