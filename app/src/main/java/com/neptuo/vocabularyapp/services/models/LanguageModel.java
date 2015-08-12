package com.neptuo.vocabularyapp.services.models;

/**
 * Created by Windows10 on 8/12/2015.
 */
public class LanguageModel {
    private String name;
    private String code;

    public LanguageModel(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
