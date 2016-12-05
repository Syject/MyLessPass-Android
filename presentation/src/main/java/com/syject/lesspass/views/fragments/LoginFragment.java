package com.syject.lesspass.views.fragments;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.syject.lesspass.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment {

    @InstanceState
    boolean isExpanded;
    int textLength;
    String textExpanded;

    @ViewById(R.id.master_password_check_box)
    CheckBox masterPasswordCheckBox;

    @ViewById(R.id.params)
    ImageButton paramsImageButton;

    @ViewById(R.id.host_liner_layoit)
    LinearLayout hostLinerLayout;

    @AfterViews
    protected void initViews() {
        textLength = getString(R.string.use_master_password).length();
        textExpanded = getString(R.string.use_master_password_expand);
        masterPasswordCheckBox.setOnClickListener(view -> {
                isExpanded = !isExpanded;
                toggleText(isExpanded);
        });

        paramsImageButton.setOnClickListener(view -> {
            if (hostLinerLayout.getVisibility() == View.GONE) {
                hostLinerLayout.setVisibility(View.VISIBLE);
            } else {
                hostLinerLayout.setVisibility(View.GONE);
            }
        });
    }

    private void toggleText(boolean isExpanded) {
        if (isExpanded) {
            masterPasswordCheckBox.append(textExpanded);
        } else {
            String string = masterPasswordCheckBox.getText().toString();
            String resStr = string.substring(0, string.length() - textExpanded.length());
            masterPasswordCheckBox.setText(resStr);
        }
    }

    public static LoginFragment newInstance() {
        return LoginFragment_.builder().build();
    }
}
