package com.neptuo.vocabularyapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.services.models.LanguageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/13/2015.
 */
public class DetailItemRepository {
    private SQLiteDatabase db;

    public DetailItemRepository(SQLiteDatabase db) {
        this.db = db;
    }

    public List<DetailItemModel> getListByDownloadId(int downloadId) {
        String[] projection = {
            Sql.DetailItem._ID,
            Sql.DetailItem._SOURCE_TEXT,
            Sql.DetailItem._SOURCE_DESCRIPTION,
            Sql.DetailItem._TARGET_TEXT,
            Sql.DetailItem._TARGET_DESCRIPTION
        };

        String selection = Sql.DetailItem._DOWNLOAD_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(downloadId)};

        Cursor cursor = null;
        try {
            cursor = db.query(
                Sql.DetailItem.TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
            );

            List<DetailItemModel> result = new ArrayList<DetailItemModel>();

            if (cursor.moveToFirst()) {
                do {

                    int id = (int) cursor.getInt(cursor.getColumnIndexOrThrow(Sql.DetailItem._ID));
                    String sourceText = cursor.getString(cursor.getColumnIndexOrThrow(Sql.DetailItem._SOURCE_TEXT));
                    String sourceDescription = cursor.getString(cursor.getColumnIndexOrThrow(Sql.DetailItem._SOURCE_DESCRIPTION));
                    String targetText = cursor.getString(cursor.getColumnIndexOrThrow(Sql.DetailItem._TARGET_TEXT));
                    String targetDescription = cursor.getString(cursor.getColumnIndexOrThrow(Sql.DetailItem._TARGET_DESCRIPTION));

                    DetailItemModel itemModel = new DetailItemModel(sourceText, targetText, sourceDescription, targetDescription);

                    itemModel.getTags().addAll(getTags(id));
                    result.add(itemModel);

                } while (cursor.moveToNext());
            }

            return result;

        } finally {
            if(cursor != null)
                cursor.close();
        }
    }

    private List<String> getTags(int detailItemId) {
        String[] projection = {
            Sql.DetailItemTag._NAME
        };

        String selection = Sql.DetailItemTag._DETAIL_ITEM_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(detailItemId)};

        Cursor cursor = null;
        try {
            cursor = db.query(
                Sql.DetailItemTag.TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
            );

            List<String> result = new ArrayList<String>();
            if(cursor.moveToFirst()) {
                do {

                    String name = cursor.getString(cursor.getColumnIndexOrThrow(Sql.DetailItemTag._NAME));
                    result.add(name);

                } while (cursor.moveToNext());
            }

            return result;

        } finally {
            if(cursor != null)
                cursor.close();
        }
    }

    public int save(int downloadId, DetailItemModel model) {
        ContentValues values = new ContentValues();
        values.put(Sql.DetailItem._DOWNLOAD_ID, downloadId);
        values.put(Sql.DetailItem._SOURCE_TEXT, model.getOriginalText());
        values.put(Sql.DetailItem._SOURCE_DESCRIPTION, model.getOriginalDescription());
        values.put(Sql.DetailItem._TARGET_TEXT, model.getTranslatedText());
        values.put(Sql.DetailItem._TARGET_DESCRIPTION, model.getTranslatedDescription());

        int detailItemId = (int) db.insert(Sql.DetailItem.TABLE, null, values);

        for (String tag : model.getTags()) {
            ContentValues tagValues = new ContentValues();
            tagValues.put(Sql.DetailItemTag._DETAIL_ITEM_ID, detailItemId);
            tagValues.put(Sql.DetailItemTag._NAME, tag);

            db.insert(Sql.DetailItemTag.TABLE, null, tagValues);
        }

        return detailItemId;
    }

    public void truncate() {
        db.delete(Sql.DetailItem.TABLE, null, null);
        db.delete(Sql.DetailItemTag.TABLE, null, null);
    }
}
