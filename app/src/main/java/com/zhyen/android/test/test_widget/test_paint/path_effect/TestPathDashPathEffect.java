package com.zhyen.android.test.test_widget.test_paint.path_effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * PathDashPathEffect(Path shape, float advance, float phase, PathDashPathEffect.Style style) 中，
 * shape 参数是用来绘制的 Path ；
 * advance 是两个相邻的 shape 段之间的间隔，不过注意，这个间隔是两个 shape 段的起点的间隔，而不是前一个的终点和后一个的起点的距离；
 * phase 和  DashPathEffect 中一样，是虚线的偏移；
 * 最后一个参数 style，是用来指定拐弯改变的时候 shape 的转换方式。style 的类型为 PathDashPathEffect.Style ，是一个 enum ，具体有三个值：
 *
 * TRANSLATE：位移
 * ROTATE：旋转
 * MORPH：变体
 */
public class TestPathDashPathEffect extends View {

    private Paint textPaint;
    private Paint unPaint;
    private Paint paint;

    public TestPathDashPathEffect(Context context) {
        super(context);
    }

    public TestPathDashPathEffect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestPathDashPathEffect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(200, 100);
        path.lineTo(150, 150);
        path.close(); // 使用 close() 封闭子图形。等价于 path.lineTo(100, 100)
        PathDashPathEffect pathEffect = new PathDashPathEffect(path, 40, 0, PathDashPathEffect.Style.TRANSLATE);
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
        canvas.drawText("PathDash模式", 700, 900 + lineHeight, textPaint);

    }
}
