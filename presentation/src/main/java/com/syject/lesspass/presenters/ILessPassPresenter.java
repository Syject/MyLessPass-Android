package com.syject.lesspass.presenters;

import rx.Observable;

public interface ILessPassPresenter {

    void login();
    Observable<String> generatePassword();
    void copyToClipboard();
}
