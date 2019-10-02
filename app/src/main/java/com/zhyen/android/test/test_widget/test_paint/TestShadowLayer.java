package com.zhyen.android.test.test_widget.test_paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 在之后的绘制内容下面加一层阴影。
 * radius 是阴影的模糊范围； dx dy 是阴影的偏移量；  shadowColor 是阴影的颜色。
 * 如果要清除阴影层，使用 clearShadowLayer() 。
 * 注意：
 * <p>
 * 在硬件加速开启的情况下， setShadowLayer() 只支持文字的绘制，文字之外的绘制必须关闭硬件加速才能正常绘制阴影。
 * <p>
 * 如果 shadowColor 是半透明的，阴影的透明度就使用 shadowColor 自己的透明度；而如果  shadowColor 是不透明的，阴影的透明度就使用 paint 的透明度。
 */
public class TestShadowLayer extends View {
    public TestShadowLayer(Context context) {
        super(context);
    }

    public TestShadowLayer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestShadowLayer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setShadowLayer(100, 0, 40, Color.RED);
        paint.setTextSize(50);
        String text = "Hello World";
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        canvas.drawText(text, getWidth() / 2 - rect.width() / 2, getHeight() / 2, paint);
    }
}
