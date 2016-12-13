package com.syject.lesspass.presenters;

import rx.Observable;

public interface ILessPassPresenter {

    void login();
    void generatePassword();
    void copyToClipboard();
}
