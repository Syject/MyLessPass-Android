package com.syject.data.preferences;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.syject.data.entities.Options;

public class OptionsPreferences {

    private static final String PREF_OPTIONS = "options";

    protected static final Gson gson = new Gson();

    public static void setOptions(Context context, Options options) {
        String json = gson.toJson(options);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_OPTIONS, json)
                .apply();
    }

    public static Options getOptions(Context context) {
        String json = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_OPTIONS, null);

         return gson.fromJson(json, Options.class);
    }
}
