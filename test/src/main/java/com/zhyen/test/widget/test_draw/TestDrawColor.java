package com.zhyen.test.widget.test_draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TestDrawColor extends View {


    public TestDrawColor(Context context) {
        super(context);
    }

    public TestDrawColor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestDrawColor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.YELLOW);
    }
}
