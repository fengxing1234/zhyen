package com.zhyen.test.widget.test_paint.path_effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 离散的路径效果
 * 把线条进行随机的偏离，让轮廓变得乱七八糟。乱七八糟的方式和程度由参数决定。
 * <p>
 * DiscretePathEffect 具体的做法是，把绘制改为使用定长的线段来拼接，并且在拼接的时候对路径进行随机偏离。
 * 它的构造方法 DiscretePathEffect(float segmentLength, float deviation) 的两个参数中，
 * segmentLength 是用来拼接的每个线段的长度， deviation 是偏离量。这两个值设置得不一样，显示效果也会不一样
 */
public class TestDiscretePathEffect extends View {

    private Paint textPaint;
    private Paint paint;
    private Paint unPaint;

    public TestDiscretePathEffect(Context context) {
        super(context);
    }

    public TestDiscretePathEffect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
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
        DiscretePathEffect pathEffect = new DiscretePathEffect(20, 8);
        paint.setPathEffect(pathEffect);

        unPaint = new Paint();
        unPaint.setAntiAlias(true);
        unPaint.setStyle(Paint.Style.STROKE);
        unPaint.setColor(Color.RED);
    }

    public TestDiscretePathEffect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        canvas.drawText("Discrete模式", 700, 400 + lineHeight, textPaint);


    }

}
