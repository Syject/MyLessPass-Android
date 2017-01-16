package com.syject.lesspass.ui.screens.lesspass;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
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
import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.syject.data.entities.Options;
import com.syject.data.preferences.PreferencesManager;
import com.syject.domain.utils.SystemUtils;
import com.syject.lesspass.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import rx.Observable;
import rx.Subscription;

@OptionsMenu(R.menu.fragment_less_pass)
@EFragment(R.layout.fragment_less_pass)
public class LessPassFragment extends Fragment implements ILessPassView {

    @Bean
    protected PreferencesManager preferences;

    @Bean
    LessPassPresenter presenter;

    @Bean
    protected SystemUtils systemUtils;

    @InstanceState
    boolean isSettingsExpanded;

    @InstanceState
    boolean isGeneratedPasswordExpanded;

    @ViewById
    EditText siteEditText;

    @ViewById
    EditText loginEditText;

    @ViewById
    EditText masterPasswordEditText;

    @ViewById
    TextView password;

    @ViewById
    CheckBox hasLowerCaseLitters;

    @ViewById
    CheckBox hasAppearCaseLitters;

    @ViewById
    CheckBox hasNumbers;

    @ViewById
    CheckBox hasSymbols;

    @ViewById
    EditText length;

    @ViewById
    EditText counter;

    @ViewById
    Button generate;

    @ViewById
    ImageButton settingsImageButton;

    @ViewById
    ImageButton visibilityImageButton;

    @ViewById
    LinearLayout passwordOptionsLinerLayout;

    @ViewById
    LinearLayout generatedPasswordLinearLayout;

    @ViewById
    CoordinatorLayout coordinatorLayout;

    @ViewById
    TextView mandatoryErrorTextView;

    @ViewById
    Button saveAsDefaultButton;

    @ViewById
    ProgressBar progressBar;

    Subscription subscription;

    private Menu menu;

    @AfterViews
    protected void initViews() {

        presenter.setView(this);

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
                RxTextView.textChanges(siteEditText),
                RxTextView.textChanges(loginEditText),
                RxTextView.textChanges(masterPasswordEditText),
                RxTextView.textChanges(length),
                RxTextView.textChanges(counter),
                RxCompoundButton.checkedChanges(hasLowerCaseLitters),
                RxCompoundButton.checkedChanges(hasAppearCaseLitters),
                RxCompoundButton.checkedChanges(hasNumbers),
                RxCompoundButton.checkedChanges(hasSymbols),

                (s, l, mp, len, c, lcl, acl, n, sym) -> null
        )
                .subscribe(n -> {
                    resetPasswordGenerated(true);
                    presenter.checkOptionsSaved();
                });
    }

    public interface OnActionSelectedListener {
        void onSignInSelected();
        void onSignOut();
        void onKeysSelected();
    }

    @OptionsItem(R.id.action_sign_in)
    void signIn() {
        getActivityCallback().onSignInSelected();
    }

    @OptionsItem(R.id.action_sign_out)
    void signOut() {
        presenter.signOut()
                .subscribe(n -> getActivityCallback().onSignOut());
    }

    @OptionsItem(R.id.action_save)
    void save() {
        presenter.save()
                .subscribe(b -> afterSaveOptions(b ? R.string.options_saved : R.string.options_exist));
    }

    private void afterSaveOptions(Integer res) {
        Snackbar.make(coordinatorLayout, res, Snackbar.LENGTH_SHORT).show();
        menu.findItem(R.id.action_save).setVisible(false);
    }

    @OptionsItem(R.id.action_keys)
    void keys() {
        getActivityCallback().onKeysSelected();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        boolean isSignIn = preferences.isSignIn();
        menu.findItem(R.id.action_keys).setVisible(isSignIn);
        menu.findItem(R.id.action_sign_out).setVisible(isSignIn);
        menu.findItem(R.id.action_save).setVisible(false);
        menu.findItem(R.id.action_sign_in).setVisible(!isSignIn);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Click(R.id.copy_button)
    void copyPassword() {
        presenter.copyToClipboard();
    }

    @Click
    void generate() {
        presenter.generatePassword();
    }

    @Click(R.id.save_as_default_button)
    void saveAsDefault() {
        presenter.saveOptionsAsDefault();
    }

    public String getSite() {
        return siteEditText.getText().toString();
    }

    public String getLogin() {
        return loginEditText.getText().toString();
    }

    public String getMasterPassword() {
        return masterPasswordEditText.getText().toString();
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
        masterPasswordEditText.setFocusableInTouchMode(true);
        masterPasswordEditText.requestFocus();
        systemUtils.closeKeyboard(masterPasswordEditText);
    }

    @Override
    public void onPasswordGenerating() {
        generate.setText(null);
        generate.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPasswordGenerated(String password) {
        generate.setText(R.string.generate);
        generate.setVisibility(View.GONE);
        progressBar.setVisibility(View.INVISIBLE);
        generatedPasswordLinearLayout.setVisibility(View.VISIBLE);
        setPassword(password);
        if (preferences.isSignIn()) {
            menu.findItem(R.id.action_save).setVisible(true);
        }
    }

    @Override
    public void resetPasswordGenerated(boolean withMasterPass) {
        if (generatedPasswordLinearLayout.getVisibility() != View.GONE) {
            generatedPasswordLinearLayout.setVisibility(View.GONE);
            generate.setVisibility(View.VISIBLE);
            generate.setEnabled(true);
            if (withMasterPass) {
                masterPasswordEditText.setText(null);
            }
            menu.findItem(R.id.action_save).setVisible(false);
        }
    }

    @Override
    public void onCopiedToClipboard() {
        Snackbar.make(coordinatorLayout, R.string.copied, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onOptionsSave(boolean isSaved) {
        saveAsDefaultButton.setEnabled(!isSaved);
        saveAsDefaultButton.setText(isSaved ? R.string.saved_as_def : R.string.save_as_def);
        saveAsDefaultButton.setCompoundDrawablesWithIntrinsicBounds(isSaved ? R.drawable.ic_done_black_18dp : 0, 0, 0, 0);
    }

    @Override
    public void setOptions(Options options) {
        siteEditText.setText(options.getSite());
        loginEditText.setText(options.getLogin());
        hasLowerCaseLitters.setChecked(options.isHasLowerCaseLitters());
        hasAppearCaseLitters.setChecked(options.isHasUpperCaseLitters());
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

    private OnActionSelectedListener getActivityCallback() {
        return (OnActionSelectedListener) getActivity();
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
