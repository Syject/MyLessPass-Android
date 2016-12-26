package com.syject.lesspass.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.syject.lesspass.R;
import com.syject.lesspass.ui.screens.lesspass.LessPassActivity_;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (savedInstanceState == null)
            showFragment(startFragment(), false);
    }

    protected abstract Fragment startFragment();

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    protected void showFragment(Fragment fragment, boolean isAddToBackStack) {
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction()
                .replace(R.id.fragment_container, fragment);

        if (isAddToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }
}