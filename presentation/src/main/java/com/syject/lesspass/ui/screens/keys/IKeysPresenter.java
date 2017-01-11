package com.syject.lesspass.ui.screens.keys;

import com.syject.data.entities.Options;

import java.util.List;

import rx.Observable;

public interface IKeysPresenter {

    Observable<List<Options>> getOptions();
}
