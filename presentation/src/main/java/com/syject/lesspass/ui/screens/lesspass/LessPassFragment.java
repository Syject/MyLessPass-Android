package com.syject.lesspass.ui.screens.lesspass;

import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.syject.data.entities.Options;
import com.syject.lesspass.R;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import rx.Observable;
import rx.Subscription;

@EFragment(R.layout.fragment_less_pass)
public class LessPassFragment extends Fragment implements ILessPassView {

    private static final String TAG = "LessPassFragment";

    @Bean
    LessPassPresenter presenter;

    @InstanceState
    boolean isSettingsExpanded;

    @InstanceState
    boolean isGeneratedPasswordExpanded;

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

    @ViewById(R.id.settings)
    protected ImageButton settingsImageButton;

    @ViewById
    protected ImageButton visibilityImageButton;

    @ViewById
    protected LinearLayout passwordOptionsLinerLayout;

    @ViewById
    protected LinearLayout generatedPasswordLinearLayout;

    @ViewById(R.id.mandatory_error)
    protected TextView mandatoryErrorTextView;

    @ViewById(R.id.save_as_default)
    protected Button saveAsDefaultButton;

    private Subscription subscription;

    @AfterInject
    protected void afterInject() {
        presenter.setView(this);
    }

    @AfterViews
    protected void initViews() {

        presenter.initFields();

        togglePasswordOptions(isSettingsExpanded);
        toggleGeneratedPassword(isGeneratedPasswordExpanded);

        RxView.clicks(visibilityImageButton)
                .map(b -> isGeneratedPasswordExpanded = !isGeneratedPasswordExpanded)
                .subscribe(this::toggleGeneratedPassword);

        RxView.clicks(settingsImageButton)
                .map(b -> isSettingsExpanded = !isSettingsExpanded)
                .subscribe(this::togglePasswordOptions);

        subscription = Observable.combineLatest(
                RxTextView.textChanges(site),
                RxTextView.textChanges(login),
                RxTextView.textChanges(masterPassword),
                RxTextView.textChanges(length),
                RxTextView.textChanges(counter),
                RxCompoundButton.checkedChanges(hasLowerCaseLitters),
                RxCompoundButton.checkedChanges(hasAppearCaseLitters),
                RxCompoundButton.checkedChanges(hasNumbers),
                RxCompoundButton.checkedChanges(hasSymbols),

                (s, l, mp, len, c, lcl, acl, n, sym) -> null
        )
                .subscribe(n -> {
                    resetPasswordGenerated(false);
                    presenter.checkOptionsSaved();
                });
                /*.flatMap(v -> presenter.generatePassword().subscribeOn(Schedulers.newThread()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setPassword);*/
    }

    @Click(R.id.copy_button)
    void copyPassword() {
        presenter.copyToClipboard();
    }

    @Click
    void generate() {
        presenter.generatePassword();
    }

    @Click(R.id.save_as_default)
    void saveAsDefault() {
        presenter.saveOptionsAsDefault();
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
    public String getLength() {
        return length.getText().toString();
    }

    @Override
    public String getCounter() {
        return counter.getText().toString();
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
    public void onValidationFailed() {
        mandatoryErrorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onValidationSuccess() {
        mandatoryErrorTextView.setVisibility(View.GONE);
    }

    @Override
    public void onPasswordGenerated(String password) {
        generate.setVisibility(View.GONE);
        generatedPasswordLinearLayout.setVisibility(View.VISIBLE);
        setPassword(password);
    }

    @Override
    public void resetPasswordGenerated(boolean withMasterPass) {
        if (generatedPasswordLinearLayout.getVisibility() != View.GONE) {
            generatedPasswordLinearLayout.setVisibility(View.GONE);
            generate.setVisibility(View.VISIBLE);
            if (withMasterPass) {
                masterPassword.setText(null);
            }
        }
    }

    @Override
    public void onOptionsSaved() {
        saveAsDefaultButton.setEnabled(false);
        saveAsDefaultButton.setText(R.string.saved_as_def);
        saveAsDefaultButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_black_18dp, 0, 0, 0);
    }

    @Override
    public void onOptionsNotSaved() {
        saveAsDefaultButton.setEnabled(true);
        saveAsDefaultButton.setText(R.string.save_as_def);
        saveAsDefaultButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    @Override
    public void setOptions(Options options) {
        site.setText(options.getSite());
        login.setText(options.getLogin());
        hasLowerCaseLitters.setChecked(options.isHasLowerCaseLitters());
        hasAppearCaseLitters.setChecked(options.isHasAppearCaseLitters());
        hasNumbers.setChecked(options.isHasNumbers());
        hasSymbols.setChecked(options.isHasSymbols());
        length.setText(String.valueOf(options.getLength()));
        counter.setText(String.valueOf(options.getCounter()));
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

    private void toggleGeneratedPassword(boolean isGeneratedPasswordExpanded) {
        if (isGeneratedPasswordExpanded) {
            password.setTransformationMethod(null);
        } else {
            password.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    private void togglePasswordOptions(boolean isPasswordOprionsExpanded) {
        if (isPasswordOprionsExpanded) {
            passwordOptionsLinerLayout.setVisibility(View.VISIBLE);
        } else {
            passwordOptionsLinerLayout.setVisibility(View.GONE);
        }
    }
}
