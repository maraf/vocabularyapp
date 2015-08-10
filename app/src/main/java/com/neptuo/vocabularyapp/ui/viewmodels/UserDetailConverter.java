package com.neptuo.vocabularyapp.ui.viewmodels;

import com.neptuo.vocabularyapp.services.UserStorage;
import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/10/2015.
 */
public class UserDetailConverter {
    public static List<UserDetailItemModel> map(UserStorage userStorage, List<DetailItemModel> items) {
        List<UserDetailItemModel> userItems = new ArrayList<UserDetailItemModel>();
        for (DetailItemModel item : items) {
            userItems.add(new UserDetailItemModel(item, userStorage.find(item)));
        }

        return userItems;
    }
}
