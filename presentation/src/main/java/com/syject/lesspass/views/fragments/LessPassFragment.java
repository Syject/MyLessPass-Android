package com.syject.lesspass.views.fragments;

import android.support.v4.app.Fragment;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.syject.lesspass.R;
import com.syject.lesspass.presenters.LessPassPresenter;
import com.syject.lesspass.views.ILessPassView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@OptionsMenu(R.menu.fragment_less_pass)
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

    @AfterInject
    protected void afterInject() {
        presenter.setView(this);
    }

    @AfterViews
    protected void initViews() {
        presenter.initView();
    }

    @OptionsItem(R.id.action_login)
    void login() {
        presenter.login();
    }

    @Click(R.id.copy_button)
    void copyPassword() {
        presenter.copyToClipboard();
    }

    //@Override
    public void onValidationFailed(Exception e) {

    }

    public static LessPassFragment newInstance() {
        return LessPassFragment_.builder().build();
    }

    @Override
    public CheckBox hasLowerCaseLittersView() {
        return hasLowerCaseLitters;
    }

    @Override
    public CheckBox hasAppearCaseLittersView() {
        return hasAppearCaseLitters;
    }

    @Override
    public CheckBox hasNumbersView() {
        return hasNumbers;
    }

    @Override
    public CheckBox hasSymbolsView() {
        return hasSymbols;
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
    public EditText getLengthView() {
        return length;
    }

    @Override
    public EditText getCounterView() {
        return counter;
    }

    @Override
    public EditText getSiteView() {
        return site;
    }

    @Override
    public EditText getLoginView() {
        return login;
    }

    @Override
    public EditText getMasterPasswordView() {
        return masterPassword;
    }

    @Override
    public TextView getPasswordView() {
        return password;
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
    }

    /*public static byte[] getEncryptedPassword(String password, byte[] salt,  int iterations,  int derivedKeyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength * 8);

        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        return f.generateSecret(spec).getEncoded();
    }*/
}
