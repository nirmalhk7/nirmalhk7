package com.nirmalhk7.nirmalhk7;

public class convert {
    public static String railtonormal(String railTime) {
        String result="0";
        if (railTime.length() == 3) {
            //Times like 1420: All Times after 12PM

            String a="0"+railTime;
            String hh=a.substring(0,2);
            String mm=a.substring(2,4);
            result=hh+":"+mm+" AM";
        }
        else if(railTime.length()==4)
        {
            String hh=railTime.substring(0,2);
            int h=Integer.parseInt(hh);
            if(h>12)
            {
                h-=12;
                hh=Integer.toString(h);
            }
            String mm=railTime.substring(2,4);
            result=hh+":"+mm+" PM";
        }
        return result;
    }
    public static String normaltorail(String normaltime)
    {
        String hh=normaltime.substring(0,normaltime.indexOf(':'));
        String mm=normaltime.substring(normaltime.indexOf(':')+1,normaltime.length());
        return hh;
    }
}
