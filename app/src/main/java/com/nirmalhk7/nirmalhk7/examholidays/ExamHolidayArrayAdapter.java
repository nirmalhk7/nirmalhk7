package com.nirmalhk7.nirmalhk7.examholidays;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.model.ExamholidaysEntity;
import com.nirmalhk7.nirmalhk7.util.converter;

import java.util.List;

public class ExamHolidayArrayAdapter extends ArrayAdapter<ExamholidaysEntity> {
    /*
     * Resource ID for the background color for this list of words
     */
    private int mColorResourceId;


    public ExamHolidayArrayAdapter(Context context, List<ExamholidaysEntity> event) {
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

        // Get the {@link TimetableListItem} object located at this position in the list
        ExamholidaysEntity currentWord = getItem(position);

        // Find the TextView in the dailyschedule_list_itemdule_list_item.xml layout with the ID miwok_text_view.
        TextView title = listItemView.findViewById(R.id.holidayExam_name);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.
        title.setText(currentWord.getmName());

    //TODO
        TextView id=listItemView.findViewById(R.id.holidayExam_id);
        id.setText(Integer.toString(currentWord.getId()));
        TextView hOrE = listItemView.findViewById(R.id.holidayExam);
        if(currentWord.getHolexa()==1)
        {
            hOrE.setText(currentWord.getmType());
            hOrE.setTextColor(Color.RED);
        }
        else if(currentWord.getHolexa()==2)
        {
            hOrE.setText("HOLIDAY");
            hOrE.setTextColor(Color.GREEN);
        }


        TextView date = listItemView.findViewById(R.id.holidayExam_date);
        String eventDate= converter.date_to(currentWord.getStart(),"dd MM yyyy");
        if(currentWord.getEnd()!=null)
            eventDate+=" - "+converter.date_to(currentWord.getEnd(),"dd MM yyyy");

        date.setText(eventDate);
        return listItemView;
    }
}
