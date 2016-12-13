package com.syject.lesspass.ui.screens.lesspass;

import android.support.v4.app.Fragment;

import com.syject.lesspass.R;
import com.syject.lesspass.ui.SingleFragmentActivity;
import com.syject.lesspass.ui.screens.login.LoginFragment;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

@OptionsMenu(R.menu.fragment_less_pass)
@EActivity
public class LessPassActivity extends SingleFragmentActivity {

    @Override
    protected Fragment startFragment() {
        return LessPassFragment.newInstance();
    }

    @OptionsItem(R.id.action_login)
    void login() {
        showFragment(LoginFragment.newInstance());
    }
}
