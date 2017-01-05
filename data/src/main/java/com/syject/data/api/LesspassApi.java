package com.syject.data.api;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;

public class LesspassApi extends ApiBase implements ILesspassApi {

    private final String basicAuthHeader;
    private final RestApi restApi;

    public LesspassApi(String url, String token) {
        basicAuthHeader = "JWT " + token;
        this.addInterceptor(new AuthInterceptor());
        this.restApi = createRetrofitApi(url, RestApi.class);
    }

    private class AuthInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            return chain.proceed(request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", basicAuthHeader)
                        .build());
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
    public Observable<List<OptionsRequest>> getAllOptions() {
        return restApi.getAllOptions()
                .map(optionsResponse -> optionsResponse.results);
    }

    @Override
    public Observable<OptionsRequest> saveOptions(OptionsRequest optionsRequest) {
        return restApi.saveOptions(optionsRequest);
    }
}
