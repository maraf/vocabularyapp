package com.neptuo.vocabularyapp.ui.viewmodels;

import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;

/**
 * Created by Windows10 on 8/30/2015.
 */
public class BrowseViewModel {
    private UserDetailItemModel model;
    private boolean isVisible;

    public BrowseViewModel(UserDetailItemModel model) {
        this(model, true);
    }

    public BrowseViewModel(UserDetailItemModel model, boolean isVisible) {
        this.model = model;
        this.isVisible = isVisible;
    }

    public UserDetailItemModel getModel() {
        return model;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
