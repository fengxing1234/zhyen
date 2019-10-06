package com.zhyen.test.widget.test_draw_order;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 由于 draw() 是总调度方法，所以如果把绘制代码写在 super.draw() 的下面，那么这段代码会在其他所有绘制完成之后再执行，也就是说，它的绘制内容会盖住其他的所有绘制内容。
 * <p>
 * 它的效果和重写 onDrawForeground()，并把绘制代码写在 super.onDrawForeground() 下面时的效果是一样的：都会盖住其他的所有内容。
 * <p>
 * 当然了，虽说它们效果一样，但如果你既重写 draw() 又重写 onDrawForeground() ，那么 draw() 里的内容还是会盖住 onDrawForeground() 里的内容的。所以严格来讲，它们的效果还是有一点点不一样的。
 * <p>
 * 但这属于抬杠……
 */
public class TestAfterDrawView extends AppCompatImageView {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        paint.setTextSize(30);
    }


    public TestAfterDrawView(Context context) {
        super(context);
    }

    public TestAfterDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestAfterDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        paint.setColor(Color.parseColor("#f44336"));
        Matrix imageMatrix = getImageMatrix();
        canvas.concat(imageMatrix);
        canvas.drawRect(0, 0, 100, 40, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("New", 20, 30, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
