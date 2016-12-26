package com.syject.lesspass.ui.screens.lesspass;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;

import com.syject.data.preferences.PreferencesManager;
import com.syject.domain.interactors.IAuthInteractor;
import com.syject.domain.interactors.concrete.AuthInteractor;
import com.syject.lesspass.R;
import com.syject.lesspass.ui.BaseActivity;
import com.syject.lesspass.ui.screens.login.LoginFragment_;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

@EActivity
public class LessPassActivity extends BaseActivity {

    @Override
    protected Fragment startFragment() {
        return LessPassFragment_.builder().build();
    }
}
