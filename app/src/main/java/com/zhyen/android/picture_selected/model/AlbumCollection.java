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

import com.zhyen.android.picture_selected.loader.AlbumLoader;

import java.lang.ref.WeakReference;

public class AlbumCollection implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String STATE_CURRENT_SELECTION = "state_current_selection";
    private static final int LOADER_ID = 1;
    private static final String TAG = AlbumCollection.class.getSimpleName();
    private WeakReference<Context> context;
    private LoaderManager loaderManager;
    private AlbumCallbacks callbacks;
    private boolean mLoadFinished;
    private int mCurrentSelection;

    public void onCreate(FragmentActivity activity, AlbumCallbacks callbacks) {
        context = new WeakReference<Context>(activity);
        loaderManager = activity.getSupportLoaderManager();
        this.callbacks = callbacks;
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_CURRENT_SELECTION, mCurrentSelection);
        Log.d(TAG, "onSaveInstanceState: " + mCurrentSelection);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        mCurrentSelection = savedInstanceState.getInt(STATE_CURRENT_SELECTION);
        Log.d(TAG, "onRestoreInstanceState: " + mCurrentSelection);
    }

    public void loadAlbums() {
        loaderManager.initLoader(LOADER_ID, null, this);
    }

    public void reload() {
        if (context.get() == null) {
            return;
        }
        if (loaderManager == null) {
            return;
        }
        Loader<Object> loader = loaderManager.getLoader(LOADER_ID);
        if (loader != null) {
            loader.forceLoad();
        }
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
        return mCurrentSelection;
    }

    public void setStateCurrentSelection(int currentSelection) {
        mCurrentSelection = currentSelection;
    }

    public void onDestroy() {
        if (loaderManager != null) {
            loaderManager.destroyLoader(LOADER_ID);
        }
        callbacks = null;
    }

    public interface AlbumCallbacks {
        void onAlbumLoad(Cursor cursor);

        void onLoaderReset();
    }
}
