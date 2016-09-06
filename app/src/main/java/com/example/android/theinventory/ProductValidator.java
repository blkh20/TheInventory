package com.example.android.theinventory;

import android.widget.EditText;

/**
 * Created by BlkH20 on 9/6/2016.
 */
public class ProductValidator {

    public boolean checkBlank(EditText words) {
        if (words.getText().toString().trim().length() > 0)
            return false;
        else
            return true;

    }

    public boolean checkIsFloat(EditText words) {
        try {
            Float.parseFloat(words.getText().toString());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean checkIsInteger(EditText words) {
        try {
            Integer.parseInt(words.getText().toString());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}