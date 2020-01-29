package com.nirmalhk7.nirmalhk7.DialogFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.controllers.AttendanceDialogControllerInterface;
import com.nirmalhk7.nirmalhk7.model.AttendanceDAO;
import com.nirmalhk7.nirmalhk7.model.SubjectlogDAO;

public class AttendanceDialogFragment extends DialogFragment{
    public int key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    AttendanceDialogControllerInterface attendanceDialogController=new AttendanceDialogControllerInterface(getContext(),getActivity());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_attendance, container, false);

        final AttendanceDAO attendanceDAO = DBGateway.getInstance(getContext()).getATTDao();
        final SubjectlogDAO SLDAO=DBGateway.getInstance(getContext()).getSALDAO();

        final EditText Present = rootView.findViewById(R.id.present_fsd);
        final EditText Absent = rootView.findViewById(R.id.absent_fsd);
        final AppCompatAutoCompleteTextView autoTextView=rootView.findViewById(R.id.attendance_task);

        final Bundle bundle = this.getArguments();
        if (bundle != null) {

            final int dbNo=bundle.getInt("key");
            Log.d("ATT/FSD","Bundle Passed: "+dbNo);

            LinearLayout topIcons = rootView.findViewById(R.id.attendanceDialog);
            attendanceDialogController.onEditSetup(dbNo,topIcons);
            Present.setText(bundle.getInt("present")+"");
            Absent.setText(bundle.getInt("absent")+"");


            autoTextView.setText(bundle.getString("subject")+"");
        }

        String[] subject = attendanceDialogController.autocompleteSetup();
        attendanceDialogController.autocompleteSetup();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), R.layout.partial_suggestion, subject);
        autoTextView.setThreshold(1); //will start working from first character
        autoTextView.setAdapter(adapter);


        attendanceDialogController.changeTotal(rootView, Present, Absent);


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

                    try{
                        Snackbar.make(rootView,"Please refresh to update :)",Snackbar.LENGTH_LONG);
                        attendanceDialogController.onClickSave(bundle,Integer.valueOf(Present.getText().toString()),
                                Integer.valueOf(Absent.getText().toString()),
                                autoTextView.getText().toString());

                    }catch (NumberFormatException e)
                    {
                        Log.d(getClass().getName(),e.getMessage());
                        attendanceDialogController.onClickSave(bundle,0,0,autoTextView.getText().toString());
                    }
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
