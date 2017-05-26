package com.lmasi.lmasi.yous;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConect extends SQLiteOpenHelper {

    public DBConect(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists Flag(field text, flag text)");
        db.execSQL("create table if not exists CurrentId(idx integer, id text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
