package com.nirmalhk7.nirmalhk7.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.model.CpScheduleListItem;

import java.util.ArrayList;

public class CpScheduleArrayAdapter extends ArrayAdapter<CpScheduleListItem> {
    /**
     * Resource ID for the background color for this list of words
     */
    private int mColorResourceId;


    public CpScheduleArrayAdapter(Context context, ArrayList<CpScheduleListItem> event) {
        super(context, 0, event);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_cpschedule, parent, false);
        }

        // Get the {@link TimetableListItem} object located at this position in the list
        CpScheduleListItem currentWord = getItem(position);

        TextView hostname= listItemView.findViewById(R.id.codesch_hostname);
        hostname.setText(currentWord.getHname());

        TextView comptnName=listItemView.findViewById(R.id.codesh_compname);
        comptnName.setText(currentWord.getCName());

        TextView link=listItemView.findViewById(R.id.codesch_link);
        link.setText(currentWord.getCurl());

        TextView duration=listItemView.findViewById(R.id.codesch_time);
        duration.setText(currentWord.getDuration());

        return listItemView;
    }
}
