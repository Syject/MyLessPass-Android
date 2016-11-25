package com.syject.lesspass;

import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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

    public static byte[] getEncryptedPassword(String password, byte[] salt,  int iterations,  int derivedKeyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength * 8);

        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        return f.generateSecret(spec).getEncoded();
    }

    @Click(R.id.copy_button)
    void copyPassword() {


        /*if (password != null && !password.getText().equals("")) {
            Toast.makeText(getActivity(), "Copied!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Generate password firstly!", Toast.LENGTH_SHORT).show();
        }*/
    }

    public static LessPassFragment newInstance() {
        return LessPassFragment_.builder().build();
    }
}
