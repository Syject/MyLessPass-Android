package com.syject.data.api;

import rx.Observable;

public interface ILesspassApi {

    Observable<String> requestToken(UserRequest userRequest);
    Observable<String> refrechToken(TokenResponse tokenRequest);
}
