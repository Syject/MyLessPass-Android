package com.syject.data.api;

import rx.Observable;

public class LesspassApi extends ApiBase implements ILesspassApi {

    private final RestApi restApi;

    public LesspassApi(String url) {
        this.restApi = createRetrofitApi(url, RestApi.class);
    }

    @Override
    public Observable<String> requestToken(UserRequest userRequest) {
        return restApi.requestToken(userRequest)
                .map(tokenResponse -> tokenResponse.token);
    }

    @Override
    public Observable<String> refrechToken(TokenResponse tokenRequest) {
        return restApi.refreshToken(tokenRequest)
                .map(tokenResponse -> tokenResponse.token);
    }
}
