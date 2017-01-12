package com.syject.domain.interactors.concrete;

import com.syject.data.api.ILesspassApi;
import com.syject.data.api.base.ApiFactory;
import com.syject.data.api.base.IApiFactory;
import com.syject.data.entities.Options;
import com.syject.data.preferences.PreferencesManager;
import com.syject.domain.interactors.IKeysInteractor;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import rx.Observable;

@EBean
public class KeysInteractor implements IKeysInteractor {

    private ILesspassApi lesspassApi;

    @Bean
    protected PreferencesManager preferences;

    @Bean(ApiFactory.class)
    protected IApiFactory apiFactory;

    @AfterInject
    protected void initData() {
        lesspassApi = apiFactory.createLesspassApi();
    }

    @Override
    public Observable<Boolean> saveOptions(Options options) {

        //TODO check options before save (if exist)
        return lesspassApi.saveOptions(options)
                .map(optionsRequest -> true);
    }

    @Override
    public Observable<Void> removeOptions(Options options) {
        return lesspassApi.deleteOption(options.getUuid());
    }

    @Override
    public Observable<List<Options>> getOptions() {
        return lesspassApi.getAllOptions()
                .map(optionsRequests -> optionsRequests);
    }
}
