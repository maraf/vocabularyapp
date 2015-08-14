package com.neptuo.vocabularyapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.neptuo.vocabularyapp.services.models.UserGuessModel;

import java.security.Key;

/**
 * Created by Windows10 on 8/14/2015.
 */
public class UserGuessRepository {
    private SQLiteDatabase db;

    public UserGuessRepository(SQLiteDatabase db) {
        this.db = db;
    }

    public UserGuessModel find(String key) {
        String[] projection = {
            Sql.UserGuess._CORRECT_COUNT,
            Sql.UserGuess._WRONG_COUNT
        };

        String selection = Sql.UserGuess._KEY + " LIKE ?";
        String[] selectionArgs = { key };

        Cursor cursor = db.query(
            Sql.UserGuess.TABLE,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        );

        if(cursor.moveToFirst()) {
            int correctCount = cursor.getInt(cursor.getColumnIndexOrThrow(Sql.UserGuess._CORRECT_COUNT));
            int wrongCount = cursor.getInt(cursor.getColumnIndexOrThrow(Sql.UserGuess._WRONG_COUNT));
            return new UserGuessModel(this, key, correctCount, wrongCount);
        }

        return null;
    }

    private ContentValues getContentValues(UserGuessModel model) {
        ContentValues values = new ContentValues();
        values.put(Sql.UserGuess._CORRECT_COUNT, model.getCorrectCount());
        values.put(Sql.UserGuess._WRONG_COUNT, model.getWrongCount());
        return values;
    }

    public void insert(String key, UserGuessModel model) {
        ContentValues values = getContentValues(model);
        values.put(Sql.UserGuess._KEY, key);
        db.insert(Sql.UserGuess.TABLE, null, values);
    }

    public void update(String key, UserGuessModel model) {
        ContentValues values = getContentValues(model);

        String selection = Sql.UserGuess._KEY + " LIKE ?";
        String[] selectionArgs = { key };

        db.update(Sql.UserGuess.TABLE, values, selection, selectionArgs);
    }

    public void truncate() {
        db.delete(Sql.UserGuess.TABLE, null, null);
    }
}
