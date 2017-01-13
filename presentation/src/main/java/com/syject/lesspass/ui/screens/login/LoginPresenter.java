package com.syject.lesspass.ui.screens.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.syject.data.preferences.PreferencesManager;
import com.syject.domain.interactors.IAuthInteractor;
import com.syject.domain.interactors.concrete.AuthInteractor;
import com.syject.lesspass.presenters.IPresenter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;
import rx.Subscription;
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

    private Subscription subscription;

    public LoginPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void login() {

        subscription = Observable.just(true)
                .filter(n -> {
                    if (TextUtils.isEmpty(view.getEmail())) {
                        view.onEmailEmpty();
                        n = false;
                    }
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(view.getEmail()).matches()) {
                        view.onEmailInvalid();
                        n = false;
                    }
                    if (TextUtils.isEmpty(view.getLesspassPassword())) {
                        view.onPasswordEmpty();
                        n = false;
                    }
                    return n;
                })
                .subscribe(n -> {
                    view.onSigningIn();
                    authInteractor.login(view.getEmail(), view.getLesspassPassword())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(v -> view.onSignInSuccess(), view::onSignInFail);
                });
    }

    @Override
    public void register() {

        subscription = Observable.just(true)
                .filter(n -> {
                    if (TextUtils.isEmpty(view.getEmail())) {
                        view.onEmailEmpty();
                        n = false;
                    }
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(view.getEmail()).matches()) {
                        view.onEmailInvalid();
                        n = false;
                    }
                    if (TextUtils.isEmpty(view.getLesspassPassword())) {
                        view.onPasswordEmpty();
                        n = false;
                    }
                    return n;
                })
                .subscribe(n -> {
                    view.onRegistering();
                    authInteractor.register(view.getEmail(), view.getLesspassPassword())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(v -> view.onRegisterSuccess(), view::onRegisterFail);
                });
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
        if (subscription != null) subscription.unsubscribe();
    }
}
