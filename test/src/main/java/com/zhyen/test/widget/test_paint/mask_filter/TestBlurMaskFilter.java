package com.zhyen.test.widget.test_paint.mask_filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhyen.test.R;


/**
 * 它的构造方法 BlurMaskFilter(float radius, BlurMaskFilter.Blur style) 中， radius 参数是模糊的范围， style 是模糊的类型。一共有四种：
 */
public class TestBlurMaskFilter extends View {

    private static final String TAG = TestBlurMaskFilter.class.getSimpleName();
    private Paint textPaint;
    private Paint paint;
    private Paint unPaint;
    private Bitmap bitmap;

    public TestBlurMaskFilter(Context context) {
        super(context);
    }

    public TestBlurMaskFilter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestBlurMaskFilter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        BlurMaskFilter maskfilter = new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL);
        //paint.setMaskFilter(maskfilter);

        unPaint = new Paint();
        unPaint.setAntiAlias(true);
        unPaint.setStyle(Paint.Style.STROKE);
        unPaint.setColor(Color.RED);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.what_the_fuck);
        Log.d(TAG, "init: width = " + bitmap.getWidth() + "====" + bitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, getWidth() / 2 - bitmap.getWidth() / 2, 0, paint);
        String text = "原图";
        int textX = getWidth() / 2 - getTextSize(text, textPaint).width() / 2;
        int textY = bitmap.getHeight() + 50 + getTextSize(text, textPaint).height() / 2;
        canvas.drawText(text, textX, textY, textPaint);


        //solid
        paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID));
        canvas.drawBitmap(bitmap, 100, getHeight() / 3, paint);
        text = "Solid";
        textX = 100 + bitmap.getWidth() / 2 - getTextSize(text, textPaint).width() / 2;
        textY = getHeight() / 3 + bitmap.getHeight() + 50 + getTextSize(text, textPaint).height() / 2;
        canvas.drawText(text, textX, textY, textPaint);

        //normal
        paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));
        canvas.drawBitmap(bitmap, getWidth() / 2 + 100, getHeight() / 3, paint);
        text = "Normal";
        textX = getWidth() / 2 + 100 + bitmap.getWidth() / 2 - getTextSize(text, textPaint).width() / 2;
        textY = getHeight() / 3 + bitmap.getHeight() + 50 + getTextSize(text, textPaint).height() / 2;
        canvas.drawText(text, textX, textY, textPaint);

        //outer
        paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.OUTER));
        canvas.drawBitmap(bitmap, 100, getHeight() * 2 / 3, paint);
        text = "outer";
        textX = 100 + bitmap.getWidth() / 2 - getTextSize(text, textPaint).width() / 2;
        textY = getHeight() * 2 / 3 + bitmap.getHeight() + 50 + getTextSize(text, textPaint).height() / 2;
        canvas.drawText(text, textX, textY, textPaint);

        //inner
        paint.setMaskFilter(new BlurMaskFilter(100, BlurMaskFilter.Blur.INNER));
        canvas.drawBitmap(bitmap, getWidth() / 2 + 100, getHeight() * 2 / 3, paint);
        text = "inner";
        textX = getWidth() / 2 + 100 + bitmap.getWidth() / 2 - getTextSize(text, textPaint).width() / 2;
        textY = getHeight() * 2 / 3 + bitmap.getHeight() + 50 + getTextSize(text, textPaint).height() / 2;
        canvas.drawText(text, textX, textY, textPaint);
    }

    private Rect getTextSize(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }
}