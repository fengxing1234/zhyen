package com.zhyen.android.test.test_widget.test_paint.path_effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 使用虚线来绘制线条。
 *
 * 构造方法 DashPathEffect(float[] intervals, float phase) 中，
 * 第一个参数 intervals 是一个数组，它指定了虚线的格式：
 * 数组中元素必须为偶数（最少是 2 个），按照「画线长度、空白长度、画线长度、空白长度」……的顺序排列，
 * 例如上面代码中的 20, 5, 10, 5 就表示虚线是按照「画 20 像素、空 5 像素、画 10 像素、空 5 像素」的模式来绘制；
 * 第二个参数 phase 是虚线的偏移量。
 */
public class TestDashPathEffect extends View {

    private Paint textPaint;
    private Paint paint;
    private Paint unPaint;


    public TestDashPathEffect(Context context) {
        super(context);
    }

    public TestDashPathEffect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestDashPathEffect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        DashPathEffect pathEffect = new DashPathEffect(new float[]{20, 10, 5, 10}, 10);
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
        canvas.drawText("Dash模式", 700, 400 + lineHeight, textPaint);

    }
}
