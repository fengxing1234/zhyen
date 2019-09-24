package com.zhyen.android.picture_selected;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.zhyen.android.R;
import com.zhyen.android.picture_selected.entity.Album;
import com.zhyen.android.picture_selected.entity.Item;
import com.zhyen.android.picture_selected.model.AlbumCollection;
import com.zhyen.android.picture_selected.model.SelectedItemCollection;
import com.zhyen.android.picture_selected.ui.MediaSelectionFragment;
import com.zhyen.android.picture_selected.ui.adapter.AlbumPictureAdapter;
import com.zhyen.android.picture_selected.ui.adapter.AlbumsAdapter;
import com.zhyen.android.picture_selected.ui.widget.AlbumsSpinner;
import com.zhyen.android.picture_selected.util.MediaStoreCompat;

import java.util.ArrayList;

public class PictureSelectionActivity extends AppCompatActivity
        implements View.OnClickListener,
        MediaSelectionFragment.SelectionProvider,
        AlbumPictureAdapter.OnPhotoCapture,
        AlbumCollection.AlbumCallbacks,
        AlbumPictureAdapter.CheckStateListener,
        AlbumPictureAdapter.OnMediaClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = PictureSelectionActivity.class.getSimpleName();

    public static final String EXTRA_RESULT_SELECTION = "extra_result_selection";
    public static final String EXTRA_RESULT_SELECTION_PATH = "extra_result_selection_path";
    public static final String EXTRA_RESULT_ORIGINAL_ENABLE = "extra_result_original_enable";
    private static final String CHECK_STATE = "checkState";
    private static final int REQUEST_CODE_CAPTURE = 24;

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
    private MediaStoreCompat mMediaStoreCompat;

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

        if (mSpec.capture) {
            mMediaStoreCompat = new MediaStoreCompat(this);
            if (mSpec.captureStrategy == null) {
                throw new RuntimeException("Don't forget to set CaptureStrategy.");
            }
            mMediaStoreCompat.setCaptureStrategy(mSpec.captureStrategy);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Drawable navigationIcon = toolbar.getNavigationIcon();
        if (navigationIcon != null) {
            TypedArray ta = getTheme().obtainStyledAttributes(new int[]{R.attr.album_element_color});
            final int color = ta.getColor(0, 0);
            ta.recycle();
            navigationIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }

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
        adapter = new AlbumsAdapter(this, null, true);
        albumsSpinner = new AlbumsSpinner(this);
        albumsSpinner.setOnItemSelectedListener(this);
        albumsSpinner.setSelectedTextView((TextView) findViewById(R.id.tv_selected_album));
        albumsSpinner.setPopupAnchorView(findViewById(R.id.toolbar));
        albumsSpinner.setAdapter(adapter);
        mAlbumCollection.onCreate(this, this);
        mAlbumCollection.onRestoreInstanceState(savedInstanceState);
        mAlbumCollection.loadAlbums();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
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
        Log.d(TAG, "onAlbumLoad: ");
        adapter.swapCursor(cursor);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                cursor.moveToPosition(mAlbumCollection.getCurrentSelection());
                albumsSpinner.setSelection(PictureSelectionActivity.this,
                        mAlbumCollection.getCurrentSelection());
                Album album = Album.valueOf(cursor);
                if (album.isAll() && SelectionSpec.getInstance().capture) {
                    album.addCaptureCount();
                }
                onAlbumSelected(album);
            }
        });
    }

    private void onAlbumSelected(Album album) {
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
        Log.d(TAG, "onLoaderReset: ");
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

    @Override
    public void onUpdate() {
        updateBottomToolbar();

        if (mSpec.onSelectedListener != null) {
            mSpec.onSelectedListener.onSelected(
                    mSelectedCollection.asListOfUri(), mSelectedCollection.asListOfString());
        }
    }


    @Override
    public void onMediaClick(Album album, Item item, int adapterPosition) {

    }

    @Override
    public SelectedItemCollection provideSelectedItemCollection() {
        return mSelectedCollection;
    }

    @Override
    public void capture() {
        if (mMediaStoreCompat != null) {
            mMediaStoreCompat.dispatchCaptureIntent(this, REQUEST_CODE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == REQUEST_CODE_CAPTURE) {
            Uri contentUri = mMediaStoreCompat.getCurrentPhotoUri();
            String path = mMediaStoreCompat.getCurrentPhotoPath();
            ArrayList<Uri> selected = new ArrayList<>();
            selected.add(contentUri);
            ArrayList<String> selectedPath = new ArrayList<>();
            selectedPath.add(path);
            Intent result = new Intent();
            result.putParcelableArrayListExtra(EXTRA_RESULT_SELECTION, selected);
            result.putStringArrayListExtra(EXTRA_RESULT_SELECTION_PATH, selectedPath);
            setResult(RESULT_OK, result);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                PictureSelectionActivity.this.revokeUriPermission(contentUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            new SingleMediaScanner(this.getApplicationContext(), path, new SingleMediaScanner.ScanListener() {
                @Override
                public void onScanFinish() {
                    Log.i("SingleMediaScanner", "scan finish!");
                }
            });
            //finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mAlbumCollection.setStateCurrentSelection(position);
        adapter.getCursor().moveToPosition(position);
        Album album = Album.valueOf(adapter.getCursor());
        if (album.isAll() && SelectionSpec.getInstance().capture) {
            album.addCaptureCount();
        }
        onAlbumSelected(album);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
