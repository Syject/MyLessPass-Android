package com.syject.domain.interactors;

import com.syject.data.entities.Lesspass;
import com.syject.data.entities.Template;

import rx.Observable;

public interface IPasswordInteractor {

    String getPassword(Lesspass lesspass, Template template);
}
