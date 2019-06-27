package com.nirmalhk7.nirmalhk7.dailyscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nirmalhk7.nirmalhk7.R;

import java.util.ArrayList;

public class ScheduleAdapter extends ArrayAdapter<scheduleItem> {

    /** Resource ID for the background color for this list of words */
    private int mColorResourceId;


    public ScheduleAdapter(Context context, ArrayList<scheduleItem> words) {
        super(context, 0, words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link scheduleItem} object located at this position in the list
        scheduleItem currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.
        miwokTextView.setText(currentWord.getScheduleTitle());

        // Find the TextView in the list_item.xml layout with the ID default_text_view.
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        defaultTextView.setText(currentWord.getScheduleLabel());

        TextView defaultTime = (TextView) listItemView.findViewById(R.id.default_time);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        defaultTime.setText(currentWord.getScheduleId());



        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}