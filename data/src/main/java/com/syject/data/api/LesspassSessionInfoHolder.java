package com.syject.data.api;

import com.syject.data.entities.LesspassSessionInfo;
import com.syject.data.preferences.PreferencesManager;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

@EBean
public class LesspassSessionInfoHolder implements ISessionInfoHolder {

    @Bean
    PreferencesManager preferences;

    @Override
    public LesspassSessionInfo getSessionInfo() {
        return preferences.getLesspassSessionInfo();
    }

    @Override
    public void setSessionInfoHolder(LesspassSessionInfo sessionInfo) {
        preferences.setLesspassSessionInfo(sessionInfo);
    }
}
