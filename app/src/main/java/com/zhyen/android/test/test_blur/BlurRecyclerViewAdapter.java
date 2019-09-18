package com.zhyen.android.test.test_blur;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhyen.android.R;

class BlurRecyclerViewAdapter extends RecyclerView.Adapter {
    private final Context context;
    private static final int ITEM_COUNT = 10;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public BlurRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new HeaderHolder(LayoutInflater.from(context).inflate(R.layout.test_blur_recyclerview_header, parent, false));
        }
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.test_blur_recyclerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class HeaderHolder extends RecyclerView.ViewHolder {
        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }
}
