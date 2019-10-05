package com.zhyen.test.widget.draw_order;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * 只要重写 dispatchDraw()，并在 super.dispatchDraw() 的下面写上你的绘制代码，这段绘制代码就会发生在子 View 的绘制之后，从而让绘制内容盖住子 View 了。
 *
 * 把绘制代码写在 super.dispatchDraw() 的上面，这段绘制就会在 onDraw() 之后、  super.dispatchDraw() 之前发生，也就是绘制内容会出现在主体内容和子 View 之间。而这个……
 *
 * 其实和前面 1.1 讲的，重写 onDraw() 并把绘制代码写在 super.onDraw() 之后的做法，效果是一样的。
 */
public class TestViewDispatchDrawLayout extends LinearLayout {
    private Pattern pattern = new Pattern();

    public TestViewDispatchDrawLayout(Context context) {
        super(context);
    }

    public TestViewDispatchDrawLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestViewDispatchDrawLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        pattern.draw(canvas);
    }

    private class Pattern {
        private static final float PATTERN_RATIO = 5f / 6;

        Paint patternPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TestViewDispatchDrawLayout.Pattern.Spot[] spots;

        private Pattern() {
            spots = new TestViewDispatchDrawLayout.Pattern.Spot[4];
            spots[0] = new TestViewDispatchDrawLayout.Pattern.Spot(0.24f, 0.3f, 0.026f);
            spots[1] = new TestViewDispatchDrawLayout.Pattern.Spot(0.69f, 0.25f, 0.067f);
            spots[2] = new TestViewDispatchDrawLayout.Pattern.Spot(0.32f, 0.6f, 0.067f);
            spots[3] = new TestViewDispatchDrawLayout.Pattern.Spot(0.62f, 0.78f, 0.083f);
        }

        private Pattern(TestViewDispatchDrawLayout.Pattern.Spot[] spots) {
            this.spots = spots;
        }

        {
            patternPaint.setColor(Color.parseColor("#A0E91E63"));
        }

        private void draw(Canvas canvas) {
            int repitition = (int) Math.ceil((float) getWidth() / getHeight());
            for (int i = 0; i < spots.length * repitition; i++) {
                TestViewDispatchDrawLayout.Pattern.Spot spot = spots[i % spots.length];
                canvas.drawCircle(i / spots.length * getWidth() * PATTERN_RATIO + spot.relativeX * getWidth(), spot.relativeY * getHeight(), spot.relativeSize * getHeight(), patternPaint);
            }
        }

        private class Spot {
            private float relativeX;
            private float relativeY;
            private float relativeSize;

            private Spot(float relativeX, float relativeY, float relativeSize) {
                this.relativeX = relativeX;
                this.relativeY = relativeY;
                this.relativeSize = relativeSize;
            }
        }
    }
}
