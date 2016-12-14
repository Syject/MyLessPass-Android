package com.syject.domain.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class SystemUtils {

    public static void copyToClipboard(Context context, String password, CharSequence label) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText(label, password);
        clipboardManager.setPrimaryClip(data);
    }
}
