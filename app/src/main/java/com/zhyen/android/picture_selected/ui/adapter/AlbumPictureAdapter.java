package com.zhyen.android.picture_selected.ui.adapter;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumPictureAdapter extends RecyclerView.Adapter<AlbumPictureAdapter.AlbumPictureHolder> {


    @NonNull
    @Override
    public AlbumPictureHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumPictureHolder albumPuctureHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class AlbumPictureHolder extends RecyclerView.ViewHolder {

        public AlbumPictureHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
