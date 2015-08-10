package com.neptuo.vocabularyapp.ui.viewmodels.comparators;

import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;

import java.util.Comparator;

/**
 * Created by Windows10 on 8/10/2015.
 */
public class PercentageUserDetailItemModelComparator implements Comparator<UserDetailItemModel> {
    private boolean isAscending;

    public PercentageUserDetailItemModelComparator(boolean isAscending) {
        this.isAscending = isAscending;
    }

    @Override
    public int compare(UserDetailItemModel lhs, UserDetailItemModel rhs) {
        if(lhs.getTotalCount() == 0) {
            if(rhs.getTotalCount() == 0) {
                return 0;
            }

            return isAscending ? 1 : -1;
        } else if(rhs.getTotalCount() == 0) {
            return isAscending ? -1 : 1;
        }

        double ratioLhs = ((double)lhs.getCorrectCount()) / lhs.getTotalCount();
        double ratioRhs = ((double)rhs.getCorrectCount()) / rhs.getTotalCount();

        if(isAscending)
            return (int)((ratioRhs - ratioLhs) * 100);
        else
            return (int)((ratioLhs - ratioRhs) * 100);
    }
}
