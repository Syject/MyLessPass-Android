package com.syject.lesspass.ui.screens.lesspass;

import android.support.v4.app.Fragment;

import com.syject.lesspass.ui.BaseActivity;

import org.androidannotations.annotations.EActivity;

@EActivity
public class LessPassActivity extends BaseActivity {

    @Override
    protected Fragment startFragment() {
        return LessPassFragment_.builder().build();
    }
}
