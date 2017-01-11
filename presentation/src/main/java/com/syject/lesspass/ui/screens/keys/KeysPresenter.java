package com.syject.lesspass.ui.screens.keys;

import android.support.annotation.NonNull;

import com.syject.data.entities.Options;
import com.syject.domain.interactors.IKeysInteractor;
import com.syject.domain.interactors.concrete.KeysInteractor;
import com.syject.lesspass.presenters.IPresenter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import rx.Observable;

@EBean
public class KeysPresenter implements IPresenter<IKeysView>, IKeysPresenter{

    private IKeysView view;

    @Bean(KeysInteractor.class)
    protected IKeysInteractor keysInteractor;

    @Override
    public void setView(@NonNull IKeysView iKeysView) {
        view = iKeysView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public Observable<List<Options>> getOptions() {
        return keysInteractor.getOptions();
    }
}
