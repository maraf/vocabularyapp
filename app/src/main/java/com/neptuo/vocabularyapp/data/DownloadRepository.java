package com.neptuo.vocabularyapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.neptuo.vocabularyapp.services.models.DownloadModel;
import com.neptuo.vocabularyapp.services.models.UrlModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Windows10 on 8/12/2015.
 */
public class DownloadRepository {
    private SQLiteDatabase db;

    public DownloadRepository(SQLiteDatabase db) {
        this.db = db;
    }

    public Map<Integer, DownloadModel> getList() {
        String[] projection = {
            Sql.Download._ID,
            Sql.Download._SOURCE_LANGUAGE_ID,
            Sql.Download._TARGET_LANGUAGE_ID
        };

        Cursor cursor = db.query(
            Sql.Download.TABLE,
            projection,
            null,
            null,
            null,
            null,
            null
        );

        LanguageRepository languages = new LanguageRepository(db);
        Map<Integer, DownloadModel> result = new HashMap<Integer, DownloadModel>();

        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Sql.Download._ID));
                int sourceLanguageId = cursor.getInt(cursor.getColumnIndexOrThrow(Sql.Download._SOURCE_LANGUAGE_ID));
                int targetLanguageId = cursor.getInt(cursor.getColumnIndexOrThrow(Sql.Download._TARGET_LANGUAGE_ID));

                DownloadModel model = new DownloadModel(languages.get(sourceLanguageId), languages.get(targetLanguageId));
                model.getUrls().addAll(getUrls(id));
                result.put(id, model);

            } while (cursor.moveToNext());
        }

        return result;
    }

    private List<UrlModel> getUrls(int downloadId) {
        String[] projection = {
            Sql.Url._NAME,
            Sql.Url._VALUE
        };

        String selection = Sql.Url._DOWNLOAD_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(downloadId) };

        Cursor cursor = db.query(
            Sql.Url.TABLE,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        );

        List<UrlModel> result = new ArrayList<UrlModel>();

        if(cursor.moveToFirst()) {
            do {

                String name = cursor.getString(cursor.getColumnIndexOrThrow(Sql.Url._NAME));
                String value = cursor.getString(cursor.getColumnIndexOrThrow(Sql.Url._VALUE));
                result.add(new UrlModel(name, value));

            } while (cursor.moveToNext());
        }

        return result;
    }

    public int save(DownloadModel model) {
        LanguageRepository languages = new LanguageRepository(db);
        int sourceLanguageId = languages.save(model.getSourceLanguage());
        int targetLanguageId = languages.save(model.getTargetLanguage());

        ContentValues values = new ContentValues();
        values.put(Sql.Download._SOURCE_LANGUAGE_ID, sourceLanguageId);
        values.put(Sql.Download._TARGET_LANGUAGE_ID, targetLanguageId);

        int downloadId = (int) db.insert(Sql.Download.TABLE, null, values);

        for(UrlModel url : model.getUrls()) {
            ContentValues urlValues = new ContentValues();
            urlValues.put(Sql.Url._DOWNLOAD_ID, downloadId);
            urlValues.put(Sql.Url._NAME, url.getName());
            urlValues.put(Sql.Url._VALUE, url.getValue());

            db.insert(Sql.Url.TABLE, null, urlValues);
        }

        return downloadId;
    }

    public void truncate() {
        LanguageRepository languages = new LanguageRepository(db);
        languages.truncate();
        db.delete(Sql.Download.TABLE, null, null);
    }
}
