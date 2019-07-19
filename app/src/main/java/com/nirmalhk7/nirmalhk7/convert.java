package com.nirmalhk7.nirmalhk7;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class convert {
    public static String addrailtime(String railTime, int addTime) {
        //Input will be of form 2145
        String hh = railTime.substring(0, 2);
        String mm = railTime.substring(2, 4);
        Log.d("CONVERTX",hh+"+"+mm);
        int h = Integer.parseInt(hh);
        int m = Integer.parseInt(mm);
        Log.d("CONVERTX",h+"+"+m);
        m += addTime;
        if ((m + addTime) >= 60) {
            m =m- 60;
            h++;
            if (h == 23) {
                h = 0;
            }
        }
        hh=Integer.toString(h);
        mm=Integer.toString(m);

        String ansTime="";
        if((h==0)&&(m==0))
        {
            ansTime="0"+h+"0"+m;
        }
        else if((h==0)&&(m!=0))
        {
            ansTime="0"+h+m;
        }
        return ansTime;
    }

    public static String railtonormal(String railTime) {
        String day = "AM";
        String hh = railTime.substring(0, 2);
        int h = Integer.parseInt(hh);
        if (h > 12) {
            h -= 12;
            hh = Integer.toString(h);
            day = "PM";
        } else if (h == 12) {
            day = "PM";
        }
        String mm = railTime.substring(2, 4);
        String result = hh + ":" + mm + " " + day;

        return result;
    }

    public static String normaltorail(String normaltime) {
        String result;
        String hh = normaltime.substring(0, normaltime.indexOf(':'));
        String mm = normaltime.substring(normaltime.indexOf(':') + 1, normaltime.indexOf(' '));
        String day = normaltime.substring(normaltime.indexOf(' ') + 1);

        if (day.equals("PM")) {
            int h = Integer.parseInt(hh);
            h += 12;
            hh = Integer.toString(h);
        }
        result = hh + mm;
        return result;

    }
}
