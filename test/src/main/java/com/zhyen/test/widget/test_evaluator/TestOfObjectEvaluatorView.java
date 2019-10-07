package com.zhyen.test.widget.test_evaluator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhyen.base.utils.UIUtils;

/**
 * 借助于 TypeEvaluator，属性动画就可以通过 ofObject() 来对不限定类型的属性做动画了。方式很简单：
 * <p>
 * 为目标属性写一个自定义的 TypeEvaluator
 * 使用 ofObject() 来创建 Animator，并把自定义的 TypeEvaluator 作为参数填入
 * 另外在 API 21 中，已经自带了 PointFEvaluator 这个类，所以如果你的 minSdk 大于或者等于 21（哈哈哈哈哈哈哈哈），上面这个类你就不用写了，直接用就行了。
 */
public class TestOfObjectEvaluatorView extends View {

    private float RADIUS = UIUtils.dpToPixel(20);
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private PointF position = new PointF();

    {
        paint.setColor(Color.parseColor("#009688"));
    }

    public TestOfObjectEvaluatorView(Context context) {
        super(context);
    }

    public TestOfObjectEvaluatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestOfObjectEvaluatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        this.position = position;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float innerPaddingLeft = RADIUS * 1;
        float innterPaddingRight = RADIUS * 1;
        float innterPaddingTop = RADIUS * 1;
        float innterPaddingBottom = RADIUS * 3;
        float width = getWidth() - innerPaddingLeft - innterPaddingRight - RADIUS * 2;
        float height = getHeight() - innterPaddingTop - innterPaddingBottom - RADIUS * 2;
        canvas.drawCircle(innerPaddingLeft + RADIUS + width * position.x, innterPaddingTop + RADIUS + height * position.y, RADIUS, paint);
    }
}
