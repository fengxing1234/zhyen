package com.zhyen.android.test.test_blur;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.zhyen.android.R;
import com.zhyen.android.utils.BitmapUtils;
import com.zhyen.android.utils.BlurUtils;

public class TestSimpleBlurActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_simple_blur);
        ImageView imageView = (ImageView) findViewById(R.id.iv_test_blur);
        Drawable drawable = getResources().getDrawable(R.drawable.test_image);
        Bitmap bitmap = BitmapUtils.drawableToBitmap(drawable);
        Bitmap blur = BlurUtils.blur(this, bitmap);
        imageView.setImageBitmap(blur);
    }
}
