package com.neptuo.vocabularyapp.services.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class DetailModel {
    private String originalLanguageCode;
    private String translatedLanguageCode;
    private List<DetailItemModel> items;

    public DetailModel(String originalLanguageCode, String translatedLanguageCode) {
        this.originalLanguageCode = originalLanguageCode;
        this.translatedLanguageCode = translatedLanguageCode;
        this.items = new ArrayList<DetailItemModel>();
    }

    public List<DetailItemModel> getItems() {
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
