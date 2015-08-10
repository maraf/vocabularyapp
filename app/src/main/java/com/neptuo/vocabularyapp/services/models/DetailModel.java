package com.neptuo.vocabularyapp.services.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class DetailModel {
    private DownloadModel download;
    private List<DetailItemModel> items;

    public DetailModel(DownloadModel download) {
        this.download = download;
        this.items = new ArrayList<DetailItemModel>();
    }

    public DownloadModel getDownload() {
        return download;
    }

    public List<DetailItemModel> getItems() {
        return items;
    }
}
