package com.nirmalhk7.nirmalhk7.attendance;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.DialogFragment;

import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.controllers.AttendanceDialogController;
import com.nirmalhk7.nirmalhk7.model.AttendanceDAO;
import com.nirmalhk7.nirmalhk7.model.SubjectlogDAO;

public class AttendanceDialogFragment extends DialogFragment {
    public int key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_attendance, container, false);

        final AttendanceDAO attendanceDAO = DBGateway.getInstance(getContext()).getATTDao();
        final SubjectlogDAO SLDAO=DBGateway.getInstance(getContext()).getSALDAO();
        final AttendanceDialogController attendanceDialogController=new AttendanceDialogController(getContext(),getActivity());

        final EditText Present = rootView.findViewById(R.id.present_fsd);
        final EditText Absent = rootView.findViewById(R.id.absent_fsd);


        final Bundle bundle = this.getArguments();
        if (bundle != null) {

            final int dbNo=bundle.getInt("key");
            LinearLayout topIcons = rootView.findViewById(R.id.attendanceDialog);

            attendanceDialogController.onEditSetup(dbNo,topIcons);
            Log.d("ATT/FSD","Bundle Passed: "+dbNo);
            Present.setText(bundle.getInt("present")+"");
            Absent.setText(bundle.getInt("absent")+"");

            AppCompatAutoCompleteTextView autoTextView=
                    rootView.findViewById(R.id.attendance_task);

            autoTextView.setText(bundle.getString("subject")+"");


        }



        String[] subject = attendanceDialogController.getSubjectsList();
        final AutoCompleteTextView autoTextView=rootView.findViewById(R.id.attendance_task);
        attendanceDialogController.autocompleteSetup(subject,autoTextView);


        changeTotal(rootView, Present, Absent);


        //Close button action
        (rootView.findViewById(R.id.button_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        //On Click SAVE
        Button b = rootView.findViewById(R.id.ATT_button_save);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation(rootView)) {
                    attendanceDialogController.onClickSave(bundle,Integer.valueOf(Present.getText().toString()),
                            Integer.valueOf(Absent.getText().toString()),
                            autoTextView.getText().toString());
                    dismiss();
                }
            }
        });
        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void changeTotal(View rootView, final EditText pr, final EditText ab) {

        final TextView Total = rootView.findViewById(R.id.total_fsd);
        pr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int p = Integer.parseInt(s.toString());
                    int a = Integer.parseInt(ab.getText().toString());
                    Total.setText("Total Classes: " + (p + a));
                    Log.d("ATT/FSD", "TotalTV " + Total.getText());

                } catch (NumberFormatException e) {
                    Log.e("ATT/FSD", e.getMessage());
                }

            }
        });
        ab.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int a = Integer.parseInt(s.toString());
                    int p = Integer.parseInt(pr.getText().toString());
                    Total.setText("Total Classes: " + (p + a));
                    Log.d("ATT/FSD", "TotalTV " + Total.getText());

                } catch (NumberFormatException e) {
                    Log.e("ATT/FSD", e.getMessage());
                }

            }
        });

    }

    public boolean Validation(View rootview) {
        AppCompatAutoCompleteTextView EtaskName = rootview.findViewById(R.id.attendance_task);
        String taskName = EtaskName.getText().toString();
        if (taskName.isEmpty()) {
            Log.d("ATT/FSD", "Validation SubjectName");
            EtaskName.setError("Required");
            return false;
        }
        return true;

    }
}
