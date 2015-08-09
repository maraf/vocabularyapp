package com.neptuo.vocabularyapp.services;

import com.neptuo.vocabularyapp.services.models.DefinitionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class ServiceProvider {


    private static List<DefinitionModel> definitions = new ArrayList<DefinitionModel>();

    public static List<DefinitionModel> getDefinitions() {
        return definitions;
    }


    private static VocabularyService service;

    public static VocabularyService getVocabulary() {
        if(service == null) {
            service = new VocabularyService();
        }

        return service;
    }
}
