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

import com.zhyen.android.R;
import com.zhyen.android.picture_selected.entity.Album;
import com.zhyen.android.picture_selected.entity.Item;
import com.zhyen.android.picture_selected.model.AlbumCollection;
import com.zhyen.android.picture_selected.model.SelectedItemCollection;
import com.zhyen.android.picture_selected.ui.AlbumPreviewActivity;
import com.zhyen.android.picture_selected.ui.BasePreviewActivity;
import com.zhyen.android.picture_selected.ui.MediaSelectionFragment;
import com.zhyen.android.picture_selected.ui.SelectedPreviewActivity;
import com.zhyen.android.picture_selected.ui.adapter.AlbumPictureAdapter;
import com.zhyen.android.picture_selected.ui.adapter.AlbumsAdapter;
import com.zhyen.android.picture_selected.ui.widget.AlbumsSpinner;
import com.zhyen.android.picture_selected.ui.widget.IncapableDialog;
import com.zhyen.android.picture_selected.ui.widget.SelectionCheckRadioView;
import com.zhyen.android.picture_selected.util.MediaStoreCompat;
import com.zhyen.android.picture_selected.util.PhotoMetadataUtils;

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
    private static final int REQUEST_CODE_PREVIEW = 23;

    private SelectedItemCollection mSelectedCollection = new SelectedItemCollection(this);
    private final AlbumCollection mAlbumCollection = new AlbumCollection();
    private TextView tvPreview;
    private TextView tvApply;
    private SelectionSpec mSpec;
    private LinearLayout llOriginLayout;
    private AlbumsAdapter adapter;
    private AlbumsSpinner albumsSpinner;

    private boolean mOriginalEnable;
    private FrameLayout mEmptyView;
    private FrameLayout mContainer;
    private MediaStoreCompat mMediaStoreCompat;
    private SelectionCheckRadioView mCheckView;
    private MediaSelectionFragment fragment;

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
        mCheckView = findViewById(R.id.selection_check_radio_view);

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
            updateOriginalState();
        } else {
            llOriginLayout.setVisibility(View.GONE);
        }
    }

    private void updateOriginalState() {
        mCheckView.setChecked(mOriginalEnable);
        if (countOverMaxSize() > 0) {

            if (mOriginalEnable) {
                IncapableDialog incapableDialog = IncapableDialog.newInstance("",
                        getString(R.string.error_over_original_size, mSpec.originalMaxSize));
                incapableDialog.show(getSupportFragmentManager(),
                        IncapableDialog.class.getName());

                mCheckView.setChecked(false);
                mOriginalEnable = false;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_preview) {
            Intent intent = new Intent(this, SelectedPreviewActivity.class);
            intent.putExtra(BasePreviewActivity.EXTRA_DEFAULT_BUNDLE, mSelectedCollection.getDataWithBundle());
            intent.putExtra(BasePreviewActivity.EXTRA_RESULT_ORIGINAL_ENABLE, mOriginalEnable);
            startActivityForResult(intent, REQUEST_CODE_PREVIEW);
        } else if (v.getId() == R.id.tv_apply) {
            Intent result = new Intent();
            ArrayList<Uri> selectedUris = (ArrayList<Uri>) mSelectedCollection.asListOfUri();
            result.putParcelableArrayListExtra(EXTRA_RESULT_SELECTION, selectedUris);
            ArrayList<String> selectedPaths = (ArrayList<String>) mSelectedCollection.asListOfString();
            result.putStringArrayListExtra(EXTRA_RESULT_SELECTION_PATH, selectedPaths);
            result.putExtra(EXTRA_RESULT_ORIGINAL_ENABLE, mOriginalEnable);
            setResult(RESULT_OK, result);
            finish();
        } else if (v.getId() == R.id.ll_origin_layout) {
            int count = countOverMaxSize();
            if (count > 0) {
                IncapableDialog incapableDialog = IncapableDialog.newInstance("",
                        getString(R.string.error_over_original_count, count, mSpec.originalMaxSize));
                incapableDialog.show(getSupportFragmentManager(),
                        IncapableDialog.class.getName());
                return;
            }
            mOriginalEnable = !mOriginalEnable;
            mCheckView.setChecked(mOriginalEnable);

            if (mSpec.onCheckedListener != null) {
                mSpec.onCheckedListener.onCheck(mOriginalEnable);
            }
        }
    }

    private int countOverMaxSize() {
        int count = 0;
        int selectedCount = mSelectedCollection.count();
        for (int i = 0; i < selectedCount; i++) {
            Item item = mSelectedCollection.asList().get(i);
            if (item.isImage()) {
                float size = PhotoMetadataUtils.getSizeInMB(item.size);
                if (size > mSpec.originalMaxSize) {
                    count++;
                }
            }
        }
        return count;
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
            fragment = MediaSelectionFragment.newInstance(album);
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
        Intent intent = new Intent(this, AlbumPreviewActivity.class);
        intent.putExtra(AlbumPreviewActivity.EXTRA_ALBUM, album);
        intent.putExtra(AlbumPreviewActivity.EXTRA_ITEM, item);
        intent.putExtra(BasePreviewActivity.EXTRA_DEFAULT_BUNDLE, mSelectedCollection.getDataWithBundle());
        intent.putExtra(BasePreviewActivity.EXTRA_RESULT_ORIGINAL_ENABLE, mOriginalEnable);
        startActivityForResult(intent, REQUEST_CODE_PREVIEW);
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
            final Uri contentUri = mMediaStoreCompat.getCurrentPhotoUri();
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
//                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
//                    sendBroadcast(intent);
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
