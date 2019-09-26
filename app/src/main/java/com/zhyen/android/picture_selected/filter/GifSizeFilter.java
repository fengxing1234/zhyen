package com.zhyen.android.picture_selected.filter;

import android.content.Context;
import android.graphics.Point;

import com.zhyen.android.R;
import com.zhyen.android.picture_selected.MimeType;
import com.zhyen.android.picture_selected.entity.IncapableCause;
import com.zhyen.android.picture_selected.entity.Item;
import com.zhyen.android.picture_selected.util.PhotoMetadataUtils;

import java.util.HashSet;
import java.util.Set;

public class GifSizeFilter extends Filter {

    private int mMinWidth;
    private int mMinHeight;
    private int mMaxSize;

    public GifSizeFilter(int minWidth, int minHeight, int maxSizeInBytes) {
        mMinWidth = minWidth;
        mMinHeight = minHeight;
        mMaxSize = maxSizeInBytes;
    }

    @Override
    public Set<MimeType> constraintTypes() {
        return new HashSet<MimeType>() {{
            add(MimeType.GIF);
        }};
    }

    @Override
    public IncapableCause filter(Context context, Item item) {
        if (!needFiltering(context, item))
            return null;

        Point size = PhotoMetadataUtils.getBitmapBound(context.getContentResolver(), item.getContentUri());
        if (size.x < mMinWidth || size.y < mMinHeight || item.size > mMaxSize) {
            return new IncapableCause(IncapableCause.DIALOG, context.getString(R.string.error_gif, mMinWidth,
                    String.valueOf(PhotoMetadataUtils.getSizeInMB(mMaxSize))));
        }
        return null;
    }

}
