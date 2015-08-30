package com.neptuo.vocabularyapp.ui.viewmodels.comparators;

import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;
import com.neptuo.vocabularyapp.ui.viewmodels.BrowseViewModel;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by Windows10 on 8/10/2015.
 */
public class AlphabetUserDetailItemModelComparator implements Comparator<BrowseViewModel> {
    private DetailModel detail;
    private boolean isSortedByOriginalText;

    public AlphabetUserDetailItemModelComparator(DetailModel detail, boolean isSortedByOriginalText) {
        this.detail = detail;
        this.isSortedByOriginalText = isSortedByOriginalText;
    }

    @Override
    public int compare(BrowseViewModel lhs, BrowseViewModel rhs) {
        String lhsText;
        String rhsText;
        Locale locale;
        if(isSortedByOriginalText) {
            lhsText = lhs.getModel().getModel().getOriginalText();
            rhsText = rhs.getModel().getModel().getOriginalText();
            locale = detail.getDownload().getSourceLanguage().getLocale();
        } else {
            lhsText = lhs.getModel().getModel().getTranslatedText();
            rhsText = rhs.getModel().getModel().getTranslatedText();
            locale = detail.getDownload().getTargetLanguage().getLocale();
        }

        if(locale.getLanguage().equals("de")) {
            lhsText = normalizeGerman(lhsText);
            rhsText = normalizeGerman(rhsText);
        }

        Collator comparator = Collator.getInstance(locale);
        return comparator.compare(lhsText, rhsText);
    }

    private String normalizeGerman(String text) {
        String testText = text.toLowerCase();
        if(testText.startsWith("der ") || testText.startsWith("die ") || testText.startsWith("dar ")) {
            return text.substring(4);
        }

        return text;
    }
}
