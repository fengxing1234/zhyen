package com.zhyen.android.test.test_widget.test_draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhyen.android.R;

public class TestDrawArc extends View {
    public TestDrawArc(Context context) {
        super(context);
        init(context);
    }

    public TestDrawArc(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestDrawArc(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Paint paint;
    private int width;
    private int height;
    private Context context;
    private int centerWidth;
    private int centerHeight;
    private int halfCenterWidth;
    private int halfCenterHeight;


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

        RectF rectF = new RectF(halfCenterWidth, halfCenterHeight, centerWidth + halfCenterWidth, centerHeight + halfCenterHeight);

        // 绘制扇形
        canvas.drawArc(rectF, -110, 100, true, paint);

        // 绘制弧形
        canvas.drawArc(rectF, 20, 140, false, paint);

        //绘制不封口的弧形
        paint.setStyle(Paint.Style.STROKE); // 画线模式
        canvas.drawArc(rectF, 180, 60, false, paint);


    }
}
