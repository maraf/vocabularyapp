package com.neptuo.vocabularyapp.services.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class DownloadModel {
    private boolean isSelected;
    private LanguageModel sourceLanguage;
    private LanguageModel targetLanguage;
    private List<String> urls;

    public DownloadModel(LanguageModel sourceLanguage, LanguageModel targetLanguage) {
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.urls = new ArrayList<String>();
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public List<String> getUrls() {
        return urls;
    }

    public LanguageModel getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(LanguageModel sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public LanguageModel getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(LanguageModel targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ sourceLanguage.hashCode() ^ targetLanguage.hashCode() ^ urls.hashCode();
    }
}
