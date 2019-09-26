package com.zhyen.android.picture_selected;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Set;

public final class Matisse {

    private final WeakReference<Activity> mContext;
    private final WeakReference<Fragment> mFragment;


    private Matisse(Activity activity) {
        this(activity, null);
    }

    private Matisse(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private Matisse(Activity activity, Fragment fragment) {
        mContext = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }

    public static Matisse from(Activity activity) {
        return new Matisse(activity);
    }

    public static Matisse from(Fragment fragment) {
        return new Matisse(fragment);
    }

    public static List<Uri> obtainResult(Intent data) {
        return data.getParcelableArrayListExtra(PictureSelectionActivity.EXTRA_RESULT_SELECTION);
    }

    public static List<String> obtainPathResult(Intent data) {
        return data.getStringArrayListExtra(PictureSelectionActivity.EXTRA_RESULT_SELECTION_PATH);
    }

    public static boolean obtainOriginalState(Intent data) {
        return data.getBooleanExtra(PictureSelectionActivity.EXTRA_RESULT_ORIGINAL_ENABLE, false);
    }

    public SelectionCreator choose(Set<MimeType> mimeTypes) {
        return this.choose(mimeTypes, true);
    }
    public SelectionCreator choose(Set<MimeType> mimeTypes, boolean mediaTypeExclusive) {
        return new SelectionCreator(this, mimeTypes, mediaTypeExclusive);
    }

    @Nullable
    Activity getActivity() {
        return mContext.get();
    }

    @Nullable
    Fragment getFragment() {
        return mFragment != null ? mFragment.get() : null;
    }

}
