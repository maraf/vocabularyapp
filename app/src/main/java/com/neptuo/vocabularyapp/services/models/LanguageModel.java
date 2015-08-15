package com.neptuo.vocabularyapp.services.models;

import com.neptuo.vocabularyapp.data.Sql;

import java.util.Locale;

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

    public Locale getLocale() {
        if(code.length() == 2)
            return new Locale(code);
        else if(code.length() == 5) {
            String language = code.substring(0, 2);
            String country = code.substring(3, 5);
            return new Locale(language, country);
        } else {
            return Locale.US;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ name.hashCode() ^ code.hashCode();
    }
}
