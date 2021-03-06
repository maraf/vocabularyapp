package com.neptuo.vocabularyapp.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.models.DetailItemModel;

/**
 * Created by Windows10 on 8/14/2015.
 */
public class BrowseDialogFragment extends DialogFragment {
    private DetailItemModel itemModel;
    private String positiveButtonText = "OK";
    private DialogInterface.OnClickListener positiveButton = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

    private String negativeButtonText = null;
    private DialogInterface.OnClickListener negativeButton = null;

    public void setItemModel(DetailItemModel itemModel) {
        this.itemModel = itemModel;
    }

    public void setPositiveButton(String positionButtonText, DialogInterface.OnClickListener positiveButton) {
        this.positiveButtonText = positionButtonText;
        this.positiveButton = positiveButton;
    }

    public void setNegativeButton(String negativeButtonText, DialogInterface.OnClickListener negativeButton) {
        this.negativeButtonText = negativeButtonText;
        this.negativeButton = negativeButton;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_browse_item, null);

        TextView sourceText = (TextView) view.findViewById(R.id.originalText);
        TextView sourceDescription = (TextView) view.findViewById(R.id.originalDescription);
        TextView targetText = (TextView) view.findViewById(R.id.translatedText);
        TextView targetDescription = (TextView) view.findViewById(R.id.translatedDescription);
        TextView tagsText = (TextView) view.findViewById(R.id.tagsText);

        if(itemModel != null) {
            sourceText.setText(itemModel.getOriginalText());
            sourceDescription.setText(itemModel.getOriginalDescription());
            targetText.setText(itemModel.getTranslatedText());
            targetDescription.setText(itemModel.getTranslatedDescription());

            StringBuilder tags = new StringBuilder();
            for (String tag : itemModel.getTags()) {
                if(tags.length() > 0) {
                    tags.append(", ");
                }

                tags.append(tag);
            }
            tagsText.setText(tags.toString());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            //.setTitle("Detail")
            .setView(view)
            .setPositiveButton(positiveButtonText, positiveButton);

        if(negativeButton != null) {
            builder
                .setNegativeButton(negativeButtonText, negativeButton);
        }

        return builder.create();
    }
}
