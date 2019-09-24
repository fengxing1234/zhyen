package com.zhyen.android.picture_selected.model;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.zhyen.android.picture_selected.entity.Album;
import com.zhyen.android.picture_selected.loader.AlbumMediaLoader;

import java.lang.ref.WeakReference;

public class AlbumMediaCollection implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 2;
    private static final String ARGS_ALBUM = "args_album";
    private static final String TAG = AlbumMediaCollection.class.getSimpleName();

    private WeakReference<Context> mContext;
    private LoaderManager mLoaderManager;
    private AlbumMediaCallbacks mCallbacks;

    public void onCreate(FragmentActivity context, AlbumMediaCallbacks callbacks) {
        mContext = new WeakReference<Context>(context);
        mLoaderManager = context.getSupportLoaderManager();
        this.mCallbacks = callbacks;
    }

    public void load(Album album) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_ALBUM, album);
        mLoaderManager.initLoader(LOADER_ID, bundle, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        Context context = mContext.get();
        if (context == null) {
            Log.d(TAG, "onCreateLoader: 页面已经关闭了");
            return null;
        }
        if (bundle == null) {
            return null;
        }

        Album album = bundle.getParcelable(ARGS_ALBUM);
        return AlbumMediaLoader.newInstance(context, album);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }
        if (mCallbacks != null) {
            mCallbacks.onLoadFinish(loader, cursor);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }
        if (mCallbacks != null) {
            mCallbacks.onLoaderReset(loader);
        }
    }

    public void onDestroy() {
        if (mLoaderManager != null) {
            mLoaderManager.destroyLoader(LOADER_ID);
        }
        mCallbacks = null;
    }


    public interface AlbumMediaCallbacks {

        void onLoadFinish(Loader<Cursor> loader, Cursor cursor);

        void onLoaderReset(Loader<Cursor> cursor);
    }
}
