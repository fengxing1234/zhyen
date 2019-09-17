package com.zhyen.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.zhyen.android.test.test_provider.TestPictureProvider.DB_TABLE;
import static com.zhyen.android.test.test_provider.TestPictureProvider.SQL_CREATE_IPICTURE;

public class DefaultDBHelp extends SQLiteOpenHelper {

    public DefaultDBHelp(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, factory, version);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_IPICTURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DB_TABLE);
        onCreate(db);
    }

}
