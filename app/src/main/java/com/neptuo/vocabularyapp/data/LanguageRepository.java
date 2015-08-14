package com.neptuo.vocabularyapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.neptuo.vocabularyapp.services.models.DownloadModel;
import com.neptuo.vocabularyapp.services.models.LanguageModel;

import java.util.List;

/**
 * Created by Windows10 on 8/12/2015.
 */
public class LanguageRepository {
    private SQLiteDatabase db;

    public LanguageRepository(SQLiteDatabase db) {
        this.db = db;
    }

    public LanguageModel get(int languageId) {
        String[] projection = {
            Sql.Language._NAME,
            Sql.Language._CODE
        };

        String selection = Sql.Language._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(languageId) };

        Cursor cursor = db.query(
            Sql.Language.TABLE,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        );

        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndexOrThrow(Sql.Language._NAME));
        String code = cursor.getString(cursor.getColumnIndexOrThrow(Sql.Language._CODE));

        return new LanguageModel(name, code);
    }

    public int save(LanguageModel model) {
        ContentValues values = new ContentValues();
        values.put(Sql.Language._NAME, model.getName());
        values.put(Sql.Language._CODE, model.getCode());

        return (int) db.insert(Sql.Language.TABLE, null, values);
    }

    public void truncate() {
        db.delete(Sql.Language.TABLE, null, null);
    }
}
