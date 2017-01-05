package com.syject.domain.interactors;

import com.syject.data.api.OptionsRequest;
import com.syject.data.api.TokenResponse;
import com.syject.data.entities.Options;

import rx.Observable;

public interface IAuthInteractor {

    Observable<Void> login(String email, String password);
    Observable<Void> register(String email, String password);
    Observable<Void> signOut();
    Observable<Void> saveOptions(Options options);
}
