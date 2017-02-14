package com.syject.lesspass.ui.screens.lesspass;

import android.support.annotation.StringRes;

import com.syject.data.entities.Options;

public interface ILessPassView {

    String getSite();
    String getLogin();
    String getMasterPassword();
    String getPassword();
    String getLength();
    String getCounter();

    void setPassword(String pass);

    boolean hasLowerCaseLitters();
    boolean hasAppearCaseLitters();
    boolean hasNumbers();
    boolean hasSymbols();

    void onLengthError(@StringRes int stringResError);
    void onCounterError(@StringRes int stringResError);
    void onValidationFailed();
    void onValidationSuccess();
    void onPasswordGenerating();
    void onPasswordGenerated(String password);
    void resetPasswordGenerated(boolean withMasterPass);
    void onCopiedToClipboard();
    void onOptionsSave(boolean isSaved);

    void setOptions(Options options);
}
