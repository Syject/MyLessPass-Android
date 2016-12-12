package com.syject.lesspass.views.fragments;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.syject.lesspass.LessPassHelper;
import com.syject.lesspass.R;
import com.syject.lesspass.presenters.LessPassPresenter;
import com.syject.lesspass.views.ILessPassView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_less_pass)
public class LessPassFragment extends Fragment implements ILessPassView {

    private static final String TAG = "LessPassFragment";

    @Bean
    LessPassPresenter presenter;

    @ViewById(R.id.site_edit_text)
    protected EditText site;

    @ViewById(R.id.login_edit_text)
    protected EditText login;

    @ViewById(R.id.master_password_edit_text)
    protected EditText masterPassword;

    @ViewById
    protected TextView password;

    @ViewById
    protected CheckBox hasLowerCaseLitters;

    @ViewById
    protected CheckBox hasAppearCaseLitters;

    @ViewById
    protected CheckBox hasNumbers;

    @ViewById
    protected CheckBox hasSymbols;

    @ViewById
    protected EditText length;

    @ViewById
    protected EditText counter;

    @ViewById
    protected Button generate;

    private Subscription subscription;

    @AfterInject
    protected void afterInject() {
        presenter.setView(this);
    }

    @AfterViews
    protected void initViews() {
        subscription = Observable.combineLatest(
                RxTextView.textChanges(site)
                        .filter(LessPassHelper::validateLength),
                RxTextView.textChanges(login)
                        .filter(LessPassHelper::validateLength),
                RxTextView.textChanges(masterPassword)
                        .filter(LessPassHelper::validateLength),
                RxTextView.textChanges(length)
                        .filter(LessPassHelper::validateNumbers),
                RxTextView.textChanges(counter)
                        .filter(LessPassHelper::validateNumbers),
                RxCompoundButton.checkedChanges(hasLowerCaseLitters),
                RxCompoundButton.checkedChanges(hasAppearCaseLitters),
                RxCompoundButton.checkedChanges(hasNumbers),
                RxCompoundButton.checkedChanges(hasSymbols),

                (s, l, mp, len, c, lcl, acl, n, sym) -> null
        )
                .flatMap(v -> presenter.generatePassword().subscribeOn(Schedulers.newThread()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setPassword);
    }

    @Click(R.id.copy_button)
    void copyPassword() {
        presenter.copyToClipboard();
    }

    @Click
    void generate() {
        Toast.makeText(getActivity(), "Generating...", Toast.LENGTH_SHORT).show();
    }

    public static LessPassFragment newInstance() {
        return LessPassFragment_.builder().build();
    }

    @Override
    public String getSite() {
        return site.getText().toString();
    }

    @Override
    public String getLogin() {
        return login.getText().toString();
    }

    @Override
    public String getMasterPassword() {
        return masterPassword.getText().toString();
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public int getLength() {
        return LessPassHelper.getInt(length.getText().toString());
    }

    @Override
    public int getCounter() {
        return LessPassHelper.getInt(counter.getText().toString());
    }

    @Override
    public boolean hasLowerCaseLitters() {
        return hasLowerCaseLitters.isChecked();
    }

    @Override
    public boolean hasAppearCaseLitters() {
        return hasAppearCaseLitters.isChecked();
    }

    @Override
    public boolean hasNumbers() {
        return hasNumbers.isChecked();
    }

    @Override
    public boolean hasSymbols() {
        return hasSymbols.isChecked();
    }

    @Override
    public void onValidationLengthError() {
        length.setError("Length is incorrect!");
    }

    @Override
    public void onValidationCounterError() {
        counter.setError("Counter is incorrect!");
    }

    @Override
    public void setPassword(String pass) {
        password.setText(pass);
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
    public void onDestroyView() {
        super.onDestroyView();
        presenter.destroy();
        subscription.unsubscribe();
    }
}
