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
        items.add(new VocabularyItem("Přechod pro chodce", "Fußgängerübergang", "Přechod pro chodce je místo, kde mohou chodci bezpečně přecházet."));
        items.add(new VocabularyItem("Kočka", "Katze", "Milý malý chlupatý tvor."));
        items.add(new VocabularyItem("Slepé střevo", "Anhang", "Nepotřebný kus lidského těla."));
        items.add(new VocabularyItem("Kachna", "Ente", "Chutný vodní pták"));
    }

    public VocabularyItem nextRandom() {
        int index = random.nextInt(items.size());
        return items.get(index);
    }
}
