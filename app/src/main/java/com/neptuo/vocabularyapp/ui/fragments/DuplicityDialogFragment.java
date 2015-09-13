package com.neptuo.vocabularyapp.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.DuplicityChecker;
import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.ui.adapters.DuplicityListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 9/13/2015.
 */
public class DuplicityDialogFragment extends DialogFragment {
    private DuplicityChecker duplicityChecker;

    public void setDuplicityChecker(DuplicityChecker duplicityChecker) {
        this.duplicityChecker = duplicityChecker;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DuplicityListAdapter adapter = new DuplicityListAdapter(getActivity(), duplicityChecker.getItems());

        ListView view = new ListView(getActivity());
        view.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setTitle("Nalezené duplicity")
            .setView(view)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        return builder.create();
    }
}
