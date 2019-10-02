package com.zhyen.android.test.test_widget.test_paint.color_filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhyen.android.R;

/**
 * 参数里的 mul 和  add 都是和颜色值格式相同的 int 值，其中 mul 用来和目标像素相乘，add 用来和目标像素相加：
 * R' = R * mul.R / 0xff + add.R
 * G' = G * mul.G / 0xff + add.G
 * B' = B * mul.B / 0xff + add.B
 * <p>
 * 一个「保持原样」的「基本 LightingColorFilter 」，mul 为 0xffffff，add 为 0x000000（也就是0），那么对于一个像素，它的计算过程就是：
 * R' = R * 0xff / 0xff + 0x0 = R // R' = R
 * G' = G * 0xff / 0xff + 0x0 = G // G' = G
 * B' = B * 0xff / 0xff + 0x0 = B // B' = B
 * <p>
 * 基于这个「基本 LightingColorFilter 」，你就可以修改一下做出其他的 filter。比如，如果你想去掉原像素中的红色，可以把它的 mul 改为 0x00ffff （红色部分为 0 ） ，那么它的计算过程就是：
 * <p>
 * R' = R * 0x0 / 0xff + 0x0 = 0 // 红色被移除
 * G' = G * 0xff / 0xff + 0x0 = G
 * B' = B * 0xff / 0xff + 0x0 = B
 *
 * 或者，如果你想让它的绿色更亮一些，就可以把它的 add 改为 0x003000 （绿色部分为 0x30 ），那么它的计算过程就是：
 *
 * R' = R * 0xff / 0xff + 0x0 = R
 * G' = G * 0xff / 0xff + 0x30 = G + 0x30 // 绿色被加强
 * B' = B * 0xff / 0xff + 0x0 = B
 */
public class TestLightingColorFilter extends View {
    public TestLightingColorFilter(Context context) {
        super(context);
    }

    public TestLightingColorFilter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestLightingColorFilter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.what_the_fuck);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setColorFilter(new LightingColorFilter(0xff00ff, 0x000000));
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }
}
