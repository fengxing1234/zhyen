package com.zhyen.android.test.test_provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.zhyen.android.db.DefaultDBHelp;

import java.util.HashMap;

public class TestPictureProvider extends ContentProvider {

    private ContentResolver mContentResolver;
    private DefaultDBHelp mDBHelper;
    private SQLiteDatabase mDatabase;

    private static final int IPICTURE_LIST = 0;
    private static final int IPICTURE_ITEM = 1;

    private static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static HashMap<String, String> mProjectionMap = new HashMap<>();

    static {


        mUriMatcher.addURI(TestPictureContract.AUTHORITY, "ipicture", IPICTURE_LIST);
        mUriMatcher.addURI(TestPictureContract.AUTHORITY, "ipicture/#", IPICTURE_ITEM);

        mProjectionMap.put(TestPictureContract.IPicture.ID, TestPictureContract.IPicture.ID);
        mProjectionMap.put(TestPictureContract.IPicture.TITLE, TestPictureContract.IPicture.TITLE);
        mProjectionMap.put(TestPictureContract.IPicture.AUTHOR, TestPictureContract.IPicture.AUTHOR);

    }

    public static final String DB_NAME = "db_test.db";
    public static final String DB_TABLE = "tab_picture";

    public static final String SQL_CREATE_IPICTURE = "create table " +
            DB_TABLE +
            " ( " +
            TestPictureContract.IPicture.ID + " integer primary key, " +
            TestPictureContract.IPicture.TITLE + " text not null, " +
            TestPictureContract.IPicture.AUTHOR + " text not null)";


    public TestPictureProvider() {

    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mContentResolver = context.getContentResolver();
        mDBHelper = new DefaultDBHelp(context, DB_NAME, null, 1);
        return true;


    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        mDatabase = mDBHelper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        switch (mUriMatcher.match(uri)) {
            case IPICTURE_ITEM:
                long id = ContentUris.parseId(uri);
                builder.setTables(DB_TABLE);
                builder.setProjectionMap(mProjectionMap);
                builder.appendWhere(TestPictureContract.IPicture.ID + " = " + id);
                break;

            case IPICTURE_LIST:
                builder.setTables(DB_TABLE);
                builder.setProjectionMap(mProjectionMap);
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        Cursor cursor = builder.query(mDatabase, projection, selection, selectionArgs, null, null, TestPictureContract.IPicture.DEFAULT_SORT);
        cursor.setNotificationUri(mContentResolver, uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case IPICTURE_ITEM:
                return TestPictureContract.IPicture.CONTENT_TYPE;
            case IPICTURE_LIST:
                return TestPictureContract.IPicture.CONTENT_ITME_TYPE;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        mDatabase = mDBHelper.getReadableDatabase();
        Uri mNewUri = null;
        long id = 0;
        switch (mUriMatcher.match(uri)) {
            case IPICTURE_ITEM:
                throw new IllegalArgumentException("Error Uri: " + uri);
            case IPICTURE_LIST:
                id = mDatabase.insert(DB_TABLE, null, values);
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        if (id > 0) {
            mNewUri = ContentUris.withAppendedId(uri, id);
        } else {
            throw new SQLiteException("Unable to insert");
        }
        mContentResolver.notifyChange(uri, null);
        return mNewUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        mDatabase = mDBHelper.getReadableDatabase();
        int count = 0;
        switch (mUriMatcher.match(uri)) {
            case IPICTURE_ITEM:
                long id = ContentUris.parseId(uri);
                selection = TestPictureContract.IPicture.ID + " = " + id + " " + selection;
                count = mDatabase.delete(DB_TABLE, selection, selectionArgs);
                break;
            case IPICTURE_LIST:
                count = mDatabase.delete(DB_TABLE, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        mContentResolver.notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        mDatabase = mDBHelper.getWritableDatabase();
        int count = 0;
        switch (mUriMatcher.match(uri)) {
            case IPICTURE_ITEM:
                long id = ContentUris.parseId(uri);
                selection = TestPictureContract.IPicture.ID + " = " + id + " " + selection;
                count = mDatabase.update(DB_TABLE, values, selection, selectionArgs);
                break;

            case IPICTURE_LIST:
                count = mDatabase.update(DB_TABLE, values, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        //notify all uris
        mContentResolver.notifyChange(uri, null);
        //successfully count > 0 . or count = 0
        return count;
    }
}
