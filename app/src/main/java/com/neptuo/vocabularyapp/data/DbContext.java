package com.neptuo.vocabularyapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Windows10 on 8/12/2015.
 */
public class DbContext extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Sql.Language.TABLE_DROP);
        db.execSQL(Sql.Download.TABLE_DROP);
        db.execSQL(Sql.Url.TABLE_DROP);
        db.execSQL(Sql.DetailItem.TABLE_DROP);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
