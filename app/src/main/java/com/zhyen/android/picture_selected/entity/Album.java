package com.zhyen.android.picture_selected.entity;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import com.zhyen.android.R;
import com.zhyen.android.picture_selected.loader.AlbumLoader;

public class Album implements Parcelable {

    public static final String ALBUM_ID_ALL = String.valueOf(-1);
    public static final String ALBUM_NAME_ALL = "All";

    public String id;
    public String coverPath;
    public String displayName;
    public long count;

    public Album(String id, String data, String displayName, long count) {
        this.id = id;
        this.coverPath = data;
        this.displayName = displayName;
        this.count = count;
    }

    protected Album(Parcel in) {
        id = in.readString();
        coverPath = in.readString();
        displayName = in.readString();
        count = in.readLong();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public static Album valueOf(Cursor cursor) {
        return new Album(
                cursor.getString(cursor.getColumnIndex("bucket_id")),
                cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)),
                cursor.getString(cursor.getColumnIndex("bucket_display_name")),
                cursor.getLong(cursor.getColumnIndex(AlbumLoader.COLUMN_COUNT)));
    }

    public void addCaptureCount() {
        count++;
    }

    public String getDisplayName(Context context) {
        if (isAll()) {
            return context.getString(R.string.all);
        }
        return displayName;
    }

    public boolean isAll() {
        return ALBUM_ID_ALL.equals(id);
    }

    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(coverPath);
        dest.writeString(displayName);
        dest.writeLong(count);
    }
}
