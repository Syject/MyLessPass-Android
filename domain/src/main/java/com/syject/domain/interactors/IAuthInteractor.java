package com.syject.domain.interactors;

import rx.Observable;

public interface IAuthInteractor {

    Observable<Void> login(String email, String password);
    Observable<Void> register(String email, String password);
    Observable<Void> signOut();
}
