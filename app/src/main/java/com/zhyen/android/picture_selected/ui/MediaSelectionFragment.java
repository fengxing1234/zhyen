package com.zhyen.android.picture_selected.ui;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhyen.android.R;
import com.zhyen.android.picture_selected.SelectionSpec;
import com.zhyen.android.picture_selected.entity.Album;
import com.zhyen.android.picture_selected.model.AlbumMediaCollection;
import com.zhyen.android.picture_selected.ui.adapter.AlbumPictureAdapter;
import com.zhyen.android.utils.UIUtils;

public class MediaSelectionFragment extends Fragment implements AlbumMediaCollection.AlbumMediaCallbacks {

    public static final String TAG = MediaSelectionFragment.class.getSimpleName();
    public static final String EXTRA_ALBUM = "extra_album";

    private AlbumMediaCollection mCollection = new AlbumMediaCollection();

    private RecyclerView mRecyclerView;
    private AlbumPictureAdapter mAdapter;
    private SelectionSpec mSelectionSpec;

    public static Fragment newInstance(Album album) {
        MediaSelectionFragment mediaSelectionFragment = new MediaSelectionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ALBUM, album);
        mediaSelectionFragment.setArguments(bundle);
        return mediaSelectionFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picture_selected, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Album album = getArguments().getParcelable(EXTRA_ALBUM);
        Log.d(TAG, "onActivityCreated: " + album.getDisplayName(getContext()));
        mAdapter = new AlbumPictureAdapter();
        mRecyclerView.setHasFixedSize(true);
        mSelectionSpec = SelectionSpec.getInstance();
        int spanCount;
        if (mSelectionSpec.gridExpectedSize > 0) {
            spanCount = UIUtils.spanCount(getContext(), mSelectionSpec.gridExpectedSize);
        } else {
            spanCount = mSelectionSpec.spanCount;
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        mRecyclerView.setAdapter(mAdapter);
        mCollection.onCreate(getActivity(), this);
        mCollection.load(album);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCollection.onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLoadFinish(Loader<Cursor> loader, Cursor cursor) {
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
            Log.d(TAG, "onLoadFinish: " + name);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursor) {

    }
}
