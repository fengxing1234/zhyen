package com.zhyen.android.picture_selected.loader;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.zhyen.android.picture_selected.SelectionSpec;
import com.zhyen.android.picture_selected.entity.Album;

public class AlbumLoader extends CursorLoader {

    private static final String TAG = AlbumLoader.class.getSimpleName();

    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");

    public static final String COLUMN_COUNT = "count";

    private static final String BUCKET_ORDER_BY = "datetaken DESC";

    private static final String SELECTION =
            "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0"
                    + ") GROUP BY (bucket_id";

    private static final String[] SELECTION_ARGS = {
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO),
    };

    //首先创建一个字符数组，且字符数组的值对应着表的字段，
    private static final String[] COLUMNS = {
            MediaStore.Files.FileColumns._ID,
            "bucket_id",
            "bucket_display_name",
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.MIME_TYPE,
            COLUMN_COUNT};

    //需要查询的参数
    private static final String[] PROJECTION = {
            MediaStore.Files.FileColumns._ID,
            "bucket_id",
            "bucket_display_name",
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.MIME_TYPE,
            "COUNT(*) AS " + COLUMN_COUNT};


    //查询指定类型的数据
    // === params for showSingleMediaType: true ===
    private static final String SELECTION_FOR_SINGLE_MEDIA_TYPE =
            MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0"
                    + ") GROUP BY (bucket_id";

    private static String[] getSelectionArgsForSingleMediaType(int mediaType) {
        return new String[]{String.valueOf(mediaType)};
    }


    ///////////////////////////////////params for showSingleMediaType: true ///////////////////////////////////

    private static final String SELECTION_FOR_SINGLE_MEDIA_GIF_TYPE =
            MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0"
                    + " AND " + MediaStore.MediaColumns.MIME_TYPE + "=?"
                    + ") GROUP BY (bucket_id";

    private static String[] getSelectionArgsForSingleMediaGifType(int mediaType) {
        return new String[]{String.valueOf(mediaType), "image/gif"};
    }


    public AlbumLoader(@NonNull Context context) {
        super(context);
    }

    public AlbumLoader(@NonNull Context context, @Nullable String selection, @Nullable String[] selectionArgs) {
        super(context, QUERY_URI, PROJECTION, selection, selectionArgs, BUCKET_ORDER_BY);
    }

    public static Loader newInstance(Context context) {
        return newInstance(context, null, null);
    }

    public static Loader newInstance(Context context, String selections, String[] selection_args) {
        String selection;
        String[] selectionArgs;
        if (SelectionSpec.getInstance().onlyShowGif()) {
            selection = SELECTION_FOR_SINGLE_MEDIA_GIF_TYPE;
            selectionArgs = getSelectionArgsForSingleMediaGifType(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE);
        } else if (SelectionSpec.getInstance().onlyShowImages()) {
            selection = SELECTION_FOR_SINGLE_MEDIA_TYPE;
            selectionArgs = getSelectionArgsForSingleMediaType(
                    MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE);
        } else if (SelectionSpec.getInstance().onlyShowVideos()) {
            selection = SELECTION_FOR_SINGLE_MEDIA_TYPE;
            selectionArgs = getSelectionArgsForSingleMediaType(
                    MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO);
        } else if (SelectionSpec.getInstance().customShowList()) {
            selection = selections;
            selectionArgs = selection_args;
        } else {
            selection = SELECTION;
            selectionArgs = SELECTION_ARGS;
        }
        if (selection == null || selection_args == null) {
            selection = SELECTION;
            selectionArgs = SELECTION_ARGS;
        }
        return new AlbumLoader(context, selection, selectionArgs);
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = super.loadInBackground();
        MatrixCursor matrixCursor = new MatrixCursor(COLUMNS);
        long totalCount = 0;
        String allAlbumCoverPath = "";
        if (cursor != null) {
            while (cursor.moveToNext()) {
                totalCount += cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT));
            }
            if (cursor.moveToFirst()) {
                allAlbumCoverPath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            }
        }
        matrixCursor.addRow(new String[]{Album.ALBUM_ID_ALL, Album.ALBUM_ID_ALL, Album.ALBUM_NAME_ALL, allAlbumCoverPath, "", String.valueOf(totalCount)});
        return new MergeCursor(new Cursor[]{matrixCursor, cursor});
    }

    @Override
    public void onContentChanged() {
        // FIXME a dirty way to fix loading multiple times
    }
}
