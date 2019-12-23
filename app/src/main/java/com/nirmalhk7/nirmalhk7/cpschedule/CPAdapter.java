package com.nirmalhk7.nirmalhk7.cpschedule;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.examholidays.heItem;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CPAdapter extends ArrayAdapter<cpItem> {
    /**
     * Resource ID for the background color for this list of words
     */
    private int mColorResourceId;


    public CPAdapter(Context context, ArrayList<cpItem> event) {
        super(context, 0, event);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.cpsched_list_item, parent, false);
        }

        // Get the {@link scheduleItem} object located at this position in the list
        cpItem currentWord = getItem(position);

        TextView hostname= listItemView.findViewById(R.id.codesch_hostname);
        hostname.setText(currentWord.getHname());

        TextView comptnName=listItemView.findViewById(R.id.codesh_compname);
        comptnName.setText(currentWord.getCName());

        TextView time=listItemView.findViewById(R.id.codesch_time);
        time.setText(currentWord.getTime());

        TextView duration=listItemView.findViewById(R.id.codesch_duration);
        duration.setText(currentWord.getDuration());

        return listItemView;
    }
}
