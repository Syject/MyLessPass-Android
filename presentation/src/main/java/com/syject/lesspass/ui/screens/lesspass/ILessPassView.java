package com.syject.lesspass.ui.screens.lesspass;

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
    void onPasswordGenerated(String password);
    void resetPasswordGenerated();
}
