package com.syject.lesspass.ui.screens.login;

public interface ILoginView {
    String getEmail();
    String getLesspassPassword();

    void onSignInSuccess();
    void onSignInFail(Throwable throwable);
    void onSigningIn();

    void onRegisterSuccess();
    void onRegisterFail(Throwable throwable);
    void onRegistering();

    void onPasswordEmpty();
    void onEmailEmpty();
    void onEmailInvalid();
}
