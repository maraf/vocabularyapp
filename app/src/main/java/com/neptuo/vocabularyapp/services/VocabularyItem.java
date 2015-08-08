package com.neptuo.vocabularyapp.services;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class VocabularyItem {
    private String originalText;
    private String translatedText;
    private String translatedDescription;

    public VocabularyItem(String originalText, String translatedText, String translatedDescription) {
        this.originalText = originalText;
        this.translatedText = translatedText;
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

    public String getTranslatedDescription() {
        return translatedDescription;
    }

    public void setTranslatedDescription(String translatedDescription) {
        this.translatedDescription = translatedDescription;
    }
}
