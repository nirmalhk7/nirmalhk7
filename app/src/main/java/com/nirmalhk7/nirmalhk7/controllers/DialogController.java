package com.nirmalhk7.nirmalhk7.controllers;

import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

public interface DialogController{

    void onEditSetup(int dbNo, LinearLayout topIcons);
    void autocompleteSetup(String[] subject, AutoCompleteTextView autocomplete);

}
