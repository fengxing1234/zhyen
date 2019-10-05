package com.zhyen.test.widget.draw_order;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 如果把绘制代码写在 super.onDraw() 的上面，由于绘制代码会执行在原有内容的绘制之前，所以绘制的内容会被控件的原内容盖住。
 * <p>
 * 相对来说，这种用法的场景就会少一些。不过只是少一些而不是没有，比如你可以通过在文字的下层绘制纯色矩形来作为「强调色」：
 */
public class TestViewBeforeOnDrawView extends AppCompatTextView {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        paint.setColor(Color.YELLOW);
    }

    public TestViewBeforeOnDrawView(Context context) {
        super(context);
    }

    public TestViewBeforeOnDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestViewBeforeOnDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Layout layout = getLayout();
        float left1 = layout.getLineLeft(1);
        float right1 = layout.getLineRight(1);
        int top1 = layout.getLineTop(1);
        int bottom1 = layout.getLineBottom(1);

        float left5 = layout.getLineLeft(5);
        float right5 = layout.getLineRight(5);
        int top5 = layout.getLineTop(5);
        int bottom5 = layout.getLineBottom(5);

        canvas.drawRect(left1, top1, right1, bottom1, paint);
        canvas.drawRect(left5, top5, right5, bottom5, paint);

        super.onDraw(canvas);
    }
}
