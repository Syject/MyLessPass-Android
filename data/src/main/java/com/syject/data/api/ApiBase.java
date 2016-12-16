package com.syject.data.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBase {

    private static final long TIMEOUT_SECONDS = 30;

    private List<Interceptor> interceptors;

    public ApiBase() {
        this.interceptors = new ArrayList<>();
    }

    protected void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    protected void addLoggingInterceptor(HttpLoggingInterceptor.Level level) {
        addInterceptor(new HttpLoggingInterceptor().setLevel(level));
    }

    protected <T> T createRetrofitApi(String url, Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideHttpClient())
                .build();

        return retrofit.create(service);
    }

    private OkHttpClient provideHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS);

        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }

        return builder.build();
    }
}
