package com.syject.domain.interactors.concret;

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
                    return null;
                });
    }
}
