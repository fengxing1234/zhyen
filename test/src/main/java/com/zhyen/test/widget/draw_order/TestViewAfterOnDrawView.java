package com.zhyen.test.widget.draw_order;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 把绘制代码写在 super.onDraw() 的下面，由于绘制代码会在原有内容绘制结束之后才执行，所以绘制内容就会盖住控件原来的内容。
 * <p>
 * 这是最为常见的情况：为控件增加点缀性内容。比如，在 Debug 模式下绘制出 ImageView 的图像尺寸信息：
 */
public class TestViewAfterOnDrawView extends AppCompatImageView {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        paint.setColor(Color.parseColor("#FFC107"));
        paint.setTextSize(28);
    }

    public TestViewAfterOnDrawView(Context context) {
        super(context);
    }

    public TestViewAfterOnDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestViewAfterOnDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = getDrawable();
        if (drawable != null) {
            canvas.save();
            Matrix imageMatrix = getImageMatrix();
            canvas.concat(imageMatrix);
            Rect bounds = drawable.getBounds();
            String text = bounds.width() + " * " + bounds.height();
            canvas.drawText(text, 20, 40, paint);
            canvas.restore();
        }
    }
}
