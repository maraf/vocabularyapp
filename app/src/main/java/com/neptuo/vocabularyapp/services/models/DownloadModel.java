package com.neptuo.vocabularyapp.services.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class DownloadModel {
    private boolean isSelected;
    private String sourceLanguage;
    private String targetLanguage;
    private List<String> urls;

    public DownloadModel(String sourceLanguage, String targetLanguage) {
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

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }
}
