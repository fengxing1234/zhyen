package com.zhyen.test.widget.test_evaluator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TestArgbEvaluatorView extends View {

    private int color = 0xffff0000;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {

    }

    public TestArgbEvaluatorView(Context context) {
        super(context);
    }

    public TestArgbEvaluatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestArgbEvaluatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    public int getColor() {
        return color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        paint.setColor(color);
        canvas.drawCircle(width / 2, height / 2, width / 6, paint);
    }
}
