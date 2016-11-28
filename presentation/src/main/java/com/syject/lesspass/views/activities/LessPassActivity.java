package com.syject.lesspass.views.activities;

import android.support.v4.app.Fragment;

import com.syject.lesspass.views.fragments.LessPassFragment;

public class LessPassActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return LessPassFragment.newInstance();
    }
}
