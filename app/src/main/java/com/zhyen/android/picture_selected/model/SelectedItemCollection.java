package com.zhyen.android.picture_selected.model;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import com.zhyen.android.R;
import com.zhyen.android.picture_selected.SelectionSpec;
import com.zhyen.android.picture_selected.entity.IncapableCause;
import com.zhyen.android.picture_selected.entity.Item;
import com.zhyen.android.picture_selected.ui.widget.CheckView;
import com.zhyen.android.picture_selected.util.PhotoMetadataUtils;
import com.zhyen.android.test.test_luban.PathUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SelectedItemCollection {

    private static final String TAG = SelectedItemCollection.class.getSimpleName();

    public static final String STATE_SELECTION = "state_selection";
    public static final String STATE_COLLECTION_TYPE = "state_collection_type";

    /**
     * Collection only with images
     */
    public static final int COLLECTION_IMAGE = 0x01;
    /**
     * Collection only with videos
     */
    public static final int COLLECTION_VIDEO = 0x01 << 1;
    /**
     * Collection with images and videos.
     */
    public static final int COLLECTION_MIXED = COLLECTION_IMAGE | COLLECTION_VIDEO;

    /**
     * Empty collection
     */
    public static final int COLLECTION_UNDEFINED = 0x00;

    private LinkedList<Item> mItems;
    private int mCollectionType = COLLECTION_UNDEFINED;
    private Context mContext;

    public SelectedItemCollection(Context context) {
        this.mContext = context;
    }

    public void onCreate(Bundle bundle) {
        if (bundle == null) {
            mItems = new LinkedList<>();
        } else {
            ArrayList<Item> list = bundle.getParcelableArrayList(STATE_SELECTION);
            if (list == null) {
                list = new ArrayList<>();
            }
            mItems = new LinkedList<>(list);
            mCollectionType = bundle.getInt(STATE_COLLECTION_TYPE, COLLECTION_UNDEFINED);
        }
    }

    public void setDefaultSelection(List<Item> uris) {
        mItems.addAll(uris);
    }

    public int count() {
        return mItems.size();
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_SELECTION, new ArrayList<>(mItems));
        outState.putInt(STATE_COLLECTION_TYPE, mCollectionType);
    }

    public int checkedNumOf(Item item) {
        int index = new ArrayList<>(mItems).indexOf(item);
        return index == -1 ? CheckView.UNCHECKED : index + 1;
    }

    public boolean maxSelectableReached() {
        return mItems.size() == currentMaxSelectable();
    }

    private int currentMaxSelectable() {
        SelectionSpec spec = SelectionSpec.getInstance();
        if (spec.maxSelectable > 0) {
            return spec.maxSelectable;
        } else if (mCollectionType == COLLECTION_IMAGE) {
            return spec.maxImageSelectable;
        } else if (mCollectionType == COLLECTION_VIDEO) {
            return spec.maxVideoSelectable;
        } else {
            return spec.maxSelectable;
        }
    }

    public boolean isSelected(Item item) {
        return mItems.contains(item);
    }

    public boolean isEmpty() {
        return mItems == null || mItems.isEmpty();
    }

    public boolean add(Item item) {
        if (typeConflict(item)) {
            throw new IllegalArgumentException("Can't select images and videos at the same time.");
        }
        boolean added = mItems.add(item);
        if (added) {
            if (mCollectionType == COLLECTION_UNDEFINED) {
                if (item.isImage()) {
                    mCollectionType = COLLECTION_IMAGE;
                } else if (item.isVideo()) {
                    mCollectionType = COLLECTION_VIDEO;
                }
            } else if (mCollectionType == COLLECTION_IMAGE) {
                if (item.isVideo()) {
                    mCollectionType = COLLECTION_MIXED;
                }
            } else if (mCollectionType == COLLECTION_VIDEO) {
                if (item.isImage()) {
                    mCollectionType = COLLECTION_MIXED;
                }
            }
        }
        return added;
    }

    public boolean remove(Item item) {
        boolean removed = mItems.remove(item);
        if (removed) {
            if (mItems.size() == 0) {
                mCollectionType = COLLECTION_UNDEFINED;
            } else {
                if (mCollectionType == COLLECTION_MIXED) {
                    refineCollectionType();
                }
            }
        }
        return removed;
    }

    private void refineCollectionType() {
        boolean hasImage = false;
        boolean hasVideo = false;
        for (Item i : mItems) {
            if (i.isImage() && !hasImage) hasImage = true;
            if (i.isVideo() && !hasVideo) hasVideo = true;
        }
        if (hasImage && hasVideo) {
            mCollectionType = COLLECTION_MIXED;
        } else if (hasImage) {
            mCollectionType = COLLECTION_IMAGE;
        } else if (hasVideo) {
            mCollectionType = COLLECTION_VIDEO;
        }
    }

    /**
     * 媒体类型确定是否会有冲突。用户只能选择图片和视频在同一时间{@link SelectionSpec # mediaTypeExclusive}是设置为false。
     * Determine whether there will be conflict media types. A user can only select images and videos at the same time
     * while {@link SelectionSpec#mediaTypeExclusive} is set to false.
     */
    public boolean typeConflict(Item item) {
        return SelectionSpec.getInstance().mediaTypeExclusive
                && ((item.isImage() && (mCollectionType == COLLECTION_VIDEO || mCollectionType == COLLECTION_MIXED))
                || (item.isVideo() && (mCollectionType == COLLECTION_IMAGE || mCollectionType == COLLECTION_MIXED)));
    }

    public IncapableCause isAcceptable(Item item) {
        if (maxSelectableReached()) {
            int maxSelectable = currentMaxSelectable();
            String cause;

            try {
                cause = mContext.getString(
                        R.string.error_over_count,
                        maxSelectable
                );
            } catch (Resources.NotFoundException e) {
                cause = mContext.getString(
                        R.string.error_over_count,
                        maxSelectable
                );
            } catch (NoClassDefFoundError e) {
                cause = mContext.getString(
                        R.string.error_over_count,
                        maxSelectable
                );
            }

            return new IncapableCause(cause);
        } else if (typeConflict(item)) {
            return new IncapableCause(mContext.getString(R.string.error_type_conflict));
        }

        return PhotoMetadataUtils.isAcceptable(mContext, item);
    }

    public List<Uri> asListOfUri() {
        List<Uri> uris = new ArrayList<>();
        for (Item item : mItems) {
            uris.add(item.getContentUri());
        }
        return uris;
    }

    public List<String> asListOfString() {
        List<String> paths = new ArrayList<>();
        for (Item item : mItems) {
            paths.add(PathUtils.getPath(mContext, item.getContentUri()));
        }
        return paths;
    }

    public Bundle getDataWithBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STATE_SELECTION, new ArrayList<>(mItems));
        bundle.putInt(STATE_COLLECTION_TYPE, mCollectionType);
        return bundle;
    }

    public List<Item> asList() {
        return new ArrayList<>(mItems);
    }

    public void overwrite(ArrayList<Item> items, int collectionType) {
        if (items.size() == 0) {
            mCollectionType = COLLECTION_UNDEFINED;
        } else {
            mCollectionType = collectionType;
        }
        mItems.clear();
        mItems.addAll(items);
    }
}
