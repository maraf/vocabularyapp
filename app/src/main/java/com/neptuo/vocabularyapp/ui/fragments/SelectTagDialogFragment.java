package com.neptuo.vocabularyapp.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.ServiceProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Windows10 on 8/24/2015.
 */
public class SelectTagDialogFragment extends DialogFragment {

    public interface OnClickOkListener {
        void onClick(List<String> selectedTags);
    }

    private OnClickOkListener okListener;
    private List<String> lastSelectedTags;

    public void setOkListener(OnClickOkListener okListener) {
        this.okListener = okListener;
    }

    public void setLastSelectedTags(List<String> lastSelectedTags) {
        this.lastSelectedTags = lastSelectedTags;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String[] tags = ServiceProvider.getTags().toArray(new String[ServiceProvider.getTags().size()]);
        final boolean[] selectedTags = new boolean[tags.length];

        for (int i = 0; i < selectedTags.length; i ++) {
            if(lastSelectedTags != null && lastSelectedTags.contains(tags[i])) {
                selectedTags[i] = true;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setTitle(R.string.select_tags)
            .setMultiChoiceItems(tags, selectedTags, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    selectedTags[which] = isChecked;
                }
            })
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(okListener != null) {
                        List<String> selected = new ArrayList<String>();
                        for (int i = 0; i < selectedTags.length; i++) {
                            if(selectedTags[i]) {
                                selected.add(tags[i]);
                            }
                        }

                        okListener.onClick(selected);
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
