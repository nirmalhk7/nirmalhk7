package com.nirmalhk7.nirmalhk7.ArrayAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nirmalhk7.nirmalhk7.Controllers.Converters;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.model.ExamholidaysEntity;

import java.util.List;

public class ExamHolidayArrayAdapter extends ArrayAdapter<ExamholidaysEntity> {

    private int mColorResourceId;


    public ExamHolidayArrayAdapter(Context context, List<ExamholidaysEntity> event) {
        super(context, 0, event);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_examholiday, parent, false);
        }

        ExamholidaysEntity currentWord = getItem(position);

        TextView title = listItemView.findViewById(R.id.holidayExam_name);
        title.setText(currentWord.getmName());
        Long today=Converters.dateToTimestamp(
                Converters.to_date(
                        Converters.today_get("dd MM yyyy"),
                        "dd MM yyyy"
                )
        );
        Long start=Converters.dateToTimestamp(
                currentWord.getStart()
        );
        Long end=Converters.dateToTimestamp(currentWord.getEnd());

        if(start<today && end<today)
        {
            listItemView.setBackgroundColor(Color.parseColor("#66494949"));
        }

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
        String eventDate= Converters.date_to(currentWord.getStart(),"dd MM yyyy");
        if(currentWord.getEnd()!=null)
            eventDate+=" - "+ Converters.date_to(currentWord.getEnd(),"dd MM yyyy");

        date.setText(eventDate);
        return listItemView;
    }
}
