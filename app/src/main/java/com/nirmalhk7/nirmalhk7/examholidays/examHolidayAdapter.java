package com.nirmalhk7.nirmalhk7.examholidays;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nirmalhk7.nirmalhk7.R;

import java.util.ArrayList;

public class examHolidayAdapter extends ArrayAdapter<examholidaysListItem> {
    /**
     * Resource ID for the background color for this list of words
     */
    private int mColorResourceId;


    public examHolidayAdapter(Context context, ArrayList<examholidaysListItem> event) {
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

        // Get the {@link timetableListItem} object located at this position in the list
        examholidaysListItem currentWord = getItem(position);

        // Find the TextView in the dailyschedule_list_itemdule_list_item.xml layout with the ID miwok_text_view.
        TextView title = listItemView.findViewById(R.id.holidayExam_name);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.
        title.setText(currentWord.getTitle());

    //TODO
        TextView id=listItemView.findViewById(R.id.holidayExam_id);
        id.setText(Integer.toString(currentWord.getmId()));
        // Find the TextView in the dailyschedule_list_item.xml_list_item.xml layout with the ID default_text_view.
        TextView hOrE = listItemView.findViewById(R.id.holidayExam);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        if(currentWord.getHolidayOrExam()==1)
        {
            hOrE.setText(currentWord.getmType());
            hOrE.setTextColor(Color.RED);
        }
        else if(currentWord.getHolidayOrExam()==2)
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
