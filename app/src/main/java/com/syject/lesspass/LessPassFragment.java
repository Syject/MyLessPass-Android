package com.syject.lesspass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

@OptionsMenu(R.menu.fragment_less_pass)
@EFragment(R.layout.fragment_less_pass)
public class LessPassFragment extends Fragment {

    private static final String TAG = "LessPassFragment";

    @ViewById(R.id.site_edit_text)
    protected EditText site;

    @ViewById(R.id.login_edit_text)
    protected EditText login;

    @ViewById(R.id.master_password_edit_text)
    protected EditText masterPassword;

    @ViewById
    protected TextView password;

    @TextChange(R.id.site_edit_text)
    protected void onSiteChange(TextView site) {
    }

    @OptionsItem(R.id.action_login)
    void login() {
        Toast.makeText(getActivity(), "login", Toast.LENGTH_LONG).show();
    }

    @Click(R.id.copy_button)
    void copyPassword() {
        if (password != null && !password.getText().equals("")) {
            Toast.makeText(getActivity(), "Copied!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Generate password firstly!", Toast.LENGTH_SHORT).show();
        }
    }

    public static LessPassFragment newInstance() {
        return LessPassFragment_.builder().build();
    }
}
