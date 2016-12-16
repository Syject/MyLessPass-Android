package com.syject.data.api;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface RestApi {

    @POST("/api/tokens/auth/")
    Observable<String> requestToken(@Query("email") String email, @Query("password") String password);
}
