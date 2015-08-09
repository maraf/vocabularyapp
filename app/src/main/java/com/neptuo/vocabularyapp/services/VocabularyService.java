package com.neptuo.vocabularyapp.services;

import com.neptuo.vocabularyapp.services.models.DetailItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class VocabularyService {

    private Random random;
    private List<DetailItemModel> items;

    public VocabularyService() {
        random = new Random();
        items = new ArrayList<DetailItemModel>();
    }

    public DetailItemModel nextRandom() {
        int index = random.nextInt(items.size());
        return items.get(index);
    }

    public List<DetailItemModel> getItems() {
        return items;
    }
}
