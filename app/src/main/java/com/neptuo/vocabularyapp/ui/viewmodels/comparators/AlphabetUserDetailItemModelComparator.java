package com.neptuo.vocabularyapp.ui.viewmodels.comparators;

import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;

import java.util.Comparator;

/**
 * Created by Windows10 on 8/10/2015.
 */
public class AlphabetUserDetailItemModelComparator implements Comparator<UserDetailItemModel> {
    private boolean isSortedByOriginalText;

    public AlphabetUserDetailItemModelComparator(boolean isSortedByOriginalText) {
        this.isSortedByOriginalText = isSortedByOriginalText;
    }

    @Override
    public int compare(UserDetailItemModel lhs, UserDetailItemModel rhs) {
        if(isSortedByOriginalText) {
            return lhs.getModel().getOriginalText().compareTo(rhs.getModel().getOriginalText());
        } else {
            return lhs.getModel().getTranslatedText().compareTo(rhs.getModel().getTranslatedText());
        }

    }
}
