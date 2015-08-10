package com.neptuo.vocabularyapp.services;

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


    private static VocabularyService service;

    public static VocabularyService getVocabulary() {
        if(service == null) {
            service = new VocabularyService();
        }

        return service;
    }



    private static UserStorage userStorage = new UserStorage();
    private static List<DetailModel> details = new ArrayList<DetailModel>();

    public  static UserStorage getUserStorage() {
        return userStorage;
    }

    public static List<DetailModel> getDetails() {
        return details;
    }
}
