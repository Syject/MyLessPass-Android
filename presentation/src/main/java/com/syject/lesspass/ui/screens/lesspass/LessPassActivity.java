package com.syject.lesspass.ui.screens.lesspass;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.syject.lesspass.ui.BaseActivity;
import com.syject.lesspass.ui.screens.keys.KeysFragment_;
import com.syject.lesspass.ui.screens.login.LoginFragment_;

import org.androidannotations.annotations.EActivity;

@EActivity
public class LessPassActivity extends BaseActivity implements LessPassFragment.OnActionSelectedListener {

    @Override
    protected Fragment startFragment() {
        return LessPassFragment_.builder().build();
    }

    @Override
    public void onSignInSelected() {
        showFragment(LoginFragment_.builder().build(), true, true);
    }

    @Override
    public void onSignOut() {
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(getIntent());
    }

    @Override
    public void onKeysSelected() {
        showFragment(KeysFragment_.builder().build(), true, true);
    }
}
