package com.zhyen.android.test.test_widget.test_paint.mask_filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhyen.android.R;

/**
 * 它的构造方法  EmbossMaskFilter(float[] direction, float ambient, float specular, float blurRadius) 的参数里，
 * direction 是一个 3 个元素的数组，指定了光源的方向； ambient 是环境光的强度，数值范围是 0 到 1； specular 是炫光的系数； blurRadius 是应用光线的范围。
 */
public class TestEmbossMaskFilter extends View {

    private Paint textPaint;
    private Paint paint;
    private Paint unPaint;
    private Bitmap bitmap;

    public TestEmbossMaskFilter(Context context) {
        super(context);
    }

    public TestEmbossMaskFilter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestEmbossMaskFilter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(50);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);

        unPaint = new Paint();
        unPaint.setAntiAlias(true);
        unPaint.setStyle(Paint.Style.STROKE);
        unPaint.setColor(Color.RED);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.what_the_fuck);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, getWidth() / 2 - bitmap.getWidth() / 2, 0, paint);
        String text = "原图";
        int textX = getWidth() / 2 - getTextSize(text, textPaint).width() / 2;
        int textY = bitmap.getHeight() + 50 + getTextSize(text, textPaint).height() / 2;
        canvas.drawText(text, textX, textY, textPaint);


        Paint p = new Paint();
        p.setMaskFilter(new EmbossMaskFilter(new float[]{0, 1, 1}, 0.2f, 8, 10));
        canvas.drawBitmap(bitmap, getWidth() / 2 - bitmap.getWidth() / 2, getHeight() / 3, p);
        text = "EmbossMaskFilter";
        textX = getWidth() / 2 - getTextSize(text, textPaint).width() / 2;
        textY = getHeight() / 3 + bitmap.getHeight() + 50 + getTextSize(text, textPaint).height() / 2;
        canvas.drawText(text, textX, textY, textPaint);

    }


    private Rect getTextSize(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }
}
