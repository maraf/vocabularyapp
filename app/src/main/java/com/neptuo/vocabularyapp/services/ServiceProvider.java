package com.neptuo.vocabularyapp.services;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class ServiceProvider {

    private static VocabularyService service;

    public static VocabularyService getVocabulary() {
        if(service == null) {
            service = new VocabularyService();
        }

        return service;
    }
}
