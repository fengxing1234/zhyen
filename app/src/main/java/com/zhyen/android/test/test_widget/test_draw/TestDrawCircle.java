package com.zhyen.android.test.test_widget.test_draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhyen.android.R;
import com.zhyen.android.utils.ScreenUtils;

public class TestDrawCircle extends View {

    private Paint paint;
    private int width;
    private int height;
    private Context context;

    public TestDrawCircle(Context context) {
        super(context);
        init(context);
    }

    public TestDrawCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestDrawCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.main_2));
        //paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerWidth = width / 2;
        int centerHeight = height / 2;
        int halfCenterWidth = centerWidth / 2;
        int halfCenterHeight = centerHeight / 2;

        //实心圆
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(halfCenterWidth, halfCenterHeight, Math.min(halfCenterHeight, halfCenterWidth) * 0.85f, paint);
        //空心圆
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(halfCenterWidth + centerWidth, halfCenterHeight, Math.min(halfCenterHeight, halfCenterWidth) * 0.85f, paint);

        //蓝色实心圆
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(halfCenterWidth, halfCenterHeight + centerHeight, Math.min(halfCenterHeight, halfCenterWidth) * 0.85f, paint);

        //4.线宽为 20 的空心圆
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20 * ScreenUtils.getDensity(context));
        paint.setColor(Color.BLUE);
        canvas.drawCircle(halfCenterWidth + centerWidth, halfCenterHeight + centerHeight, Math.min(halfCenterHeight, halfCenterWidth) * 0.85f, paint);
    }
}
