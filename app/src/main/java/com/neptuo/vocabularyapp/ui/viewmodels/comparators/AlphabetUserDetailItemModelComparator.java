package com.neptuo.vocabularyapp.ui.viewmodels.comparators;

import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by Windows10 on 8/10/2015.
 */
public class AlphabetUserDetailItemModelComparator implements Comparator<UserDetailItemModel> {
    private DetailModel detail;
    private boolean isSortedByOriginalText;

    public AlphabetUserDetailItemModelComparator(DetailModel detail, boolean isSortedByOriginalText) {
        this.detail = detail;
        this.isSortedByOriginalText = isSortedByOriginalText;
    }

    @Override
    public int compare(UserDetailItemModel lhs, UserDetailItemModel rhs) {
        String lhsText;
        String rhsText;
        Locale locale;
        if(isSortedByOriginalText) {
            lhsText = lhs.getModel().getOriginalText();
            rhsText = rhs.getModel().getOriginalText();
            locale = detail.getDownload().getSourceLanguage().getLocale();
        } else {
            lhsText = lhs.getModel().getTranslatedText();
            rhsText = rhs.getModel().getTranslatedText();
            locale = detail.getDownload().getTargetLanguage().getLocale();
        }

        Collator comparator = Collator.getInstance(locale);
        return comparator.compare(lhsText, rhsText);
    }
}
