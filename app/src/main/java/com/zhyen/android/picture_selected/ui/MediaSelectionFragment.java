package com.zhyen.android.picture_selected.ui;


import android.support.v4.app.Fragment;

import com.zhyen.android.picture_selected.entity.Album;

public class MediaSelectionFragment extends Fragment {
    public static Fragment newInstance(Album album) {
        return new MediaSelectionFragment();
    }
}
