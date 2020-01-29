package com.nirmalhk7.nirmalhk7.DialogFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.controllers.SubjectLogDialogController;
import com.nirmalhk7.nirmalhk7.model.TimetableDAO;
import com.nirmalhk7.nirmalhk7.model.TimetableEntity;

import java.util.List;

public class SubjectLogDialogFragment extends DialogFragment {

    public static String TAG = "TimetableDialogFragment";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_attendance_subject_log_real, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow);

        //Close button action
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        toolbar.setTitle("Subject Log");
        Bundle b=this.getArguments();
        String subject=b.getString("subject");
        int p=b.getInt("present");
        int a=b.getInt("absent");
        int c=b.getInt("cancelled");

        TextView subjName=rootView.findViewById(R.id.sal_subject);
        subjName.setText(subject);
        TextView present=rootView.findViewById(R.id.sal_p);
        present.setText(Integer.toString(p));
        TextView absent=rootView.findViewById(R.id.sal_a);
        absent.setText(Integer.toString(a));
        TextView cancel=rootView.findViewById(R.id.sal_c);
        cancel.setText(Integer.toString(c));
        DBGateway database2 = DBGateway.getInstance(getContext());

        TimetableDAO SDAO=database2.getTTDao();
        List<TimetableEntity> x=SDAO.getScheduleCodeByTaskName(subjName.getText().toString());
        if(x.size()==1)
        {
            TextView code=rootView.findViewById(R.id.sal_code);
            code.setText(x.get(0).getSubjCode());
        }
        SubjectLogDialogController subjectLogDialogController=new SubjectLogDialogController(getContext());

        subjectLogDialogController.SALFetch((ListView)rootView.findViewById(R.id.list_item_subjects_log),subject);
        subjectLogDialogController.swiperefresh(rootView,subject,(SwipeRefreshLayout)rootView.findViewById(R.id.pullToRefresh),
                (ListView)rootView.findViewById(R.id.list_item_subjects_log));
        return rootView;

    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
