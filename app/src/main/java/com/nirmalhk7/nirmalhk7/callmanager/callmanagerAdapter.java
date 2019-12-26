package com.nirmalhk7.nirmalhk7.callmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nirmalhk7.nirmalhk7.R;

import java.util.ArrayList;

public class callmanagerAdapter extends ArrayAdapter<callmanagerListItem> {


    /**
     * Resource ID for the background color for this list of words
     */

    public callmanagerAdapter(Context context, ArrayList<callmanagerListItem> callItem) {
        super(context, 0, callItem);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.callmgr_list_item, parent, false);
        }

        // Get the {@link timetableListItem} object located at this position in the list
        callmanagerListItem currentWord = getItem(position);

        // Find the TextView in the dailyschedule_list_item.xml_list_item.xml layout with the ID miwok_text_view.
        TextView callerName = listItemView.findViewById(R.id.callMgr_callName);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.
        callerName.setText(currentWord.getName());

        // Find the TextView in the dailyschedule_list_itemdule_list_item.xml layout with the ID default_text_view.
        TextView defaultTextView = listItemView.findViewById(R.id.callMgr_callNo);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        defaultTextView.setText(currentWord.getNo());

        TextView defaultTime = listItemView.findViewById(R.id.callMgr_callTime);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        defaultTime.setText(currentWord.getCallNo()+" Time");

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}

