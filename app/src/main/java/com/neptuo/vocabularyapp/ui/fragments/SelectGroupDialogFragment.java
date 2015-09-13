package com.neptuo.vocabularyapp.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 9/13/2015.
 */
public class SelectGroupDialogFragment extends DialogFragment {
    public interface OnClickOkListener {
        void onClick(List<Session.Group> selectedGroups);
    }

    private OnClickOkListener okListener;
    private List<Session.Group> lastSelectedGroups;

    public void setOkListener(OnClickOkListener okListener) {
        this.okListener = okListener;
    }

    public void setLastSelectedGroups(List<Session.Group> lastSelectedGroups) {
        this.lastSelectedGroups = lastSelectedGroups;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String[] items = new String[4];
        items[0] = getString(R.string.session_group_new);
        items[1] = getString(R.string.session_group_hard);
        items[2] = getString(R.string.session_group_medium);
        items[3] = getString(R.string.session_group_soft);

        final boolean[] selectedItems = new boolean[4];
        if(lastSelectedGroups.contains(Session.Group.New)) {
            selectedItems[0] = true;
        }
        if(lastSelectedGroups.contains(Session.Group.Hard)) {
            selectedItems[1] = true;
        }
        if(lastSelectedGroups.contains(Session.Group.Medium)) {
            selectedItems[2] = true;
        }
        if(lastSelectedGroups.contains(Session.Group.Soft)) {
            selectedItems[3] = true;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setTitle(R.string.select_groups)
            .setMultiChoiceItems(items, selectedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    selectedItems[which] = isChecked;
                }
            })
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(okListener != null) {
                        List<Session.Group> result = new ArrayList<Session.Group>();
                        if (selectedItems[0]) {
                            result.add(Session.Group.New);
                        }
                        if (selectedItems[1]) {
                            result.add(Session.Group.Hard);
                        }
                        if (selectedItems[2]) {
                            result.add(Session.Group.Medium);
                        }
                        if (selectedItems[3]) {
                            result.add(Session.Group.Soft);
                        }

                        okListener.onClick(result);
                    }
                }
            })
            .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        return builder.create();
    }
}
