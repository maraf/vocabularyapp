package com.neptuo.vocabularyapp.data;

import android.provider.BaseColumns;

import java.sql.Struct;

/**
 * Created by Windows10 on 8/12/2015.
 */

public final class Sql {
    public final class Download implements BaseColumns {
        public static final String TABLE = "download";
        public static final String _SOURCE_LANGUAGE_ID = "sourceLanguageId";
        public static final String _TARGET_LANGUAGE_ID = "targetLanguageId";

        public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE + "(" +
                _ID + " INTEGER PRIMARY KEY, " +
                _SOURCE_LANGUAGE_ID + " INTEGER, " +
                _TARGET_LANGUAGE_ID + " INTEGER, " +
                "FOREIGN KEY(" + _SOURCE_LANGUAGE_ID + ") REFERENCES " + Language.TABLE + "(" + Language._ID + "), " +
                "FOREIGN KEY(" + _TARGET_LANGUAGE_ID + ") REFERENCES " + Language.TABLE + "(" + Language._ID + ")" +
            ")";

        public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE;
    }

    public final class Language implements BaseColumns {
        public static final String TABLE = "language";
        public static final String _NAME = "name";
        public static final String _CODE = "code";

        public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE + "(" +
                _ID + " INTEGER PRIMARY KEY," +
                _NAME + " TEXT, " +
                _CODE + " TEXT" +
            ")";

        public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE;
    }

    public final class Url implements BaseColumns {
        public static final String TABLE = "url";
        public static final String _DOWNLOAD_ID = "downloadId";
        public static final String _NAME = "name";
        public static final String _VALUE = "value";

        public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE + "(" +
                _ID + " INTEGER PRIMARY KEY, " +
                _DOWNLOAD_ID + " INTEGER, " +
                _NAME + " TEXT, " +
                _VALUE + " TEXT, " +
                "FOREIGN KEY(" + _DOWNLOAD_ID + ") REFERENCES " + Download.TABLE + "(" + Download._ID + ")" +
            ")";

        public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE;
    }

    public final class DetailItem implements BaseColumns {
        public static final String TABLE = "detailItem";
        public static final String _DOWNLOAD_ID = "downloadId";
        public static final String _SOURCE_TEXT = "sourceText";
        public static final String _SOURCE_DESCRIPTION = "sourceDescription";
        public static final String _TARGET_TEXT = "targetText";
        public static final String _TARGET_DESCRIPTION = "targetDescription";

        public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE + "(" +
                _ID + " INTEGER PRIMARY KEY, " +
                _DOWNLOAD_ID + " INTEGER, " +
                _SOURCE_TEXT + " TEXT, " +
                _SOURCE_DESCRIPTION + " TEXT, " +
                _TARGET_TEXT + " TEXT, " +
                _TARGET_DESCRIPTION + " TEXT, " +
                "FOREIGN KEY(" + _DOWNLOAD_ID + ") REFERENCES " + Download.TABLE + "(" + Download._ID + ")" +
            ")";

        public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE;
    }

    public final class DetailItemTag implements BaseColumns {
        public static final String TABLE = "detailItemTag";
        public static final String _DETAIL_ITEM_ID = "detailItemId";
        public static final String _NAME = "name";

        public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE + "(" +
                _ID + " INTEGER PRIMARY KEY, " +
                _DETAIL_ITEM_ID + " INTEGER, " +
                _NAME + " TEXT, " +
                "FOREIGN KEY(" + _DETAIL_ITEM_ID + ") REFERENCES " + DetailItem.TABLE + "(" + DetailItem._ID + ")" +
            ")";

        public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE;
    }

    public final class UserGuess {
        public static final String TABLE = "userGuess";
        public static final String _KEY = "key";
        public static final String _CORRECT_COUNT = "correctCount";
        public static final String _WRONG_COUNT = "wrongCount";

        public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE + "(" +
                _KEY + " TEXT PRIMARY KEY, " +
                _CORRECT_COUNT + " INTEGER, " +
                _WRONG_COUNT + " INTEGER" +
            ")";

        public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE;
    }
}
