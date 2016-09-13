package com.fathy.themovieapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class DatabaseAdapter {
    SqliteHelper Db;
    Context context;


    public DatabaseAdapter(Context context) {
        Db = new SqliteHelper(context);
        this.context = context;
    }

    public List<MyMovie> getmovie(){
        int i = 0;
        SQLiteDatabase sqLiteDatabase = Db.getWritableDatabase();
        String col[] = { SqliteHelper.MOVIE_ID, SqliteHelper.MOVIE_URL};
        Cursor cursor = sqLiteDatabase.query(SqliteHelper.TABLE_NAME, col, null, null, null, null, null);
        List<MyMovie> movies = new ArrayList<>();
        while (cursor.moveToNext()){
            String url = cursor.getString(cursor.getColumnIndex(SqliteHelper.MOVIE_URL));
            int id = cursor.getInt(cursor.getColumnIndex(SqliteHelper.MOVIE_ID));
            MyMovie movie = new MyMovie();
            movie.set_ID_URL(id, url);
            movies.add(movie);
        }
        return movies;
    }


    public long insert(int id, String url) {
        SQLiteDatabase sqLiteDatabase = Db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SqliteHelper.MOVIE_URL, url);
        contentValues.put(SqliteHelper.MOVIE_ID, id);
        long i = sqLiteDatabase.insert(SqliteHelper.TABLE_NAME, null, contentValues);

        return i;

    }

    public int deleteName(int id) {
        SQLiteDatabase sqLiteDatabase = Db.getWritableDatabase();

        String[] nameDelete={id + ""};
        int count= sqLiteDatabase.delete(SqliteHelper.TABLE_NAME, SqliteHelper.MOVIE_ID + " =? ", nameDelete);
        return count;
    }

    public int search(int id){
        int i = 0;
        SQLiteDatabase sqLiteDatabase = Db.getWritableDatabase();
        String col[] = {SqliteHelper.MOVIE_ID};
        String[] args = {id + ""};
        Cursor cursor = sqLiteDatabase.query(SqliteHelper.TABLE_NAME, col, SqliteHelper.MOVIE_ID + "=?", args, null, null, null);
        while (cursor.moveToNext()){
            i = 1;
        }
        return i;
    }


}
