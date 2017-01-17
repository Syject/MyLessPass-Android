package com.syject.lesspass.ui.screens.lesspass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.syject.data.entities.Options;
import com.syject.lesspass.ui.BaseActivity;
import com.syject.lesspass.ui.screens.keys.KeysFragment;
import com.syject.lesspass.ui.screens.keys.KeysFragment_;
import com.syject.lesspass.ui.screens.login.LoginFragment;
import com.syject.lesspass.ui.screens.login.LoginFragment_;

import org.androidannotations.annotations.EActivity;

@EActivity
public class LessPassActivity extends BaseActivity implements
        LessPassFragment.OnActionSelectedListener,
        LoginFragment.OnActionSelectedListener,
        KeysFragment.OnActionSelectedListener {

    static final int PICK_OPTIONS = 1;

    @Override
    protected Fragment startFragment() {
        return LessPassFragment_.builder().build();
    }

    @Override
    public void onSignInSelected() {
        showFragmentWithRTLAnimation(LoginFragment_.builder().build(), true);
    }

    @Override
    public void onSignOut() {
        resetActivity();
    }

    @Override
    public void onKeysSelected() {
        showFragmentWithRTLAnimation(KeysFragment_.builder().build(), true);
    }

    @Override
    public void onSignedIn() {
        resetActivity();
    }

    private void resetActivity() {
        LessPassActivity_.intent(this).flags(Intent.FLAG_ACTIVITY_CLEAR_TOP).start();
    }

    @Override
    public void onKeyClicked(Options options) {

        Bundle args = new Bundle();
        args.putSerializable(LessPassFragment.TAG_OPTIONS, options);
        Fragment fragment = LessPassFragment_.builder().build();
        fragment.setArguments(args);

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        showFragmentWithLTRAnimation(fragment, false);
    }
}
