package com.zhyen.android.test.test_utils;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zhyen.android.R;

public class TestUtilsActivity extends AppCompatActivity {

    private static final String TAG = "TestUtilsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_utils_activity);
        Button test_btn_query = (Button) findViewById(R.id.test_btn_query);
        test_btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageForPath();
            }
        });
    }

    private void getImageForPath() {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(
                uri,
                null,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " = ?",
                new String[]{"RDZA778899"},
                null
        );
        if (cursor == null) {
            return;
        }
        int indexPhotoPath = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        while (cursor.moveToNext()) {
            //打印图片的路径
            Log.i("uri:", cursor.getString(indexPhotoPath));
        }
        cursor.close();
    }
}
