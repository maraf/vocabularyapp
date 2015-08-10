package com.neptuo.vocabularyapp.services.models;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class DetailItemModel {
    private String originalText;
    private String translatedText;
    private String originalDescription;
    private String translatedDescription;

    public DetailItemModel(String originalText, String translatedText, String originalDescription, String translatedDescription) {
        this.originalText = originalText;
        this.translatedText = translatedText;
        this.originalDescription = originalDescription;
        this.translatedDescription = translatedDescription;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public String getOriginalDescription() {
        return originalDescription;
    }

    public void setOriginalDescription(String originalDescription) {
        this.originalDescription = originalDescription;
    }

    public String getTranslatedDescription() {
        return translatedDescription;
    }

    public void setTranslatedDescription(String translatedDescription) {
        this.translatedDescription = translatedDescription;
    }
}
