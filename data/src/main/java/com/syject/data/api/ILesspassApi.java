package com.syject.data.api;

import rx.Observable;

public interface ILesspassApi {

    Observable<String> requestToken(String email, String password);
}
