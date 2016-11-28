package com.syject.lesspass;

import android.text.TextUtils;

public class LessPassHelper {
    
    public static boolean validateLength(CharSequence length) {
        try {
            convertInt(length);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static Integer convertInt(CharSequence cs) {
        return Integer.parseInt(cs.toString());
    }
}
