package com.zhyen.android.picture_selected;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhyen.android.R;
import com.zhyen.android.picture_selected.entity.Album;
import com.zhyen.android.picture_selected.model.AlbumCollection;
import com.zhyen.android.picture_selected.model.SelectedItemCollection;
import com.zhyen.android.picture_selected.ui.MediaSelectionFragment;
import com.zhyen.android.picture_selected.ui.adapter.AlbumsAdapter;
import com.zhyen.android.picture_selected.ui.widget.AlbumsSpinner;

public class PictureSelectionActivity extends AppCompatActivity implements View.OnClickListener, AlbumCollection.AlbumCallbacks {

    private static final String TAG = PictureSelectionActivity.class.getSimpleName();

    private static final String CHECK_STATE = "checkState";
    private SelectedItemCollection mSelectedCollection = new SelectedItemCollection(this);
    private final AlbumCollection mAlbumCollection = new AlbumCollection();
    private TextView tvPreview;
    private TextView tvApply;
    private SelectionSpec mSpec;
    private LinearLayout llOriginLayout;
    private AlbumsAdapter adapter;
    private AlbumsSpinner albumsSpinner;

    private boolean mOriginalEnable = true;
    private boolean isShowSelectedAlbum = false;
    private FrameLayout mEmptyView;
    private FrameLayout mContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        mSpec = SelectionSpec.getInstance();
        setTheme(R.style.Selection_Dracula);
        setContentView(R.layout.activity_picture_selection);

        if (mOriginalEnable) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);//传感器方向
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Drawable navigationIcon = toolbar.getNavigationIcon();
        TypedArray ta = getTheme().obtainStyledAttributes(new int[]{R.attr.album_element_color});
        final int color = ta.getColor(0, 0);
        ta.recycle();
        navigationIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN);

        TextView tvSelectedAlbum = findViewById(R.id.tv_selected_album);
        tvSelectedAlbum.setVisibility(View.GONE);
        llOriginLayout = findViewById(R.id.ll_origin_layout);
        llOriginLayout.setOnClickListener(this);

        tvPreview = findViewById(R.id.tv_preview);
        tvPreview.setOnClickListener(this);

        tvApply = findViewById(R.id.tv_apply);
        tvApply.setOnClickListener(this);

        mEmptyView = findViewById(R.id.empty_view);
        mContainer = findViewById(R.id.container);

        mSelectedCollection.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mOriginalEnable = savedInstanceState.getBoolean(CHECK_STATE);
        }
        updateBottomToolbar();
        adapter = new AlbumsAdapter(this, null, false);
        albumsSpinner = new AlbumsSpinner(this);
        albumsSpinner.setOnClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAlbumCollection.setStateCurrentSelection(position);
                adapter.getCursor().moveToPosition(position);
                onAlbumSelected(adapter.getCursor());
            }
        });
        albumsSpinner.setPopupAnchorView(findViewById(R.id.toolbar));
        albumsSpinner.setSelectedTextView(tvSelectedAlbum);
        albumsSpinner.setAdapter(adapter);
        mAlbumCollection.onCreate(this, this);
        mAlbumCollection.onRestoreInstanceState(savedInstanceState);
        mAlbumCollection.loadAlbums();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSelectedCollection.onSaveInstanceState(outState);
        mAlbumCollection.onSaveInstanceState(outState);
    }

    private void updateBottomToolbar() {
        int count = mSelectedCollection.count();
        if (count == 0) {
            tvPreview.setEnabled(false);
            tvApply.setEnabled(false);
            tvApply.setText(getString(R.string.button_apply_default));
        } else if (count == 1 && mSpec.singleSelectionModeEnabled()) {
            tvPreview.setEnabled(true);
            tvApply.setEnabled(true);
            tvApply.setText(getString(R.string.button_apply_default));
        } else {
            tvPreview.setEnabled(true);
            tvApply.setEnabled(true);
            tvApply.setText(getString(R.string.button_apply, count));
        }

        if (mSpec.originEnable) {
            llOriginLayout.setVisibility(View.VISIBLE);
            // TODO: 2019-09-18  updateOriginalState()
        } else {
            llOriginLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onAlbumLoad(final Cursor cursor) {
        adapter.swapCursor(cursor);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                cursor.moveToPosition(mAlbumCollection.getCurrentSelection());
                albumsSpinner.setSelection(PictureSelectionActivity.this,
                        mAlbumCollection.getCurrentSelection());
                onAlbumSelected(cursor);
            }
        });
    }

    private void onAlbumSelected(Cursor cursor) {
        Album album = Album.valueOf(cursor);
        if (album.isAll() && SelectionSpec.getInstance().capture) {
            album.addCaptureCount();
        }
        if (album.isAll() && album.isEmpty()) {
            mContainer.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mContainer.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
            Fragment fragment = MediaSelectionFragment.newInstance(album);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment, MediaSelectionFragment.class.getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onLoaderReset() {
        adapter.swapCursor(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        mAlbumCollection.onDestroy();
    }
}
