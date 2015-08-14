package com.neptuo.vocabularyapp.services;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Windows10 on 8/14/2015.
 */
public class ConfigurationStorage {
    private static final String STORAGE = "Storage";
    private static final String _DATA_URL = "dataUrl";

    private SharedPreferences preferences;

    public ConfigurationStorage(Context context) {
        preferences = context.getSharedPreferences(STORAGE, 0);
    }

    public String getDataUrl() {
        return preferences.getString(_DATA_URL, "http://vocabulary.neptuo.com/api/v1/list.xml");
    }

    public void setDataUrl(String dataUrl) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(_DATA_URL, dataUrl);
        editor.commit();
    }
}
