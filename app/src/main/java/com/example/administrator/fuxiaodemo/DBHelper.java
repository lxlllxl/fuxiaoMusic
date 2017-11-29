package com.example.administrator.fuxiaodemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库
 * Created by Administrator on 2017/8/5.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "member.db";
    private static final String Table_NAME = "info";
    private static final String CREATE_TABLE = "create table info(_id integer primary key autoincrement,name text,password text )";
    SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    //插入信息
    public void insertadmin(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(Table_NAME, null, values);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
