package com.zhyen.test.widget.draw_order;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * 同理，由于 draw() 是总调度方法，所以如果把绘制代码写在 super.draw() 的上面，
 * 那么这段代码会在其他所有绘制之前被执行，所以这部分绘制内容会被其他所有的内容盖住，包括背景。是的，背景也会盖住它。
 */
public class TestBeforeDrawView extends AppCompatEditText {
    public TestBeforeDrawView(Context context) {
        super(context);
    }

    public TestBeforeDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestBeforeDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#66BB6A")); // 涂上绿色
        super.draw(canvas);
    }
}
