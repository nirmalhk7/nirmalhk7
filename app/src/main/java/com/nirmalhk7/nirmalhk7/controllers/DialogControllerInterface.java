package com.nirmalhk7.nirmalhk7.controllers;

import android.widget.ImageView;
import android.widget.LinearLayout;

public interface DialogControllerInterface {
    ImageView onEditSetup(int dbNo, LinearLayout topIcons);
    String[] autocompleteSetup();
    void deleteEntry(int dbNo);
}
