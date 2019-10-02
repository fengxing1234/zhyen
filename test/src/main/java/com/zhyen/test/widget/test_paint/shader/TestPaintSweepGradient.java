package com.zhyen.test.widget.test_paint.shader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TestPaintSweepGradient extends View {
    public TestPaintSweepGradient(Context context) {
        super(context);
    }

    public TestPaintSweepGradient(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestPaintSweepGradient(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerW = getWidth() / 2;
        int centerH = getHeight() / 2;
        Paint paint = new Paint();
        SweepGradient sweepGradient = new SweepGradient(centerW, centerH, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"));
        paint.setShader(sweepGradient);
        canvas.drawCircle(centerW, centerH, centerW, paint);
    }
}
