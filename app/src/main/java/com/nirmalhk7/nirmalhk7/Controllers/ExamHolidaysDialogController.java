package com.nirmalhk7.nirmalhk7.Controllers;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nirmalhk7.nirmalhk7.DBGateway;
import com.nirmalhk7.nirmalhk7.R;
import com.nirmalhk7.nirmalhk7.model.ExamholidaysDAO;
import com.nirmalhk7.nirmalhk7.model.ExamholidaysEntity;

import java.util.List;

public class ExamHolidaysDialogController implements DialogControllerInterface {
    private Context mContext;
    private DBGateway database;
    public ExamHolidaysDialogController(Context context)
    {
        mContext=context;
        database=DBGateway.getInstance(mContext);
    }
    @Override
    public ImageView onEditSetup(int dbNo, LinearLayout l)
    {
        ImageView trash = new ImageView(mContext);
        trash.setImageResource(R.drawable.ic_trash);
        trash.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        int pxstd = mContext.getResources().getDimensionPixelSize(R.dimen.standard_dimen);
        trash.setPadding(pxstd, 0, pxstd, 0);
        return trash;
    }

    @Override
    public String[] autocompleteSetup() {
        ExamholidaysDAO EHDAO=database.getEHDAO();
        List<ExamholidaysEntity> eah= EHDAO.getEHTypesUnique();
        String[] suggestions=new String[eah.size()];
        int i=0;
        for(ExamholidaysEntity ehe: eah)
        {
            Log.d("EAH/EHE","Suggesting "+ehe.getmType());
            suggestions[i]=ehe.getmType();
            ++i;
        }
        return suggestions;
    }

    @Override
    public void deleteEntry(int dbNo) {

    }
}
