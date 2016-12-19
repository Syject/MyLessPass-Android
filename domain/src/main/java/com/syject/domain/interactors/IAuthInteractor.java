package com.syject.domain.interactors;

import com.syject.data.api.TokenResponse;

import rx.Observable;

public interface IAuthInteractor {

    Observable<String> login(String email, String password);
}
