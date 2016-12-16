package com.syject.domain.interactors.concret;

import android.util.Log;

import com.syject.data.api.ILesspassApi;
import com.syject.data.api.LesspassApi;
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
    public Observable<Void> login(String email, String password) {
        return lesspassApi.requestToken(email, password)
                .flatMap(t -> {
                    Log.d("AuthInteractor", t);
                    return null;
                });
    }
}
