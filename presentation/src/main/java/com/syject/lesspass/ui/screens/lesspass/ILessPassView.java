package com.syject.lesspass.ui.screens.lesspass;

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

    void onValidationLengthError();
    void onValidationCounterError();
    void onValidationFailed();
    void onValidationSuccess();
    void onPasswordGenerating();
    void onPasswordGenerated(String password);
    void resetPasswordGenerated(boolean withMasterPass);
    void onOptionsSave(boolean isSaved);

    void setOptions(Options options);
}
