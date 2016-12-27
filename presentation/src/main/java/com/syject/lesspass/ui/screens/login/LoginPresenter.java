package com.syject.lesspass.ui.screens.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.syject.data.preferences.PreferencesManager;
import com.syject.domain.interactors.IAuthInteractor;
import com.syject.domain.interactors.concrete.AuthInteractor;
import com.syject.lesspass.presenters.IPresenter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EBean
public class LoginPresenter implements ILoginPresenter, IPresenter<ILoginView> {

    @Bean(AuthInteractor.class)
    protected IAuthInteractor authInteractor;

    @Bean
    protected PreferencesManager preferences;

    private ILoginView view;

    private Context context;

    public LoginPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void login() {

        if (!isFormValid()) {
            view.onSignInFail(new Throwable("Email or Password is empty!"));
            return;
        }

        view.onSigningIn();
        authInteractor.login(view.getEmail(), view.getLesspassPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v ->
                    view.onSignInSuccess(), view::onSignInFail);
    }

    @Override
    public void register() {
        if (!isFormValid()) {
            view.onRegisterFail(new Throwable("Email or Password is empty!"));
            return;
        }

        view.onRegistering();
        authInteractor.register(view.getEmail(), view.getLesspassPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v ->
                        view.onRegisterSuccess(), view::onRegisterFail);
    }

    @Override
    public void rememberPassword() {

    }

    @Override
    public void setView(@NonNull ILoginView loginView) {
        this.view = loginView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.view = null;
    }

    private boolean isFormValid() {
        return !(view.getEmail().equals("") || view.getLesspassPassword().equals(""));
    }
}
