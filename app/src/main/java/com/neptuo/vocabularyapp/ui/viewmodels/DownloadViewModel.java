package com.neptuo.vocabularyapp.ui.viewmodels;

import com.neptuo.vocabularyapp.data.Sql;
import com.neptuo.vocabularyapp.services.models.DownloadModel;
import com.neptuo.vocabularyapp.ui.adapters.DownloadUrlListAdapter;

/**
 * Created by Windows10 on 8/15/2015.
 */
public class DownloadViewModel {
    private DownloadModel model;
    private DownloadUrlListAdapter urlAdapter;

    public DownloadViewModel(DownloadModel model, DownloadUrlListAdapter urlAdapter) {
        this.model = model;
        this.urlAdapter = urlAdapter;
    }

    public DownloadModel getModel() {
        return model;
    }

    public DownloadUrlListAdapter getUrlAdapter() {
        return urlAdapter;
    }

    public boolean isSelected() {
        return !urlAdapter.getSelectedItems().isEmpty();
    }

    public DownloadModel createSelectedModel() {
        DownloadModel result = new DownloadModel(model.getSourceLanguage(), model.getTargetLanguage());
        result.getUrls().addAll(urlAdapter.getSelectedItems());
        return result;
    }
}
