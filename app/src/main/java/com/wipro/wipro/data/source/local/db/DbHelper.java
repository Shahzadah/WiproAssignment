package com.wipro.wipro.data.source.local.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static DbHelper mDBHelper;

    private DbHelper(Context context) {
        super(context, DbContracts.DATABASE_NAME, null, DbContracts.DATABASE_VERSION);
    }

    public static DbHelper getInstance(Context context) {
        if (mDBHelper == null) {
            mDBHelper = new DbHelper(context);
        }
        return mDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Table creation for first time install
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Update DB structure for app upgrade
    }
}
