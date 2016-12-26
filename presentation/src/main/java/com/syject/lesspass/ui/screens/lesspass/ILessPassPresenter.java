package com.syject.lesspass.ui.screens.lesspass;

import rx.Observable;

public interface ILessPassPresenter {

    void generatePassword();
    void copyToClipboard();
    void checkOptionsSaved();
    void saveOptionsAsDefault();
    void initFields();
    Observable<Void> signOut();
}
