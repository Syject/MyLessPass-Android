package com.syject.data.preferences;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(SharedPref.Scope.APPLICATION_DEFAULT)
public interface Preferences {

    @DefaultBoolean(false)
    boolean isSignedIn();
    String options();
    String token();
}
