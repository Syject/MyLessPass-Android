package com.syject.data.api;

import com.syject.data.api.entities.TokenResponse;
import com.syject.data.api.entities.UserRequest;
import com.syject.data.api.entities.UserResponse;
import com.syject.data.entities.Options;

import java.util.List;

import rx.Observable;

public interface ILesspassApi {

    Observable<String> requestToken(UserRequest userRequest);
    Observable<String> refreshToken(TokenResponse tokenRequest);
    Observable<UserResponse> registerUser(UserRequest tokenRequest);
    Observable<Options> saveOptions(Options optionsRequest);
    Observable<List<Options>> getAllOptions();
    Observable<Void> deleteOption(String uuid);
}
