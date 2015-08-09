package com.neptuo.vocabularyapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class VocabularyService {

    private Random random;
    private List<VocabularyItem> items;

    public VocabularyService() {
        random = new Random();
        items = new ArrayList<VocabularyItem>();
    }

    public VocabularyItem nextRandom() {
        int index = random.nextInt(items.size());
        return items.get(index);
    }

    public List<VocabularyItem> getItems() {
        return items;
    }
}
