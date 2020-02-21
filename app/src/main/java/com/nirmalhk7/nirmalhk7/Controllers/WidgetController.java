package com.nirmalhk7.nirmalhk7.Controllers;

import android.content.Context;
import android.util.Log;

import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.model.AttendanceEntity;
import com.nirmalhk7.nirmalhk7.model.ExamholidaysEntity;
import com.nirmalhk7.nirmalhk7.model.TimetableEntity;

import java.util.Date;

public class WidgetController {
    private ExamholidaysEntity exam;
    private DBGateway dbGateway;
    public WidgetController(Context context){
        dbGateway = DBGateway.getInstance(context);


//        try{
//            Log.d("CHECKX","Next "+timetableEntity.getTask());
//            views.setTextViewText(R.id.widgetClassName,timetableEntity.getTask());
//            views.setTextViewText(R.id.widgetClassTime,Converters.date_to(timetableEntity.getStartTime(),"hh:mm a"));
//            views.setTextViewText(R.id.widgetClassAttendance,"ATTENDANCE");
//        }catch (Exception e)
//        {
//            Log.e("WIDGET",e.getMessage());
//            views.setTextViewText(R.id.widgetClassName,"No More Classes!");
//        }
//        try{
//            Log.d("CHECKXE","Next "+exam.getmName());
//            views.setTextViewText(R.id.widgetExamName,exam.getmName());
//            views.setTextViewText(R.id.widgetExamDate,Converters.date_to(exam.getStart(),"MMM dd"));
//        }catch(NullPointerException e)
//        {
//            Log.e("WIDGET",e.getMessage());
//        }
//        try{
//            views.setTextViewText(R.id.widgetExamName,holiday.getmName());
//            views.setTextViewText(R.id.widgetExamDate,Converters.date_to(holiday.getStart(),"MMM dd"));
//        }catch(NullPointerException e)
//        {
//            Log.e("WIDGET",e.getMessage());
//        }
    }

    public String[] getEventDetails(int holexa){
        String[] ans=new String[2];
        Date today=Converters.to_date(
                Converters.today_get("dd MM yyyy"),
                "dd MM yyyy"
        );
        try{
            ExamholidaysEntity examholidaysEntity=dbGateway.getEHDAO().getNextEvent(today,holexa);
            ans[0]=examholidaysEntity.getmName();
            ans[1]=Converters.date_to(examholidaysEntity.getStart(),"MMM dd");
        }
        catch(NullPointerException e){
            ans[0]="No more ";
            if(holexa==1){
                ans[0]+="exams!";
            }else {
                ans[0] += "holidays.";
            }
            ans[1]="";
        }
        return ans;
    }
    public float getAttendance(String subjName){
        AttendanceEntity attendanceEntity =dbGateway.getATTDao().getSubjectbyName(subjName);
        try{
            int present=attendanceEntity.getPresent();
            int absent=attendanceEntity.getAbsent();
            float perc=(float)present/(present+absent);
            return perc;
        }
        catch(Exception e){
            Log.d("WIDGETSERVICE",e.getMessage());
        }
        return -1000;

    }
    public String[] getClassDetails(){
        String[] ans=new String[3];
        String time=Converters.today_get("hh:mm a");
        TimetableEntity timetableEntity = dbGateway.getTTDao().getScheduleByDayAndTime(0,Converters.to_date(time,"hh:mm a"));
        exam = dbGateway.getEHDAO().getNextEvent(
                Converters.to_date(
                        Converters.today_get("dd MM yyyy"),
                        "dd MM yyyy"),
                1
        );
        try{
            Log.d("CHECKX","Next "+timetableEntity.getTask());
            ans[0]=timetableEntity.getTask();
            ans[1]=Converters.date_to(timetableEntity.getStartTime(),"hh:mm a");
            ans[2]="ATTENDANCE";
        }catch (Exception e)
        {
            Log.e("WIDGET",e.getMessage());
            ans[0]="No more classes!";
        }
        return ans;
    }
}
