package com.zhyen.android.picture_selected.ui;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
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
import com.zhyen.android.picture_selected.entity.Item;
import com.zhyen.android.picture_selected.model.AlbumMediaCollection;
import com.zhyen.android.picture_selected.model.SelectedItemCollection;
import com.zhyen.android.picture_selected.ui.adapter.AlbumPictureAdapter;
import com.zhyen.android.picture_selected.ui.widget.MediaGridInset;
import com.zhyen.android.utils.UIUtils;

public class MediaSelectionFragment extends Fragment implements AlbumMediaCollection.AlbumMediaCallbacks, AlbumPictureAdapter.CheckStateListener, AlbumPictureAdapter.OnMediaClickListener {

    public static final String TAG = MediaSelectionFragment.class.getSimpleName();
    public static final String EXTRA_ALBUM = "extra_album";

    private AlbumPictureAdapter.CheckStateListener mCheckStateListener;
    private AlbumPictureAdapter.OnMediaClickListener mOnMediaClickListener;
    private SelectionProvider mSelectionProvider;

    private AlbumMediaCollection mCollection = new AlbumMediaCollection();

    private RecyclerView mRecyclerView;
    private AlbumPictureAdapter mAdapter;
    private SelectionSpec mSelectionSpec;

    public static MediaSelectionFragment newInstance(Album album) {
        MediaSelectionFragment mediaSelectionFragment = new MediaSelectionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ALBUM, album);
        mediaSelectionFragment.setArguments(bundle);
        return mediaSelectionFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectionProvider) {
            mSelectionProvider = (SelectionProvider) context;
        } else {
            throw new IllegalStateException("Context must implement SelectionProvider.");
        }
        if (context instanceof AlbumPictureAdapter.CheckStateListener) {
            mCheckStateListener = (AlbumPictureAdapter.CheckStateListener) context;
        }
        if (context instanceof AlbumPictureAdapter.OnMediaClickListener) {
            mOnMediaClickListener = (AlbumPictureAdapter.OnMediaClickListener) context;
        }
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
        mAdapter = new AlbumPictureAdapter(getContext(), mSelectionProvider.provideSelectedItemCollection(), mRecyclerView);
        mAdapter.registerCheckStateListener(this);
        mAdapter.registerOnMediaClickListener(this);
        mRecyclerView.setHasFixedSize(true);
        mSelectionSpec = SelectionSpec.getInstance();
        int spanCount;
        if (mSelectionSpec.gridExpectedSize > 0) {
            spanCount = UIUtils.spanCount(getContext(), mSelectionSpec.gridExpectedSize);
        } else {
            spanCount = mSelectionSpec.spanCount;
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        int spacing = getResources().getDimensionPixelSize(R.dimen.media_grid_spacing);
        mRecyclerView.addItemDecoration(new MediaGridInset(spanCount, spacing, false));
        mRecyclerView.setAdapter(mAdapter);
        mCollection.onCreate(getActivity(), this);
        mCollection.load(album);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
        mCollection.onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLoadFinish(Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG, "onLoadFinish: ");
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursor) {
        Log.d(TAG, "onLoaderReset: ");
        mAdapter.swapCursor(null);
    }

    public void refreshMediaGrid() {
        mAdapter.notifyDataSetChanged();
    }

    public void refreshSelection() {
        mAdapter.refreshSelection();
    }

    @Override
    public void onUpdate() {
        if (mCheckStateListener != null) {
            mCheckStateListener.onUpdate();
        }
    }

    @Override
    public void onMediaClick(Album album, Item item, int adapterPosition) {
        if (mOnMediaClickListener != null) {
            mOnMediaClickListener.onMediaClick((Album) getArguments().getParcelable(EXTRA_ALBUM),
                    item, adapterPosition);
        }
    }

    public void reLoad() {
        mCollection.reLoad();
    }

    public interface SelectionProvider {
        SelectedItemCollection provideSelectedItemCollection();
    }
}
