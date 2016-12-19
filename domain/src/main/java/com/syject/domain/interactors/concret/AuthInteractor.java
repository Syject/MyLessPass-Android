package com.syject.domain.interactors.concret;

import com.syject.data.api.ILesspassApi;
import com.syject.data.api.LesspassApi;
import com.syject.data.api.UserRequest;
import com.syject.domain.interactors.IAuthInteractor;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;

import rx.Observable;

@EBean
public class AuthInteractor implements IAuthInteractor {

    private ILesspassApi lesspassApi;

    @AfterInject
    protected void initData() {
        lesspassApi = new LesspassApi("https://lesspass.com");
    }

    @Override
    public Observable<String> login(String email, String password) {

        return lesspassApi.requestToken(new UserRequest(email, password));
    }
}
