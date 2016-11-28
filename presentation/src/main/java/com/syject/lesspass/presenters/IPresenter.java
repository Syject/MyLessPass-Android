package com.syject.lesspass.presenters;

import android.support.annotation.NonNull;

public interface IPresenter<TPresenterView> {

    void setView(@NonNull TPresenterView view);

    void resume();

    void pause();

    void destroy();
}
