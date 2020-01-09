package com.nirmalhk7.nirmalhk7;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class common {
    Context mContext;
    public common(Context context)
    {
        mContext=context;
    }
    public void hideKeyboard(View view){
        InputMethodManager imm= (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(imm!=null) {
            if (view == null)
                view = new View(mContext);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
