package com.syject.lesspass.ui.screens.lesspass;

import android.support.v4.app.Fragment;

import com.syject.lesspass.R;
import com.syject.lesspass.ui.BaseFragmentActivity;
import com.syject.lesspass.ui.screens.login.LoginFragment_;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

@OptionsMenu(R.menu.fragment_less_pass)
@EActivity
public class LessPassActivity extends BaseFragmentActivity {

    @Override
    protected Fragment startFragment() {
        return LessPassFragment_.builder().build();
    }

    @OptionsItem(R.id.action_login)
    void login() {
        showFragment(LoginFragment_.builder().build(), true);
    }
}
