package com.neptuo.vocabularyapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.neptuo.vocabularyapp.services.models.DownloadModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/12/2015.
 */
public class DownloadRepository {
    private SQLiteDatabase db;

    public DownloadRepository(SQLiteDatabase db) {
        this.db = db;
    }

    public List<DownloadModel> getList() {
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
        List<DownloadModel> result = new ArrayList<DownloadModel>();

        cursor.moveToFirst();
        do {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(Sql.Download._ID));
            int sourceLanguageId = cursor.getInt(cursor.getColumnIndexOrThrow(Sql.Download._SOURCE_LANGUAGE_ID));
            int targetLanguageId = cursor.getInt(cursor.getColumnIndexOrThrow(Sql.Download._TARGET_LANGUAGE_ID));

            DownloadModel model = new DownloadModel(languages.get(sourceLanguageId), languages.get(targetLanguageId));
            model.getUrls().addAll(getUrls(id));
            result.add(model);

        } while (cursor.moveToNext());

        return result;
    }

    private List<String> getUrls(int downloadId) {
        String[] projection = {
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

        List<String> result = new ArrayList<String>();

        cursor.moveToFirst();
        do {

            String value = cursor.getString(cursor.getColumnIndexOrThrow(Sql.Url._VALUE));
            result.add(value);

        } while (cursor.moveToNext());

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

        for(String url : model.getUrls()) {
            ContentValues urlValues = new ContentValues();
            urlValues.put(Sql.Url._DOWNLOAD_ID, downloadId);
            urlValues.put(Sql.Url._VALUE, url);

            db.insert(Sql.Url.TABLE, null, values);
        }

        return downloadId;
    }

    public void truncate() {
        LanguageRepository languages = new LanguageRepository(db);
        languages.truncate();
        db.delete(Sql.Download.TABLE, null, null);
    }
}
