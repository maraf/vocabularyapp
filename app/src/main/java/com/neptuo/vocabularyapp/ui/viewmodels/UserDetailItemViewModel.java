package com.neptuo.vocabularyapp.ui.viewmodels;

import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class UserDetailItemViewModel {
    private UserDetailItemModel userModel;

    public UserDetailItemViewModel(UserDetailItemModel userModel) {
        this.userModel = userModel;
    }

    public UserDetailItemModel getUserModel() {
        return userModel;
    }

    public DetailItemModel getModel() {
        return userModel.getModel();
    }

    public boolean tryTranslation(String text) {
        String typedTranslation = text.toLowerCase();
        return userModel.getModel().getTranslatedText().toLowerCase().equals(typedTranslation);
    }

    public double getCorrentGuessRatio() {
        int totalCount = userModel.getTotalCount();
        if(totalCount == 0)
            return 0;

        return ((double)userModel.getCorrectCount()) / totalCount;
    }
}
