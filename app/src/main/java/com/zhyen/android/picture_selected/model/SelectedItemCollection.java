package com.zhyen.android.picture_selected.model;

import android.content.Context;
import android.os.Bundle;

import com.zhyen.android.picture_selected.entity.Item;

import java.util.ArrayList;
import java.util.LinkedList;

public class SelectedItemCollection {

    private static final String STATE_SELECTION = "state_selection";
    private static final String STATE_COLLECTION_TYPE = "state_collection_type";

    /**
     * Empty collection
     */
    public static final int COLLECTION_UNDEFINED = 0x00;

    private LinkedList<Item> mItems;
    private int mCollectionType;

    public SelectedItemCollection(Context context) {

    }

    public void onCreate(Bundle bundle) {
        if (bundle == null) {
            mItems = new LinkedList<>();
        } else {
            ArrayList<Item> list = bundle.getParcelableArrayList(STATE_SELECTION);
            mItems = new LinkedList<>(list);
            mCollectionType = bundle.getInt(STATE_COLLECTION_TYPE, COLLECTION_UNDEFINED);
        }
    }

    public int count() {
        return 5;
    }
}
