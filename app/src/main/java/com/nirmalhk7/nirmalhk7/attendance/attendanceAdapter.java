package com.nirmalhk7.nirmalhk7.attendance;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nirmalhk7.nirmalhk7.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class attendanceAdapter extends ArrayAdapter<attendanceItem> {

    /**
     * Resource ID for the background color for this list of words
     */
    private int kSubject;

    public attendanceAdapter(Context context, ArrayList<attendanceItem> AttendanceItem) {
        super(context, 0, AttendanceItem);
        kSubject=0;
    }

    public attendanceAdapter(Context context, ArrayList<attendanceItem> SubjectItem, int key) {
        super(context, 0, SubjectItem);
        kSubject = key;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(kSubject==1)
        {
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.attendance_subject_list_item, parent, false);
            }

            // Get the {@link attendanceItem} object located at this position in the list
            attendanceItem currentWord = getItem(position);

            // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
            TextView subjName_subj = (TextView) listItemView.findViewById(R.id.subjName_subject);
            // Get the Miwok translation from the currentWord object and set this text on
            // the Miwok TextView.
            subjName_subj.setText(currentWord.getSubjName());

            // Find the TextView in the list_item.xml layout with the ID default_text_view.
            TextView presentCount = (TextView) listItemView.findViewById(R.id.presentabsent_subject);
            // Get the default translation from the currentWord object and set this text on
            // the default TextView.
            presentCount.setText("Present "+currentWord.getmPresent()+" / Absent "+currentWord.getmAbsent());
            float present=currentWord.getmPresent();
            float absent=currentWord.getmAbsent();
            float result=present/(present+absent);
            result = (float) Math.round(result * 100) / 100;

            TextView percent = (TextView) listItemView.findViewById(R.id.percent_subject);
            // Get the default translation from the currentWord object and set this text on
            // the default TextView.
            percent.setText("Percent: "+result+"%");

        }
        else if(kSubject==2){
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.attendance_subject_list_calendar, parent, false);
            }
            attendanceItem currentWord = getItem(position);
            TextView date=listItemView.findViewById(R.id.subjName_calendar);
            date.setText(currentWord.getmSubjDate());

            TextView dt= listItemView.findViewById(R.id.dt_calendar);
            dt.setText("Friday , "+currentWord.getmSubjTime());

            TextView pacp= listItemView.findViewById(R.id.pacp_calendar);
            pacp.setText("Present");
            pacp.setBackgroundColor(Color.GREEN);


        }
        else{
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.attendance_list_item, parent, false);
            }

            // Get the {@link attendanceItem} object located at this position in the list
            attendanceItem currentWord = getItem(position);

            // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
            TextView miwokTextView = (TextView) listItemView.findViewById(R.id.subjName);
            // Get the Miwok translation from the currentWord object and set this text on
            // the Miwok TextView.
            miwokTextView.setText(currentWord.getSubjName());

            // Find the TextView in the list_item.xml layout with the ID default_text_view.
            TextView defaultTextView = (TextView) listItemView.findViewById(R.id.subjDateTime);
            // Get the default translation from the currentWord object and set this text on
            // the default TextView.
            defaultTextView.setText(currentWord.getmSubjTime() + currentWord.getmSubjDate());
        }
        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}
