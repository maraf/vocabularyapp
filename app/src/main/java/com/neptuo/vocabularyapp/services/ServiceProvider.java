package com.neptuo.vocabularyapp.services;

import android.content.Context;

import com.neptuo.vocabularyapp.data.DbContext;
import com.neptuo.vocabularyapp.data.UserGuessRepository;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.DownloadModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class ServiceProvider {

    private static List<DownloadModel> definitions = new ArrayList<DownloadModel>();

    public static List<DownloadModel> getDefinitions() {
        return definitions;
    }

    private static UserStorage userStorage;
    private static ConfigurationStorage configurationStorage;
    private static List<DetailModel> details = new ArrayList<DetailModel>();

    public static UserStorage getUserStorage() {
        return userStorage;
    }

    public static ConfigurationStorage getConfigurationStorage() {
        return configurationStorage;
    }

    public static List<DetailModel> getDetails() {
        return details;
    }

    public static void initialize(Context context) {
        userStorage = new UserStorage(new UserGuessRepository(new DbContext(context).getWritableDatabase()));
        configurationStorage = new ConfigurationStorage(context);
    }
}
