package com.syject.data.api;

import rx.Observable;

public interface ILesspassApi {

    Observable<String> requestToken(UserRequest userRequest);
    Observable<String> refreshToken(TokenResponse tokenRequest);
    Observable<UserResponse> registerUser(UserRequest tokenRequest);
}
