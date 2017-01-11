package com.syject.domain.interactors.concrete;

import com.syject.data.api.ILesspassApi;
import com.syject.data.api.LesspassApi;
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

    @AfterInject
    protected void initData() {
        //TODO Replace init of host and gerinng token
        lesspassApi = new LesspassApi("https://lesspass.com", preferences.getToken());
    }

    @Override
    public Observable<Void> saveOptions(Options options) {

        return lesspassApi.getAllOptions()
                .filter(optionsRequests -> {
                    for (Options option: optionsRequests) {
                        if (options.getLogin().equals(option.getLogin())) {
                            return false;
                        }
                    }
                    return true;
                })
                .flatMap(o -> lesspassApi.saveOptions(options))
                .map(optionsRequest -> null);
    }

    @Override
    public Observable<Void> removeOptions(Options options) {
        return null;
    }

    @Override
    public Observable<List<Options>> getOptions() {
        return lesspassApi.getAllOptions()
                .map(optionsRequests -> optionsRequests);
    }
}
