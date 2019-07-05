package com.nirmalhk7.nirmalhk7.examholidays;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.dailyscheduler.scheduleItem;

import java.util.ArrayList;

public class ExamHolidayAdapter extends ArrayAdapter<hsItem> {
    /**
     * Resource ID for the background color for this list of words
     */
    private int mColorResourceId;


    public ExamHolidayAdapter(Context context, ArrayList<hsItem> event) {
        super(context, 0, event);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_examholiday, parent, false);
        }

        // Get the {@link scheduleItem} object located at this position in the list
        hsItem currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
        TextView title = listItemView.findViewById(R.id.holidayExam_name);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.
        title.setText(currentWord.getTitle());

        // Find the TextView in the list_item.xml layout with the ID default_text_view.
        TextView hOrE = listItemView.findViewById(R.id.holidayExam);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        if(currentWord.getHolidayOrExam()==0)
        {
            hOrE.setText("EXAM    ");
            hOrE.setTextColor(Color.RED);
        }
        else if(currentWord.getHolidayOrExam()==1)
        {
            hOrE.setText("HOLIDAY");
            hOrE.setTextColor(Color.GREEN);
        }


        TextView date = listItemView.findViewById(R.id.holidayExam_date);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        date.setText(currentWord.getmDate());


        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}
