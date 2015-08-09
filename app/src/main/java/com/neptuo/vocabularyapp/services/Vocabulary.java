package com.neptuo.vocabularyapp.services;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class Vocabulary {
    private String originalLanguageCode;
    private String translatedLanguageCode;
    private List<VocabularyItem> items;

    public Vocabulary(String originalLanguageCode, String translatedLanguageCode) {
        this.originalLanguageCode = originalLanguageCode;
        this.translatedLanguageCode = translatedLanguageCode;
        this.items = new ArrayList<VocabularyItem>();
    }

    public List<VocabularyItem> getItems() {
        return items;
    }

    public String getOriginalLanguageCode() {
        return originalLanguageCode;
    }

    public void setOriginalLanguageCode(String originalLanguageCode) {
        this.originalLanguageCode = originalLanguageCode;
    }

    public String getTranslatedLanguageCode() {
        return translatedLanguageCode;
    }

    public void setTranslatedLanguageCode(String translatedLanguageCode) {
        this.translatedLanguageCode = translatedLanguageCode;
    }
}
