package com.neptuo.vocabularyapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Windows10 on 8/12/2015.
 */
public class DbContext extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 9;
    public static final String DATABASE_NAME = "Vocabulary.db";

    public DbContext(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Sql.Language.TABLE_CREATE);
        db.execSQL(Sql.Download.TABLE_CREATE);
        db.execSQL(Sql.Url.TABLE_CREATE);
        db.execSQL(Sql.DetailItem.TABLE_CREATE);
        db.execSQL(Sql.UserGuess.TABLE_CREATE);
        db.execSQL(Sql.DetailItemTag.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 8) {
            db.execSQL(Sql.Language.TABLE_DROP);
            db.execSQL(Sql.Download.TABLE_DROP);
            db.execSQL(Sql.Url.TABLE_DROP);
            db.execSQL(Sql.DetailItem.TABLE_DROP);
            db.execSQL(Sql.UserGuess.TABLE_DROP);
            onCreate(db);
        }

        if(oldVersion == 8) {
            db.execSQL(Sql.DetailItemTag.TABLE_CREATE);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, 0, newVersion);
    }
}
