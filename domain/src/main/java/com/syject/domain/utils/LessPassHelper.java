package com.syject.domain.utils;

import android.text.TextUtils;

public class LessPassHelper {
    
    public static boolean validateLength(CharSequence value) {
        return !TextUtils.isEmpty(value);
    }

    public static boolean validateNumbers(CharSequence value) {
        try {
            convertInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static Integer convertInt(CharSequence value) {
        return Integer.parseInt(value.toString());
    }

    public static Integer getInt(CharSequence value) {
        try {
            return convertInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
