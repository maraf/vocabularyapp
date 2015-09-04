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
        void onClick(List<String> selectedTags, boolean isNoTagIncluded);
    }

    private OnClickOkListener okListener;
    private List<String> lastSelectedTags;
    private boolean isNoTagIncluded;

    public void setOkListener(OnClickOkListener okListener) {
        this.okListener = okListener;
    }

    public void setLastSelectedTags(List<String> lastSelectedTags, boolean isNoTagIncluded) {
        this.lastSelectedTags = lastSelectedTags;
        this.isNoTagIncluded = isNoTagIncluded;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String[] sourceTags = ServiceProvider.getTags().toArray(new String[ServiceProvider.getTags().size()]);
        final String[] tags = new String[sourceTags.length + 1];

        for (int i = 0; i < tags.length - 1; i++) {
            tags[i + 1] = sourceTags[i];
        }
        tags[0] = "(bez štítku)";

        final boolean[] selectedTags = new boolean[tags.length];

        selectedTags[0] = isNoTagIncluded;
        for (int i = 1; i < selectedTags.length; i ++) {
            if(lastSelectedTags != null && lastSelectedTags.contains(tags[i])) {
                selectedTags[i] = true;
            }
        }
        selectedTags[0] = isNoTagIncluded;

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
                        for (int i = 1; i < selectedTags.length; i++) {
                            if(selectedTags[i]) {
                                selected.add(tags[i]);
                            }
                        }

                        okListener.onClick(selected, selectedTags[0]);
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
