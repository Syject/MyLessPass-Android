package com.syject.lesspass.ui.screens.lesspass;

public interface ILessPassPresenter {

    void login();
    void generatePassword();
    void copyToClipboard();
    void checkOptionsSaved();
    void saveOptionsAsDefault();
    void initFields();
}
