package com.syject.lesspass.views.activities;

import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.Toast;

import com.syject.lesspass.R;
import com.syject.lesspass.views.fragments.LessPassFragment;
import com.syject.lesspass.views.fragments.LoginFragment;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;

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
