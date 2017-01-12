package com.syject.data.api;

import com.syject.data.entities.LesspassSessionInfo;

public interface ISessionInfoHolder {

    LesspassSessionInfo getSessionInfo();
    void setSessionInfoHolder(LesspassSessionInfo sessionInfo);
}
