package com.syject.data.api;

import com.syject.data.api.base.ApiBase;
import com.syject.data.api.base.RestApi;
import com.syject.data.api.entities.TokenResponse;
import com.syject.data.api.entities.UserRequest;
import com.syject.data.api.entities.UserResponse;
import com.syject.data.entities.LesspassSessionInfo;
import com.syject.data.entities.Options;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;

public class LesspassApi extends ApiBase implements ILesspassApi {

    private final RestApi restApi;
    private final ISessionInfoHolder sessionHolder;

    public LesspassApi(String url, ISessionInfoHolder sessionHolder) {
        this.sessionHolder = sessionHolder;
        this.addInterceptor(new AuthInterceptor());
        this.restApi = createRetrofitApi(url, RestApi.class);
    }

    private class AuthInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Content-Type", "application/json");

            LesspassSessionInfo sessionInfo = sessionHolder.getSessionInfo();
            if (sessionInfo != null) {
                builder.addHeader("Authorization", sessionInfo.toString());
            }

            return chain.proceed(builder.build());
        }
    }

    @Override
    public Observable<String> requestToken(UserRequest userRequest) {
        return restApi.requestToken(userRequest)
                .map(tokenResponse -> tokenResponse.token);
    }

    @Override
    public Observable<String> refreshToken(TokenResponse tokenRequest) {
        return restApi.register(tokenRequest)
                .map(tokenResponse -> tokenResponse.token);
    }

    @Override
    public Observable<UserResponse> registerUser(UserRequest userRequest) {
        return restApi.register(userRequest);
    }

    @Override
    public Observable<List<Options>> getAllOptions() {
        return restApi.getAllOptions()
                .map(optionsResponse -> optionsResponse.results);
    }

    @Override
    public Observable<Void> deleteOption(String uuid) {
        return restApi.deleteOption(uuid)
                .map(r -> null);
    }

    @Override
    public Observable<Options> saveOptions(Options optionsRequest) {
        return restApi.saveOptions(optionsRequest);
    }
}
