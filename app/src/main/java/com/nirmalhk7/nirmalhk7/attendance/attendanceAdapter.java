package com.nirmalhk7.nirmalhk7.attendance;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class attendanceAdapter extends ArrayAdapter<attendanceItem> {

    /**
     * Resource ID for the background color for this list of words
     */
    private int kSubject;
    private Context context;
    public attendanceAdapter(Context context, ArrayList<attendanceItem> AttendanceItem) {
        super(context, 0, AttendanceItem);
        kSubject=0;
        this.context=context;
    }

    public attendanceAdapter(Context context, ArrayList<attendanceItem> SubjectItem, int key) {
        super(context, 0, SubjectItem);
        kSubject = key;
        this.context=context;
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
            final attendanceItem currentWord = getItem(position);

            // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
            TextView subjName_subj = listItemView.findViewById(R.id.subjName_subject);
            // Get the Miwok translation from the currentWord object and set this text on
            // the Miwok TextView.
            subjName_subj.setText(currentWord.getSubjName());

            // Find the TextView in the list_item.xml layout with the ID default_text_view.
            TextView presentCount = listItemView.findViewById(R.id.presentabsent_subject);
            // Get the default translation from the currentWord object and set this text on
            // the default TextView.
            presentCount.setText("Present "+currentWord.getmPresent()+" / Absent "+currentWord.getmAbsent());
            float present=currentWord.getmPresent();
            float absent=currentWord.getmAbsent();
            float result=present/(present+absent);
            result = (float) Math.round(result * 100);

            TextView percent = listItemView.findViewById(R.id.percent_subject);
            // Get the default translation from the currentWord object and set this text on
            // the default TextView.
            percent.setText("Percent: "+result+"%");

            final TextView id=listItemView.findViewById(R.id.attendance_id);
            Log.d("XXXC",""+currentWord.getmId());
            id.setText(currentWord.getmId()+"");

            btnlistener(listItemView,Integer.parseInt(id.getText().toString()));

            SwipeLayout swipeLayout =  (SwipeLayout)listItemView.findViewById(R.id.swipeswipe);

//set show mode.
            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

//add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
            swipeLayout.addDrag(SwipeLayout.DragEdge.Left, listItemView.findViewById(R.id.bottom_wrapper));
            final ImageButton deleteb=listItemView.findViewById(R.id.trash_swipe);
            final ImageButton editb=listItemView.findViewById(R.id.edit_swipe);
            swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onStartOpen(SwipeLayout layout) {

                }

                @Override
                public void onOpen(SwipeLayout layout) {

                    DBGateway database = Room.databaseBuilder(context, DBGateway.class, "finalDB")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();

                    final attendanceDAO attendanceDAO = database.getAttendanceDao();
                    deleteb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            attendanceEntity x=attendanceDAO.getSubjectbyId(currentWord.getmId());
                            attendanceDAO.deleteSchedule(x);
                        }
                    });
                    editb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("ATT/ATA","Clicked");
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Att_FullScreenDialog newFragment = new Att_FullScreenDialog();
                            Bundle bundle=new Bundle();
                            bundle.putInt("key",currentWord.getmId());
                            bundle.putInt("present",currentWord.getmPresent());
                            bundle.putInt("absent",currentWord.getmAbsent());
                            bundle.putString("subject",currentWord.getSubjName());
                            newFragment.setArguments(bundle);
                            activity.getSupportFragmentManager().beginTransaction().add(android.R.id.content, newFragment).addToBackStack(null).addToBackStack(null).commit();

                        }
                    });
                }

                @Override
                public void onStartClose(SwipeLayout layout) {

                }

                @Override
                public void onClose(SwipeLayout layout) {

                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

                }
            });

            LinearLayout edit=listItemView.findViewById(R.id.editAttendance);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
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
            TextView miwokTextView = listItemView.findViewById(R.id.subjName);
            // Get the Miwok translation from the currentWord object and set this text on
            // the Miwok TextView.
            miwokTextView.setText(currentWord.getSubjName());

            // Find the TextView in the list_item.xml layout with the ID default_text_view.
            TextView defaultTextView = listItemView.findViewById(R.id.subjDateTime);
            // Get the default translation from the currentWord object and set this text on
            // the default TextView.
            defaultTextView.setText(currentWord.getmSubjTime() + currentWord.getmSubjDate());
        }
        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }

    void btnlistener(View listitemview,final int id)
    {
        ImageButton p=listitemview.findViewById(R.id.present_btn);
        ImageButton a=listitemview.findViewById(R.id.absent_btn);
        DBGateway database = Room.databaseBuilder(context, DBGateway.class, "finalDB")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        final attendanceDAO attendanceDAO = database.getAttendanceDao();
        Log.d("ATT/ALS","ID "+id);
        //final attendanceEntity att=attendanceDAO.getSubjectbyId(id);
        final attendanceEntity att=attendanceDAO.getSubjectbyId(id);

        Log.d("ATT/ALS","Recieved "+att.getSubject()+" "+att.getPresent()+" "+att.getAbsent());
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pr=att.getPresent();
                att.setPresent(++pr);
                attendanceDAO.updateSubject(att);
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ab=att.getAbsent();
                att.setAbsent(++ab);
                attendanceDAO.updateSubject(att);
            }
        });




    }
}
