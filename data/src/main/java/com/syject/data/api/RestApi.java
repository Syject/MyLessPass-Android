package com.syject.data.api;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface RestApi {

    @POST("/api/tokens/auth/")
    Observable<TokenResponse> requestToken(@Body UserRequest userRequest);

    @POST("/api/tokens/refresh/")
    Observable<TokenResponse> register(@Body TokenResponse userRequest);

    @POST("/api/auth/register/")
    Observable<UserResponse> register(@Body UserRequest userRequest);
}
