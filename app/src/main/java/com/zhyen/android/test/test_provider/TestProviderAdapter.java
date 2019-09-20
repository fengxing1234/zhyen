package com.zhyen.android.test.test_provider;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhyen.android.R;

public class TestProviderAdapter extends RecyclerView.Adapter<TestProviderAdapter.TestProviderViewHolder> {

    private Cursor cursor;
    private View.OnClickListener listener;

    public TestProviderAdapter(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TestProviderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.test_item_provider, viewGroup, false);
        view.setOnClickListener(listener);
        return new TestProviderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestProviderViewHolder testProviderViewHolder, int i) {
        cursor.moveToPosition(i);
        testProviderViewHolder.tvTitle.setText(cursor.getString(cursor.getColumnIndex(TestPictureContract.IPicture.TITLE)));
        testProviderViewHolder.tvAuthor.setText(cursor.getString(cursor.getColumnIndex(TestPictureContract.IPicture.AUTHOR)));
    }

    @Override
    public int getItemCount() {
        if (isDataValid(cursor)) {
            return cursor.getCount();
        } else {
            return 0;
        }
    }


    private boolean isDataValid(Cursor cursor) {
        return cursor != null && !cursor.isClosed();
    }

    public void swapCursor(Cursor data) {
        if (cursor == data) {
            return;
        }
        if (data != null) {
            this.cursor = data;
            notifyDataSetChanged();
        }
    }

    public Cursor getCursor() {
        return cursor;
    }

    public static class TestProviderViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private final TextView tvAuthor;

        public TestProviderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.test_tv_title);
            tvAuthor = itemView.findViewById(R.id.test_tv_author);
        }
    }
}
