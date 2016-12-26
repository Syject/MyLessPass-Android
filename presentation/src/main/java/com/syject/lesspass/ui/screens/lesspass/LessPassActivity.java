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

    @Bean
    protected PreferencesManager preferences;

    @Bean(AuthInteractor.class)
    protected IAuthInteractor authInteractor;

    @Override
    protected Fragment startFragment() {
        return LessPassFragment_.builder().build();
    }

    @OptionsItem(R.id.action_sign_in)
    void signIn() {
        showFragment(LoginFragment_.builder().build(), true);
    }

    @OptionsItem(R.id.action_sign_out)
    void signOut() {
        authInteractor.signOut()
                .subscribe(n -> showStartActivity());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.fragment_less_pass, menu);

        boolean isSignIn = preferences.isSignIn();
        menu.findItem(R.id.action_keys).setVisible(isSignIn);
        menu.findItem(R.id.action_sign_out).setVisible(isSignIn);
        menu.findItem(R.id.action_sign_in).setVisible(!isSignIn);

        return super.onCreateOptionsMenu(menu);
    }
}
