package com.neptuo.vocabularyapp.services;

import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;
import com.neptuo.vocabularyapp.ui.viewmodels.UserDetailConverter;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class Session {
    private DetailModel model;
    private List<UserDetailItemModel> allItems;

    private GroupModel newGroup;
    private GroupModel hardGroup;
    private GroupModel mediumGroup;
    private GroupModel softGroup;

    private Random random;

    public Session(DetailModel model, UserStorage userStorage) {
        this.model = model;
        this.allItems = UserDetailConverter.map(userStorage, model.getItems());
        this.random = new Random();

        newGroup = new GroupModel();
        hardGroup = new GroupModel();
        mediumGroup = new GroupModel();
        softGroup = new GroupModel();

        for (UserDetailItemModel itemModel : allItems) {
            getGroup(itemModel).getAllItems().add(itemModel);
        }
    }

    private double getRatio(UserDetailItemModel itemModel) {
        return ((double)itemModel.getCorrectCount()) / itemModel.getTotalCount();
    }

    private GroupModel getGroup(UserDetailItemModel itemModel) {
        if (itemModel.getTotalCount() == 0) {
            return newGroup;
        }

        double ratio = getRatio(itemModel);
        if (ratio < 0.4) {
            return hardGroup;
        } else if(ratio < 0.7) {
            return mediumGroup;
        } else {
            return softGroup;
        }
    }

    public void update(UserDetailItemModel itemModel) {
        GroupModel group = getGroup(itemModel);
        if (!group.getAllItems().contains(itemModel)) {
            hardGroup.getAllItems().remove(itemModel);
            mediumGroup.getAllItems().remove(itemModel);
            softGroup.getAllItems().remove(itemModel);
            group.getAllItems().add(itemModel);
        }
    }

    public UserDetailItemModel nextRandom() {
        int newCount = newGroup.getAllItems().size() > 0 ? 3 : 0;
        int hardCount = hardGroup.getAllItems().size() > 0 ? 5 : 0;
        int mediumCount = mediumGroup.getAllItems().size() > 0 ? 4 : 0;
        int softCount = softGroup.getAllItems().size() > 0 ? 2 : 0;

        int totalCount = newCount + hardCount + mediumCount + softCount;

        int index = random.nextInt(totalCount);
        if (index < newCount) {
            return newGroup.nextRandom();
        } else if (index < newCount + hardCount) {
            return hardGroup.nextRandom();
        } else if (index < newCount + hardCount + mediumCount) {
            return mediumGroup.nextRandom();
        } else {
            return softGroup.nextRandom();
        }
    }


    private class GroupModel {
        private List<UserDetailItemModel> allItems;
        private HashSet<Integer> usedItems;

        private Random random;

        public GroupModel() {
            this.allItems = new ArrayList<UserDetailItemModel>();
            this.random = new Random();
            this.usedItems = new HashSet<Integer>();
        }

        public List<UserDetailItemModel> getAllItems() {
            return allItems;
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
}
