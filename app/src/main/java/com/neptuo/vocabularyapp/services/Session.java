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
    private List<UserDetailItemModel> filteredItems;

    private GroupModel newGroup;
    private GroupModel hardGroup;
    private GroupModel mediumGroup;
    private GroupModel softGroup;
    private UserDetailItemModel lastItem;

    private Random random;

    public Session(DetailModel model, UserStorage userStorage) {
        this.model = model;
        this.allItems = UserDetailConverter.map(userStorage, model.getItems());
        this.filteredItems = allItems;
        this.random = new Random();
        buildGroups();
    }

    private void buildGroups() {
        lastItem = null;
        newGroup = new GroupModel();
        hardGroup = new GroupModel();
        mediumGroup = new GroupModel();
        softGroup = new GroupModel();

        for (UserDetailItemModel itemModel : filteredItems) {
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

    public void updateGroup(UserDetailItemModel itemModel) {
        if(filteredItems.size() == 1) {
            lastItem = null;
        } else {
            lastItem =  itemModel;
        }

        GroupModel group = getGroup(itemModel);
        if (group == newGroup || !group.getAllItems().contains(itemModel)) {
            newGroup.getAllItems().remove(itemModel);
            hardGroup.getAllItems().remove(itemModel);
            mediumGroup.getAllItems().remove(itemModel);
            softGroup.getAllItems().remove(itemModel);
            group.getAllItems().add(itemModel);
        }
    }

    public void filterTags(List<String> tags) {
        filteredItems = new ArrayList<UserDetailItemModel>();
        for (UserDetailItemModel itemModel : allItems) {
            boolean hasTag = false;

            for (String tag : itemModel.getModel().getTags()) {
                if(tags.contains(tag)) {
                    filteredItems.add(itemModel);
                    break;
                }
            }
        }
        buildGroups();
    }

    public UserDetailItemModel nextRandom() {
        int newCount = Math.min(10, newGroup.getAllItems().size() * 2);
        int hardCount = Math.min(12, hardGroup.getAllItems().size());
        int mediumCount = Math.min(8, mediumGroup.getAllItems().size());
        int softCount = Math.min(4, softGroup.getAllItems().size());

        int totalCount = newCount + hardCount + mediumCount + softCount;

        UserDetailItemModel newItem;
        do {
            int index = random.nextInt(totalCount);
            if (index < newCount) {
                newItem = newGroup.nextRandom();
            } else if (index < newCount + hardCount) {
                newItem = hardGroup.nextRandom();
            } else if (index < newCount + hardCount + mediumCount) {
                newItem = mediumGroup.nextRandom();
            } else {
                newItem = softGroup.nextRandom();
            }
        } while (newItem == lastItem);

        return newItem;
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
            if(usedItems.size() >= allItems.size()) {
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
