package com.zhyen.test.widget.test_paint.path_effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 使用 PathEffect 来给图形的轮廓设置效果。对 Canvas 所有的图形绘制有效，也就是 drawLine() drawCircle() drawPath()...
 * 把所有拐角变成圆角。
 * 它的构造方法 CornerPathEffect(float radius) 的参数 radius 是圆角的半径。
 */
public class TestCornerPathEffect extends View {
    public TestCornerPathEffect(Context context) {
        super(context);
    }

    public TestCornerPathEffect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestCornerPathEffect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(50);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);

//        CornerPathEffect effect = new CornerPathEffect(40);
//        paint.setPathEffect(effect);
        Path path = new Path();
        path.moveTo(100, 300);
        path.lineTo(200, 500);
        path.lineTo(300, 200);
        path.lineTo(400, 400);
        path.lineTo(500, 250);
        path.lineTo(850, 350);
        canvas.drawPath(path, paint);
        canvas.drawText("未设置CornerPathEffect", 200, 600, textPaint);


        Paint paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setColor(Color.BLACK);
        CornerPathEffect effect = new CornerPathEffect(120);
        paint1.setPathEffect(effect);
        int lineHeight = 500;

        Path path1 = new Path();
        path1.moveTo(100, 300 + lineHeight);
        path1.lineTo(200, 500 + lineHeight);
        path1.lineTo(300, 200 + lineHeight);
        path1.lineTo(400, 400 + lineHeight);
        path1.lineTo(500, 250 + lineHeight);
        path1.lineTo(850, 350 + lineHeight);
        canvas.drawPath(path1, paint1);
        canvas.drawText("设置CornerPathEffect", 200, 600 + lineHeight, textPaint);


        canvas.drawRect(100, 100, getWidth() - 100, getHeight() - 500, paint1);
        canvas.drawText("设置CornerPathEffect", 200, getHeight() - 450, textPaint);

        canvas.drawRect(50, 50, getWidth() - 50, getHeight() - 400, paint);
        canvas.drawText("未设置CornerPathEffect", 200, getHeight() - 350, textPaint);

    }
}
