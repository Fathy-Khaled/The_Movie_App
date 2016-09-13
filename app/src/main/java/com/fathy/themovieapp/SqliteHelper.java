package com.fathy.themovieapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


class SqliteHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "Movies";
    static final String TABLE_NAME = "Favourite";
    static final int VERSION = 1;
    static final String MOVIE_ID = "id";
    static final String MOVIE_URL = "url";

    private static final String Create_Table = "create table " + TABLE_NAME + "(" + MOVIE_ID + " Integer, " + MOVIE_URL + " text);";
    private static final String Drop_Table = "Drop table if exists " + TABLE_NAME;

    private Context context;

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Create_Table);

        } catch (SQLiteException e) {
            Toast.makeText(context, e + "", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(Drop_Table);
            onCreate(db);
        } catch (SQLiteException e) {
            Toast.makeText(context, e + "", Toast.LENGTH_SHORT).show();
        }
    }
}
