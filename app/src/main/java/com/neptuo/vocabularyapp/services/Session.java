package com.neptuo.vocabularyapp.services;

import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;
import com.neptuo.vocabularyapp.ui.viewmodels.UserDetailConverter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class Session {
    private DetailModel model;
    private UserStorage userStorage;
    private List<UserDetailItemModel> allItems;
    private HashSet<Integer> usedItems;

    private Random random;

    public Session(DetailModel model, UserStorage userStorage) {
        this.model = model;
        this.userStorage = userStorage;
        this.allItems = UserDetailConverter.map(userStorage, model.getItems());
        this.random = new Random();
        this.usedItems = new HashSet<Integer>();
    }

    public UserDetailItemModel nextRandom() {
        if(usedItems.size() == allItems.size()) {
            usedItems.clear();
        }

        int index = -1;
        do {
            index = random.nextInt(allItems.size());
        } while (!usedItems.add(index));

        return allItems.get(index);
    }
}
