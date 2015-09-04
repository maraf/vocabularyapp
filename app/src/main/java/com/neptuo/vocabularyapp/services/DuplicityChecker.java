package com.neptuo.vocabularyapp.services;

import com.neptuo.vocabularyapp.services.models.DetailItemModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Windows10 on 9/4/2015.
 */
public class DuplicityChecker {
    private HashSet<String> items;
    private List<DetailItemModel> duplicities;

    public DuplicityChecker() {
        items = new HashSet<String>();
        duplicities = new ArrayList<DetailItemModel>();
    }

    public boolean tryAdd(DetailItemModel model) {
        String value = model.getOriginalText() + model.getTranslatedText();
        if(!items.add(value)) {
            duplicities.add(model);
            return false;
        }

        return true;
    }

    public int getCount() {
        return duplicities.size();
    }
}
