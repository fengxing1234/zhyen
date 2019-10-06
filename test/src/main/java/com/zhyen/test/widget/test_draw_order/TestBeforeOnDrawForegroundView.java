package com.zhyen.test.widget.test_draw_order;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 如果你把绘制代码写在了 super.onDrawForeground() 的上面，
 * 绘制内容就会在 dispatchDraw() 和  super.onDrawForeground() 之间执行，
 * 那么绘制内容会盖住子 View，但被滑动边缘渐变、滑动条以及前景盖住：
 * 由于被半透明黑色遮罩盖住，左上角的标签明显变暗了。
 * <p>
 * 这种写法，和前面 2.1 讲的，重写 dispatchDraw() 并把绘制代码写在 super.dispatchDraw() 的下面的效果是一样的：
 * 绘制内容都会盖住子 View，但被滑动边缘渐变、滑动条以及前景盖住。
 */
public class TestBeforeOnDrawForegroundView extends AppCompatImageView {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        paint.setColor(Color.parseColor("#f44336"));
        paint.setTextSize(30);
    }

    public TestBeforeOnDrawForegroundView(Context context) {
        super(context);
    }

    public TestBeforeOnDrawForegroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestBeforeOnDrawForegroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        Matrix imageMatrix = getImageMatrix();
        canvas.concat(imageMatrix);
        canvas.drawRect(0, 0, 100, 40, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("New", 20, 30, paint);
        super.onDrawForeground(canvas);
    }
}
