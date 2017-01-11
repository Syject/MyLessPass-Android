package com.syject.domain.interactors;

import com.syject.data.entities.Options;

import java.util.List;

import rx.Observable;

public interface IKeysInteractor {

    Observable<Void> saveOptions(Options options);
    Observable<Void> removeOptions(Options options);
    Observable<List<Options>> getOptions();
}
