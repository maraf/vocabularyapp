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
                Sql.DetailItem._SOURCE_TEXT,
                Sql.DetailItem._SOURCE_DESCRIPTION,
                Sql.DetailItem._TARGET_TEXT,
                Sql.DetailItem._TARGET_DESCRIPTION
        };

        String selection = Sql.DetailItem._DOWNLOAD_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(downloadId)};

        Cursor cursor = db.query(
                Sql.DetailItem.TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        List<DetailItemModel> result = new ArrayList<DetailItemModel>();

        cursor.moveToFirst();
        do {

            String sourceText = cursor.getString(cursor.getColumnIndexOrThrow(Sql.DetailItem._SOURCE_TEXT));
            String sourceDescription = cursor.getString(cursor.getColumnIndexOrThrow(Sql.DetailItem._SOURCE_DESCRIPTION));
            String targetText = cursor.getString(cursor.getColumnIndexOrThrow(Sql.DetailItem._TARGET_TEXT));
            String targetDescription = cursor.getString(cursor.getColumnIndexOrThrow(Sql.DetailItem._TARGET_DESCRIPTION));

            result.add(new DetailItemModel(sourceText, targetText, sourceDescription, targetDescription));

        } while (cursor.moveToNext());

        return result;
    }

    public int save(int downloadId, DetailItemModel model) {
        ContentValues values = new ContentValues();
        values.put(Sql.DetailItem._DOWNLOAD_ID, downloadId);
        values.put(Sql.DetailItem._SOURCE_TEXT, model.getOriginalText());
        values.put(Sql.DetailItem._SOURCE_DESCRIPTION, model.getOriginalDescription());
        values.put(Sql.DetailItem._TARGET_TEXT, model.getTranslatedText());
        values.put(Sql.DetailItem._TARGET_DESCRIPTION, model.getTranslatedDescription());

        return (int) db.insert(Sql.DetailItem.TABLE, null, values);
    }
}
