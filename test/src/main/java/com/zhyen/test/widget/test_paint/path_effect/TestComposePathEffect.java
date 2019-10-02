package com.zhyen.test.widget.test_paint.path_effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 这也是一个组合效果类的 PathEffect 。不过它是先对目标 Path 使用一个 PathEffect，然后再对这个改变后的 Path 使用另一个 PathEffect。
 * <p>
 * 它的构造方法 ComposePathEffect(PathEffect outerpe, PathEffect innerpe) 中的两个  PathEffect 参数， innerpe 是先应用的， outerpe 是后应用的。所以上面的代码就是「先偏离，再变虚线」。而如果把两个参数调换，就成了「先变虚线，再偏离」。
 * <p>
 * PathEffect 在有些情况下不支持硬件加速，需要关闭硬件加速才能正常使用：
 * <p>
 * Canvas.drawLine() 和 Canvas.drawLines() 方法画直线时，setPathEffect() 是不支持硬件加速的；
 * PathDashPathEffect 对硬件加速的支持也有问题，所以当使用 PathDashPathEffect 的时候，最好也把硬件加速关了。e
 */
public class TestComposePathEffect extends View {

    private Paint textPaint;
    private Paint unPaint;
    private Paint paint;

    public TestComposePathEffect(Context context) {
        super(context);
    }

    public TestComposePathEffect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestComposePathEffect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        ComposePathEffect pathEffect = new ComposePathEffect(dashEffect, discreteEffect);
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
        canvas.drawText("compose模式", 700, 400 + lineHeight, textPaint);

    }
}
