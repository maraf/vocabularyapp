package com.neptuo.vocabularyapp.services;

import android.content.Context;

import com.neptuo.vocabularyapp.data.DbContext;
import com.neptuo.vocabularyapp.data.UserGuessRepository;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.DownloadModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class ServiceProvider {

    private static boolean isInitialized;

    private static UserStorage userStorage;
    private static ConfigurationStorage configurationStorage;
    private static List<DetailModel> details = new ArrayList<DetailModel>();
    private static Set<String> tags = new HashSet<String>();

    public static UserStorage getUserStorage() {
        return userStorage;
    }

    public static ConfigurationStorage getConfigurationStorage() {
        return configurationStorage;
    }

    public static List<DetailModel> getDetails() {
        return details;
    }

    public static Set<String> getTags() {
        return tags;
    }

    public static boolean tryInitialize(Context context) {
        if(isInitialized)
            return false;

        userStorage = new UserStorage(new UserGuessRepository(new DbContext(context).getWritableDatabase()));
        configurationStorage = new ConfigurationStorage(context);
        return isInitialized = true;
    }
}
