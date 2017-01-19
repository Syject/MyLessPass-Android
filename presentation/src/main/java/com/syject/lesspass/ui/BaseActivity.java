package com.syject.lesspass.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.syject.lesspass.R;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;
import net.hockeyapp.android.metrics.MetricsManager;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (savedInstanceState == null) showFragment(startFragment(), false);

        checkForUpdates();
        MetricsManager.register(this, getApplication());
    }

    protected abstract Fragment startFragment();

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    protected void showFragment(Fragment fragment, boolean isAddToBackStack) {
        showFragment(fragment, isAddToBackStack, 0, 0, 0, 0);
    }

    protected void showFragmentWithLTRAnimation(Fragment fragment, boolean isAddToBackStack) {
        showFragment(fragment, isAddToBackStack, R.anim.enter_from_left, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_left);
    }

    protected void showFragmentWithRTLAnimation(Fragment fragment, boolean isAddToBackStack) {
        showFragment(fragment, isAddToBackStack, R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);

    }

    private void showFragment(Fragment fragment, boolean isAddToBackStack, @AnimRes int enter,
                              @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (enter != 0 && exit != 0 && popEnter != 0 && popExit != 0) {
            ft.setCustomAnimations(enter, exit, popEnter, popExit);
        }

        ft.replace(R.id.fragment_container, fragment);

        if (isAddToBackStack) {
            ft.addToBackStack(fragment.getClass().getName());
        }

        ft.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkForCrashes();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterManagers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterManagers();
    }

    private void checkForCrashes() {
        CrashManager.register(this);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }
}