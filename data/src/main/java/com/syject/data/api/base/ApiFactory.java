package com.syject.data.api.base;

import com.syject.data.api.ILesspassApi;
import com.syject.data.api.ISessionInfoHolder;
import com.syject.data.api.LesspassApi;
import com.syject.data.api.LesspassSessionInfoHolder;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

@EBean
public class ApiFactory implements IApiFactory {

    private static final String LESSPASS_HOST = "https://lesspass.com";

    @Bean(LesspassSessionInfoHolder.class)
    protected ISessionInfoHolder sessionHolder;

    @Override
    public ILesspassApi createLesspassApi() {
        return new LesspassApi(LESSPASS_HOST, sessionHolder);
    }
}
