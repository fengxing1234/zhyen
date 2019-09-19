package com.zhyen.android.picture_selected.entity;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.zhyen.android.R;
import com.zhyen.android.picture_selected.loader.AlbumLoader;

public class Album {

    public static final String ALBUM_ID_ALL = String.valueOf(-1);
    public static final String ALBUM_NAME_ALL = "All";

    public String mId;
    public String mCoverPath;
    public String mDisplayName;
    public long mCount;

    public Album(String id, String data, String displayName, long count) {
        this.mId = id;
        this.mCoverPath = data;
        this.mDisplayName = displayName;
        this.mCount = count;
    }

    public static Album valueOf(Cursor cursor) {
        return new Album(
                cursor.getString(cursor.getColumnIndex("bucket_id")),
                cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)),
                cursor.getString(cursor.getColumnIndex("bucket_display_name")),
                cursor.getLong(cursor.getColumnIndex(AlbumLoader.COLUMN_COUNT)));
    }

    public void addCaptureCount() {
        mCount++;
    }

    public String getDisplayName(Context context) {
        if (isAll()) {
            return context.getString(R.string.all);
        }
        return mDisplayName;
    }

    public boolean isAll() {
        return ALBUM_ID_ALL.equals(mId);
    }

    public boolean isEmpty() {
        return mCount == 0;
    }
}
