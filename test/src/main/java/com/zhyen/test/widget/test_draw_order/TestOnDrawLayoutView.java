package com.zhyen.test.widget.test_draw_order;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * 继承了一个 LinearLayout，重写了它的 onDraw() 方法，在 super.onDraw() 中插入了你自己的绘制代码，使它能够在内部绘制一些斑点作为点缀：
 * <p>
 * 当你添加了子 View 之后，你的斑点不见了
 * <p>
 * 造成这种情况的原因是 Android 的绘制顺序：在绘制过程中，每一个 ViewGroup 会先调用自己的  onDraw() 来绘制完自己的主体之后再去绘制它的子 View。对于上面这个例子来说，就是你的  LinearLayout 会在绘制完斑点后再去绘制它的子 View。那么在子 View 绘制完成之后，先前绘制的斑点就被子 View 盖住了。
 * <p>
 * 具体来讲，这里说的「绘制子 View」是通过另一个绘制方法的调用来发生的，这个绘制方法叫做：dispatchDraw()。也就是说，在绘制过程中，每个 View 和 ViewGroup 都会先调用 onDraw() 方法来绘制主体，再调用 dispatchDraw() 方法来绘制子 View。
 * <p>
 * 注：虽然 View 和 ViewGroup 都有 dispatchDraw() 方法，不过由于 View 是没有子 View 的，所以一般来说 dispatchDraw() 这个方法只对 ViewGroup（以及它的子类）有意义。
 * <p>
 * 只要重写 dispatchDraw()，并在 super.dispatchDraw() 的下面写上你的绘制代码，这段绘制代码就会发生在子 View 的绘制之后，从而让绘制内容盖住子 View 了。
 */
public class TestOnDrawLayoutView extends LinearLayout {
    Pattern pattern = new Pattern();

    {
        setWillNotDraw(false);
    }

    public TestOnDrawLayoutView(Context context) {
        super(context);
    }

    public TestOnDrawLayoutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestOnDrawLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pattern.draw(canvas);

    }

    private class Pattern {
        private static final float PATTERN_RATIO = 5f / 6;

        Paint patternPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TestOnDrawLayoutView.Pattern.Spot[] spots;

        private Pattern() {
            spots = new TestOnDrawLayoutView.Pattern.Spot[4];
            spots[0] = new TestOnDrawLayoutView.Pattern.Spot(0.24f, 0.3f, 0.026f);
            spots[1] = new TestOnDrawLayoutView.Pattern.Spot(0.69f, 0.25f, 0.067f);
            spots[2] = new TestOnDrawLayoutView.Pattern.Spot(0.32f, 0.6f, 0.067f);
            spots[3] = new TestOnDrawLayoutView.Pattern.Spot(0.62f, 0.78f, 0.083f);
        }

        private Pattern(TestOnDrawLayoutView.Pattern.Spot[] spots) {
            this.spots = spots;
        }

        {
            patternPaint.setColor(Color.parseColor("#A0E91E63"));
        }

        private void draw(Canvas canvas) {
            int repitition = (int) Math.ceil((float) getWidth() / getHeight());
            for (int i = 0; i < spots.length * repitition; i++) {
                TestOnDrawLayoutView.Pattern.Spot spot = spots[i % spots.length];
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
