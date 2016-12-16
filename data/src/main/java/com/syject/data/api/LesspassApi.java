package com.syject.data.api;

import rx.Observable;

public class LesspassApi extends ApiBase implements ILesspassApi {

    private final RestApi restApi;

    public LesspassApi(String url) {
        this.restApi = createRetrofitApi(url, RestApi.class);
    }

    @Override
    public Observable<String> requestToken(String email, String password) {
        return restApi.requestToken(email, password);
    }
}
