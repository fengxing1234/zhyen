package com.zhyen.android.picture_selected.ui.adapter;


import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhyen.android.R;
import com.zhyen.android.picture_selected.SelectionSpec;
import com.zhyen.android.picture_selected.entity.Album;
import com.zhyen.android.picture_selected.entity.Item;
import com.zhyen.android.picture_selected.model.SelectedItemCollection;
import com.zhyen.android.picture_selected.ui.widget.CheckView;
import com.zhyen.android.picture_selected.ui.widget.MediaGrid;
import com.zhyen.android.utils.ScreenUtils;

public class AlbumPictureAdapter extends RecyclerViewCursorAdapter<RecyclerView.ViewHolder> implements MediaGrid.OnMediaGridClickListener {

    private static final int VIEW_TYPE_CAPTURE = 0x01;
    private static final int VIEW_TYPE_MEDIA = 0x02;
    private final Drawable mPlaceholder;
    private final SelectedItemCollection mSelectedCollection;
    private final SelectionSpec mSelectionSpec;
    private final RecyclerView mRecyclerView;
    private CheckStateListener mCheckStateListener;
    private OnMediaClickListener mOnMediaClickListener;
    private int mImageResize;

    public AlbumPictureAdapter(Context context, SelectedItemCollection selectedCollection, RecyclerView recyclerView) {
        mSelectionSpec = SelectionSpec.getInstance();
        mSelectedCollection = selectedCollection;

        TypedArray ta = context.getTheme().obtainStyledAttributes(new int[]{R.attr.item_placeholder});
        mPlaceholder = ta.getDrawable(0);
        ta.recycle();
        mRecyclerView = recyclerView;
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        if (holder instanceof CaptureViewHolder) {
            CaptureViewHolder captureViewHolder = (CaptureViewHolder) holder;
            Drawable[] drawables = captureViewHolder.tvHint.getCompoundDrawables();

            TypedArray ta = captureViewHolder.itemView.getContext().getTheme().obtainStyledAttributes(new int[R.attr.capture_textColor]);
            int color = ta.getColor(0, 0);
            ta.recycle();

            for (int i = 0; i < drawables.length; i++) {
                Drawable drawable = drawables[i];
                if (drawable != null) {
                    final Drawable.ConstantState state = drawable.getConstantState();
                    if (state == null) {
                        continue;
                    }
                    // TODO: 2019-09-23 ConstantState 使用问题
                    Drawable newDrawable = state.newDrawable().mutate();
                    newDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                    newDrawable.setBounds(drawable.getBounds());
                    drawables[i] = newDrawable;
                }
            }
            captureViewHolder.tvHint.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        }

        if (holder instanceof AlbumPictureHolder) {
            Item item = Item.valueOf(cursor);
            AlbumPictureHolder albumPictureHolder = (AlbumPictureHolder) holder;
            albumPictureHolder.mediaGrid.preBindMedia(new MediaGrid.PreBindInfo(
                    getImageResize(albumPictureHolder.mediaGrid.getContext()),
                    mPlaceholder,
                    mSelectionSpec.countable,
                    holder
            ));
            albumPictureHolder.mediaGrid.bindMedia(item);
            albumPictureHolder.mediaGrid.setOnMediaGridClickListener(this);
            setCheckStatus(item, albumPictureHolder.mediaGrid);
        }
    }

    @Override
    protected int getItemViewType(int position, Cursor cursor) {
        return Item.valueOf(cursor).isCapture() ? VIEW_TYPE_CAPTURE : VIEW_TYPE_MEDIA;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == VIEW_TYPE_CAPTURE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_capture_item, parent, false);
            holder = new CaptureViewHolder(view);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getContext() instanceof OnPhotoCapture) {
                        ((OnPhotoCapture) v.getContext()).capture();
                    }
                }
            });
        }

        if (viewType == VIEW_TYPE_MEDIA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_grid_item, parent, false);
            holder = new AlbumPictureHolder(view);
        }
        return holder;
    }

    private void setCheckStatus(Item item, MediaGrid mediaGrid) {

    }

    private int getImageResize(Context context) {
        if (mImageResize == 0) {
            RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
            int spanCount = ((GridLayoutManager) manager).getSpanCount();
            int screenWidth = ScreenUtils.screenWidth(context);
            int availableWidth = screenWidth - context.getResources().getDimensionPixelSize(R.dimen.media_grid_spacing) * (spanCount - 1);
            mImageResize = availableWidth / spanCount;
            mImageResize = (int) (mImageResize * mSelectionSpec.thumbnailScale);
        }
        return mImageResize;
    }

    @Override
    public void onThumbnailClicked(ImageView thumbnail, Item item, RecyclerView.ViewHolder holder) {

    }

    @Override
    public void onCheckViewClicked(CheckView checkView, Item item, RecyclerView.ViewHolder holder) {

    }

    public void registerCheckStateListener(CheckStateListener listener) {
        mCheckStateListener = listener;
    }

    public void unregisterCheckStateListener() {
        mCheckStateListener = null;
    }

    public void registerOnMediaClickListener(OnMediaClickListener listener) {
        mOnMediaClickListener = listener;
    }

    public void unregisterOnMediaClickListener() {
        mOnMediaClickListener = null;
    }


    public static class AlbumPictureHolder extends RecyclerView.ViewHolder {

        private MediaGrid mediaGrid;

        public AlbumPictureHolder(@NonNull View itemView) {
            super(itemView);
            mediaGrid = (MediaGrid) itemView;
        }
    }

    public static class CaptureViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHint;

        public CaptureViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHint = itemView.findViewById(R.id.tv_hint);
        }
    }

    public interface OnPhotoCapture {
        void capture();
    }

    public interface CheckStateListener {
        void onUpdate();
    }

    public interface OnMediaClickListener {
        void onMediaClick(Album album, Item item, int adapterPosition);
    }
}
