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
public class BrowseItemDialogFragment extends DialogFragment {
    private DetailItemModel itemModel;

    public void setItemModel(DetailItemModel itemModel) {
        this.itemModel = itemModel;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_browse_item, null);

        TextView sourceText = (TextView) view.findViewById(R.id.originalText);
        TextView sourceDescription = (TextView) view.findViewById(R.id.originalDescription);
        TextView targetText = (TextView) view.findViewById(R.id.translatedText);
        TextView targetDescription = (TextView) view.findViewById(R.id.translatedDescription);

        if(itemModel != null) {
            sourceText.setText(itemModel.getOriginalText());
            sourceDescription.setText(itemModel.getOriginalDescription());
            targetText.setText(itemModel.getTranslatedText());
            targetDescription.setText(itemModel.getTranslatedDescription());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            //.setTitle("Detail")
            .setView(view)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        return builder.create();
    }
}
