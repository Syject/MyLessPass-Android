package com.syject.data.preferences;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(SharedPref.Scope.APPLICATION_DEFAULT)
public interface Preferences {

    String options();
    String token();
}
