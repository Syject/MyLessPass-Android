package com.syject.domain.interactors;

import com.syject.data.entities.Lesspass;
import com.syject.data.entities.Template;

import rx.Observable;

public interface IPasswordInteractor {

    Observable<String> getPassword(Lesspass lesspass, Template template);
}
