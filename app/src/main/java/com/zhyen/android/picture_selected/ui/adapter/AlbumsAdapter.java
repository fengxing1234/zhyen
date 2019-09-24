package com.zhyen.android.picture_selected.ui.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhyen.android.R;
import com.zhyen.android.picture_selected.SelectionSpec;
import com.zhyen.android.picture_selected.entity.Album;

import java.io.File;

public class AlbumsAdapter extends CursorAdapter {


    private Drawable placeholder;

    //autoRequery 当数据库更新，cursor是否更新
    public AlbumsAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        TypedArray ta = context.getTheme().obtainStyledAttributes(new int[]{R.attr.album_thumbnail_placeholder});
        placeholder = ta.getDrawable(0);
        ta.recycle();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.albums_list_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Album album = Album.valueOf(cursor);
        ImageView ivAlbumCover = view.findViewById(R.id.iv_album_cover);
        TextView tvAlbumName = view.findViewById(R.id.tv_album_name);
        TextView tvAlbumCount = view.findViewById(R.id.tv_album_count);

        tvAlbumName.setText(album.getDisplayName(context));
        tvAlbumCount.setText(String.valueOf(album.count));

        int resize = context.getResources().getDimensionPixelSize(R.dimen.media_grid_size);
        SelectionSpec.getInstance().imageEngine.loadThumbnail(context, resize, placeholder,
                ivAlbumCover, Uri.fromFile(new File(album.coverPath)));
    }
}
