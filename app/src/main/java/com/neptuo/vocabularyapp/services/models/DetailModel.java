package com.neptuo.vocabularyapp.services.models;

import com.neptuo.vocabularyapp.data.Sql;

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


    public DetailModel reverse() {
        DetailModel reverse = new DetailModel(new DownloadModel(download.getTargetLanguage(), download.getSourceLanguage()));
        List<DetailItemModel> reverseItems = reverse.getItems();
        for (DetailItemModel item : items) {
            DetailItemModel newItem = new DetailItemModel(item.getTranslatedText(), item.getOriginalText(), item.getTranslatedDescription(), item.getOriginalDescription());
            newItem.getTags().addAll(item.getTags());
            reverseItems.add(newItem);
        }

        return reverse;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ download.hashCode();
    }
}
