package com.syject.lesspass.ui.screens.lesspass;

public interface ILessPassPresenter {

    void generatePassword();
    void copyToClipboard();
    void checkOptionsSaved();
    void saveOptionsAsDefault();
    void initFields();
}
