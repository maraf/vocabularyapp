package com.neptuo.vocabularyapp.services;

import android.content.Context;

import com.neptuo.vocabularyapp.data.UserGuessRepository;
import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.services.models.UserGuessModel;

import java.util.HashMap;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class UserStorage {
    private UserGuessRepository repository;
    private HashMap<String, UserGuessModel> storage = new HashMap<String, UserGuessModel>();

    public UserStorage(UserGuessRepository repository) {
        this.repository = repository;
    }

    public UserGuessModel find(DetailItemModel model) {
        String key = model.getOriginalText() + model.getTranslatedText();
        if(storage.containsKey(key)) {
            return storage.get(key);
        }

        UserGuessModel result = repository.find(key);
        if(result == null) {
            result = new UserGuessModel(repository, key, 0, 0);
            repository.insert(key, result);
        }

        storage.put(key, result);
        return result;
    }

    public void truncate() {
        storage.clear();
        repository.truncate();
    }
}
