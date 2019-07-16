package com.project.simoneconigliaro.movieapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.simoneconigliaro.movieapp.data.MovieContract.MovieEntry;

public class MovieDbHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "moviesDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;


    // Constructor
    MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID                  + " INTEGER PRIMARY KEY, " +
                MovieEntry.COLUMN_MOVIE_ID      + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_TITLE         + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_PICTURE_PATH  + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_OVERVIEW      + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_USER_RATING   + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_RELEASE_DATE  + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);

    }

    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
