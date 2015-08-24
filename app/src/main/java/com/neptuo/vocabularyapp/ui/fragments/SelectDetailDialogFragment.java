package com.neptuo.vocabularyapp.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.ui.TranslateActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/14/2015.
 */
public class SelectDetailDialogFragment extends DialogFragment {

    public interface OnClickOkListener {
        void onClick(int detailIndex, boolean isReverse);
    }

    private OnClickOkListener okListener;

    public void setOkListener(OnClickOkListener okListener) {
        this.okListener = okListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        List<String> details = new ArrayList<String>();
        for (DetailModel model : ServiceProvider.getDetails()) {
            details.add(model.getDownload().getSourceLanguage().getName() + " -> " + model.getDownload().getTargetLanguage().getName());
            details.add(model.getDownload().getTargetLanguage().getName() + " -> " + model.getDownload().getSourceLanguage().getName());
        }

        String[] detailsArray = details.toArray(new String[details.size()]);

        final List<Integer> selectedIndex = new ArrayList<Integer>();
        selectedIndex.add(0);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setTitle(R.string.select_vocabulary)
            .setSingleChoiceItems(detailsArray, selectedIndex.get(0), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectedIndex.add(which);
                }
            })
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(okListener != null) {
                        int index = selectedIndex.get(selectedIndex.size() - 1);
                        int detailIndex = index / 2;
                        boolean isReverse = (index % 2) == 1;
                        okListener.onClick(detailIndex, isReverse);
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
