package com.neptuo.vocabularyapp.services;

import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.services.models.UserGuessModel;

import java.util.HashMap;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class UserStorage {

    private HashMap<String, UserGuessModel> storage = new HashMap<String, UserGuessModel>();

    public UserGuessModel find(DetailItemModel model) {
        String key = model.getOriginalText() + model.getTranslatedText();
        if(storage.containsKey(key)) {
            return storage.get(key);
        }

        UserGuessModel result = new UserGuessModel(0, 0);
        storage.put(key, result);
        return result;
    }
}
