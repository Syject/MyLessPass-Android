package com.syject.data.api.base;

import com.syject.data.api.entities.OptionsResponse;
import com.syject.data.api.entities.TokenResponse;
import com.syject.data.api.entities.UserRequest;
import com.syject.data.api.entities.UserResponse;
import com.syject.data.entities.Options;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface RestApi {

    @POST("/api/tokens/auth/")
    Observable<TokenResponse> requestToken(@Body UserRequest userRequest);

    @POST("/api/tokens/refresh/")
    Observable<TokenResponse> register(@Body TokenResponse userRequest);

    @POST("/api/auth/register/")
    Observable<UserResponse> register(@Body UserRequest userRequest);

    @POST("/api/passwords/")
    Observable<Options> saveOptions(@Body Options optionsRequest);

    @GET("/api/passwords/")
    Observable<OptionsResponse> getAllOptions();

    @DELETE("/api/passwords/{uuid}")
    Observable<Response<Void>> deleteOption(@Path("uuid") String uuid);
}
