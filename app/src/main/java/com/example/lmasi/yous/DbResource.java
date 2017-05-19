package com.example.lmasi.yous;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbResource {

    public static DBConect conn;
    public static SQLiteDatabase db;

    public static void insert(String field, boolean flag)
    {
        db.execSQL("insert into Flag values ('" + field + "', '" + Boolean.toString(flag) + "');");
    }

    public static void update(String field, boolean flag)
    {
        db.execSQL("update Flag set flag = '" + Boolean.toString(flag) + "' where field = '" + field + "';");
    }

    public static boolean get(String field)
    {

        Cursor c = db.rawQuery("select flag from Flag where field = '" + field + "';", null);
        c.moveToNext();

        try {
            return Boolean.parseBoolean(c.getString(0));
        }catch (Exception e)
        {
            return false;
        }
    }

    public static void insert_cId(String id)
    {
        db.execSQL("insert into CurrentId values (" + "0" + ", '" + id + "');");
    }

    public static void update_cId(String id)
    {
        db.execSQL("update CurrentId set id = '" + id + "' where idx = " + 0 + ";");
    }

    public static String get_cId()
    {

        Cursor c = db.rawQuery("select id from CurrentId where idx = " + 0 + ";", null);
        c.moveToNext();


        return c.getString(0);

    }

}
