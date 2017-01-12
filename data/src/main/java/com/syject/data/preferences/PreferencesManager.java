package com.syject.data.preferences;

import android.content.Context;

import com.google.gson.Gson;
import com.syject.data.entities.LesspassSessionInfo;
import com.syject.data.entities.Options;

import org.androidannotations.annotations.EBean;

@EBean
public class PreferencesManager {

    protected final Gson gson;
    protected final Preferences_ preferences;

    protected PreferencesManager(Context context) {
        gson = new Gson();
        preferences = new Preferences_(context);
    }

    public void setOptions(Options options) {
        preferences.edit().options().put(gson.toJson(options)).apply();
    }

    public Options getOptions() {
        return gson.fromJson(preferences.options().get(), Options.class);
    }

    public void setSignIn(boolean isSignIn) {
        preferences.edit().isSignedIn().put(isSignIn).apply();
    }

    public boolean isSignIn() {
        return preferences.isSignedIn().get();
    }

    public void setLesspassSessionInfo(LesspassSessionInfo sessionInfo) {
        preferences.edit().sessionInfo().put(gson.toJson(sessionInfo)).apply();
    }

    public LesspassSessionInfo getLesspassSessionInfo() {
        return gson.fromJson(preferences.sessionInfo().get(), LesspassSessionInfo.class);
    }
}
