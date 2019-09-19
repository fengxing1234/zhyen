package com.zhyen.android.picture_selected.model;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.zhyen.android.picture_selected.loader.AlbumLoader;

import java.lang.ref.WeakReference;

public class AlbumCollection implements LoaderManager.LoaderCallbacks<Cursor> {

    private WeakReference<Context> context;
    private LoaderManager loaderManager;
    private AlbumCallbacks callbacks;
    private boolean mLoadFinished;

    public void onCreate(FragmentActivity activity, AlbumCallbacks callbacks) {
        context = new WeakReference<Context>(activity);
        loaderManager = activity.getSupportLoaderManager();
        this.callbacks = callbacks;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    public void loadAlbums() {
        loaderManager.initLoader(0, null, this);
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int i, @Nullable Bundle bundle) {
        if (context.get() == null) {
            return null;
        }
        mLoadFinished = false;
        return AlbumLoader.newInstance(context.get());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (context.get() == null) {
            return;
        }
        if (!mLoadFinished) {
            mLoadFinished = true;
            callbacks.onAlbumLoad(cursor);
        }
    }


    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        Context context = this.context.get();
        if (context == null) {
            return;
        }
        callbacks.onLoaderReset();
    }

    public int getCurrentSelection() {
        return 0;
    }

    public interface AlbumCallbacks {
        void onAlbumLoad(Cursor cursor);

        void onLoaderReset();
    }
}
