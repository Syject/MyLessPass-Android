package com.syject.domain.interactors.concrete;

import com.syject.data.api.ILesspassApi;
import com.syject.data.api.base.ApiFactory;
import com.syject.data.api.base.IApiFactory;
import com.syject.data.api.entities.UserRequest;
import com.syject.data.entities.LesspassSessionInfo;
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

    @Bean(ApiFactory.class)
    protected IApiFactory apiFactory;

    @AfterInject
    protected void initData() {
        lesspassApi = apiFactory.createLesspassApi();
    }

    @Override
    public Observable<Void> login(String email, String password) {

        return lesspassApi.requestToken(new UserRequest(email, password))
                .map(token -> {
                    preferences.setLesspassSessionInfo(new LesspassSessionInfo(token));
                    preferences.setSignIn(true);
                    return null;
                });
    }

    @Override
    public Observable<Void> register(String email, String password) {
        return lesspassApi.registerUser(new UserRequest(email, password))
                /*TODO : add check response
                .flatMap(response -> {
                    checkResponse(response.isAuthenticated,
                            "Bad username or password provided", AuthenticateException.ERROR_CODE_BAD_CREDENTIALS);
                    return response;
                })*/
                .flatMap(userResponse -> login(email, password));
    }

    @Override
    public Observable<Void> signOut() {
        return Observable.just(null)
                .map(n -> {
                    preferences.setLesspassSessionInfo(null);
                    preferences.setSignIn(false);
                    return null;
                });
    }
}
