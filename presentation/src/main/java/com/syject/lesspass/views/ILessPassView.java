package com.syject.lesspass.views;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public interface ILessPassView {

    String getSite();
    String getLogin();
    String getMasterPassword();
    String getPassword();
    void setPassword(String pass);

    int getLength();
    int getCounter();

    boolean hasLowerCaseLitters();
    boolean hasAppearCaseLitters();
    boolean hasNumbers();
    boolean hasSymbols();

    void onValidationLengthError();
    void onValidationCounterError();
    void onValidationFailed();
    void onPasswordGenerated(String password);
}
