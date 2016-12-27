package com.syject.domain.interactors.concrete;

import com.syject.data.api.ILesspassApi;
import com.syject.data.api.LesspassApi;
import com.syject.data.api.UserRequest;
import com.syject.data.preferences.PreferencesManager;
import com.syject.domain.interactors.IAuthInteractor;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;

@EBean
public class AuthInteractor implements IAuthInteractor {

    private ILesspassApi lesspassApi;

    @Bean
    protected PreferencesManager preferences;

    @AfterInject
    protected void initData() {
        lesspassApi = new LesspassApi("https://lesspass.com");
    }

    @Override
    public Observable<Void> login(String email, String password) {

        return lesspassApi.requestToken(new UserRequest(email, password))
                .map(token -> {
                    preferences.setToken(token);
                    preferences.setSignIn(true);
                    return null;
                });
    }

    @Override
    public Observable<Void> register(String email, String password) {
        return lesspassApi.registerUser(new UserRequest(email, password))
                .flatMap(userResponse -> login(email, password));
    }

    @Override
    public Observable<Void> signOut() {
        return Observable.just(null)
                .map(n -> {
                    preferences.setToken(null);
                    preferences.setSignIn(false);
                    return null;
                });
    }
}
