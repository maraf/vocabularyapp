package com.neptuo.vocabularyapp.ui.viewmodels.comparators;

import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;
import com.neptuo.vocabularyapp.ui.viewmodels.BrowseViewModel;

import java.util.Comparator;

/**
 * Created by Windows10 on 8/10/2015.
 */
public class PercentageUserDetailItemModelComparator implements Comparator<BrowseViewModel> {
    private boolean isAscending;

    public PercentageUserDetailItemModelComparator(boolean isAscending) {
        this.isAscending = isAscending;
    }

    @Override
    public int compare(BrowseViewModel lhs, BrowseViewModel rhs) {
        if(lhs.getModel().getTotalCount() == 0) {
            if(rhs.getModel().getTotalCount() == 0) {
                return 0;
            }

            return isAscending ? 1 : -1;
        } else if(rhs.getModel().getTotalCount() == 0) {
            return isAscending ? -1 : 1;
        }

        double ratioLhs = ((double)lhs.getModel().getCorrectCount()) / lhs.getModel().getTotalCount();
        double ratioRhs = ((double)rhs.getModel().getCorrectCount()) / rhs.getModel().getTotalCount();

        if(isAscending)
            return (int)((ratioRhs - ratioLhs) * 100);
        else
            return (int)((ratioLhs - ratioRhs) * 100);
    }
}
