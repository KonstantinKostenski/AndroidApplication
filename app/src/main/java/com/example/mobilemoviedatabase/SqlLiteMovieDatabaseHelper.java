package com.example.mobilemoviedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlLiteMovieDatabaseHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MovieDatabase.db";
    public static final String SQL_CREATE_MOVIE_TABLE = String
            .format("CREATE TABLE %s (" +
                            " ID INTEGER PRIMARY KEY," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL)",
                    "MOVIE", "TITLE", "DESCRIPTION");

    public static final String SQL_CREATE_GENRE_TABLE = String.format("CREATE TABLE %s (" +
                    " ID INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL);",
            "GENRE", "NAME", "DESCRIPTION");

    public static final String SQL_CREATE_BUSINESS_ENTITY_TABLE = String.format("CREATE TABLE %s (" +
                    " ID INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL);",
            "BUSINESS_ENTITY", "NAME", "DESCRIPTION");

    public static final String SQL_CREATE_MOVIE_GENRE_TABLE = String.format("CREATE TABLE %s (" +
                    " MOVIE_ID INTEGER ," +
                    " GENRE_ID INTEGER," +
                    " FOREIGN KEY(MOVIE_ID) REFERENCES MOVIE(ID)," +
                    " FOREIGN KEY(GENRE_ID) REFERENCES GENRE(ID));",
            "MOVIE_GENRE");

    public static final String SQL_CREATE_MOVIE_BUSINESS_ENTITY_TABLE = String.format("CREATE TABLE %s (" +
                    " MOVIE_ID INTEGER ," +
                    " BUSINESS_ENTITY_ID INTEGER," +
                    " FOREIGN KEY(MOVIE_ID) REFERENCES MOVIE(ID)," +
                    " FOREIGN KEY(BUSINESS_ENTITY_ID) REFERENCES BUSINESS_ENTITY(ID));",
            "MOVIE_BUSINESS_ENTITY");

    public static final String SQL_DELETE_ENTRIES = "";

    public SqlLiteMovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_GENRE_TABLE);
        db.execSQL(SQL_CREATE_MOVIE_GENRE_TABLE);
        db.execSQL(SQL_CREATE_BUSINESS_ENTITY_TABLE);
        db.execSQL(SQL_CREATE_MOVIE_BUSINESS_ENTITY_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
