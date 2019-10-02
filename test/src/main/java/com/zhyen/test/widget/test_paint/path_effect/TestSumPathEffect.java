package com.zhyen.test.widget.test_paint.path_effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 分别按照两种 PathEffect 分别对目标进行绘制。
 */
public class TestSumPathEffect extends View {

    private Paint textPaint;
    private Paint unPaint;
    private Paint paint;

    public TestSumPathEffect(Context context) {
        super(context);
    }

    public TestSumPathEffect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestSumPathEffect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        PathEffect dashEffect = new DashPathEffect(new float[]{20, 10}, 0);
        PathEffect discreteEffect = new DiscretePathEffect(20, 5);
        SumPathEffect pathEffect = new SumPathEffect(dashEffect, discreteEffect);
        paint.setPathEffect(pathEffect);

        unPaint = new Paint();
        unPaint.setAntiAlias(true);
        unPaint.setStyle(Paint.Style.STROKE);
        unPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();
        path.moveTo(100, 300);
        path.lineTo(200, 500);
        path.lineTo(300, 200);
        path.lineTo(400, 400);
        path.lineTo(500, 250);
        path.lineTo(650, 350);
        canvas.drawPath(path, unPaint);
        canvas.drawText("原图", 700, 400, textPaint);


        int lineHeight = 500;
        path.reset();

        path.moveTo(100, 300 + lineHeight);
        path.lineTo(200, 500 + lineHeight);
        path.lineTo(300, 200 + lineHeight);
        path.lineTo(400, 400 + lineHeight);
        path.lineTo(500, 250 + lineHeight);
        path.lineTo(650, 350 + lineHeight);
        canvas.drawPath(path, paint);
        canvas.drawText("sum模式", 700, 400 + lineHeight, textPaint);

    }
}
