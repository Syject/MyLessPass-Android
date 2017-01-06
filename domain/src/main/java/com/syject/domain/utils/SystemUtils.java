package com.syject.domain.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SystemService;

@EBean
public class SystemUtils {

    @SystemService
    static InputMethodManager inputMethodManager;

    public void copyToClipboard(Context context, String password, CharSequence label) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText(label, password);
        clipboardManager.setPrimaryClip(data);
    }

    public void closeKeyboard(View view) {
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
