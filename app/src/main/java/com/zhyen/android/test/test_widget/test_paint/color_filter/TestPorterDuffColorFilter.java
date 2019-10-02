package com.zhyen.android.test.test_widget.test_paint.color_filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhyen.android.R;

/**
 * 这个 PorterDuffColorFilter 的作用是使用一个指定的颜色和一种指定的 PorterDuff.Mode 来与绘制对象进行合成。它的构造方法是 PorterDuffColorFilter(int color, PorterDuff.Mode mode) 其中的 color 参数是指定的颜色， mode 参数是指定的 Mode。同样也是 PorterDuff.Mode ，不过和  ComposeShader 不同的是，PorterDuffColorFilter 作为一个 ColorFilter，只能指定一种颜色作为源，而不是一个 Bitmap。
 */
public class TestPorterDuffColorFilter extends View {

    public TestPorterDuffColorFilter(Context context) {
        super(context);
    }

    public TestPorterDuffColorFilter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestPorterDuffColorFilter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.what_the_fuck);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.OVERLAY));
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }
}
