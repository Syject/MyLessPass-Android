package com.syject.lesspass.views;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public interface ILessPassView {

    EditText getSiteView();
    EditText getLoginView();
    EditText getMasterPasswordView();
    TextView getPasswordView();

    EditText getLengthView();
    EditText getCounterView();

    CheckBox hasLowerCaseLittersView();
    CheckBox hasAppearCaseLittersView();
    CheckBox hasNumbersView();
    CheckBox hasSymbolsView();

    void onValidationLengthError();
    void onValidationCounterError();
}
