package com.zhyen.test.widget.test_draw_order;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 如果你把绘制代码写在了 super.onDrawForeground() 的下面，绘制代码会在滑动边缘渐变、滑动条和前景之后被执行，那么绘制内容将会盖住滑动边缘渐变、滑动条和前景。
 */
public class TestAfterOnDrawForegroundView extends AppCompatImageView {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        paint.setColor(Color.parseColor("#f44336"));
        paint.setTextSize(30);
    }

    public TestAfterOnDrawForegroundView(Context context) {
        super(context);
    }

    public TestAfterOnDrawForegroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestAfterOnDrawForegroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        Matrix imageMatrix = getImageMatrix();
        canvas.concat(imageMatrix);
        canvas.drawRect(0, 0, 100, 40, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("New", 20, 30, paint);
    }
}
