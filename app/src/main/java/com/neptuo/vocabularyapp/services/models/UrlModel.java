package com.neptuo.vocabularyapp.services.models;

/**
 * Created by Windows10 on 8/15/2015.
 */
public class UrlModel {
    private String name;
    private String value;

    public UrlModel(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
