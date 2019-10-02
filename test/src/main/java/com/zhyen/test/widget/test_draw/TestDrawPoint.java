package com.zhyen.test.widget.test_draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhyen.test.R;


public class TestDrawPoint extends View {

    private int centerWidth;
    private int centerHeight;
    private int halfCenterWidth;
    private int halfCenterHeight;

    public TestDrawPoint(Context context) {
        super(context);
        init(context);
    }

    public TestDrawPoint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestDrawPoint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Paint paint;
    private int width;
    private int height;
    private Context context;


    private void init(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.main_2));
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        centerWidth = width / 2;
        centerHeight = height / 2;
        halfCenterWidth = centerWidth / 2;
        halfCenterHeight = centerHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(20);

        //Paint.setStrokeCap(cap) 可以设置点的形状，但这个方法并不是专门用来设置点的形状的，而是一个设置线条端点形状的方法。端点有圆头 (ROUND)、平头 (BUTT) 和方头 (SQUARE) 三种，具体会在下一节里面讲。

        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(halfCenterWidth, halfCenterHeight, paint);

        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(halfCenterWidth + centerWidth, halfCenterHeight, paint);
    }
}
