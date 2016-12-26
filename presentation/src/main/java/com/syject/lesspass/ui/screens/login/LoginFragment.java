package com.syject.lesspass.ui.screens.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.syject.data.preferences.PreferencesManager;
import com.syject.lesspass.R;
import com.syject.lesspass.ui.screens.lesspass.LessPassActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment implements ILoginView {

    @Bean
    LoginPresenter presenter;

    @InstanceState
    boolean isExpanded;

    int textLength;

    String textExpanded;

    @ViewById
    EditText emailEditText;

    @ViewById
    EditText lessPasswordEditText;

    @ViewById
    EditText hostEditText;

    @ViewById
    CheckBox masterPasswordCheckBox;

    @ViewById
    ImageButton paramsImageButton;

    @ViewById
    LinearLayout hostLinerLayout;

    @ViewById
    Button signInButton;

    @ViewById
    ProgressBar progressBar;

    @ViewById
    TextView emailPasswordErrorTextView;

    @AfterViews
    protected void initViews() {

        presenter.setView(this);

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

        RxView.clicks(signInButton)
                .subscribe(v -> presenter.login());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
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

    @Override
    public String getEmail() {
        return emailEditText.getText().toString();
    }

    @Override
    public String getLesspassPassword() {
        return lessPasswordEditText.getText().toString();
    }

    @Override
    public String getHostUrl() {
        return hostEditText.getText().toString();
    }

    @Override
    public void onSignInSuccess() {
        Intent openMainActivity= new Intent(getActivity(), LessPassActivity_.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(openMainActivity);
        setIsInProgress(false);
    }

    @Override
    public void onSignInFail(Throwable throwable) {
        lessPasswordEditText.setText(null);
        setIsInProgress(false);
        emailPasswordErrorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSigningIn() {
        emailPasswordErrorTextView.setVisibility(View.GONE);
        setIsInProgress(true);
    }

    private void setIsInProgress(boolean isInProgress) {
        signInButton.setText(isInProgress ? R.string.empty_string : R.string.sign_in);
        progressBar.setVisibility(isInProgress ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
