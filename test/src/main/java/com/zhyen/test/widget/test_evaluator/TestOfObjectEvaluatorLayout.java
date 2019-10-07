package com.zhyen.test.widget.test_evaluator;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.zhyen.test.R;

/**
 * 借助于 TypeEvaluator，属性动画就可以通过 ofObject() 来对不限定类型的属性做动画了。方式很简单：
 * <p>
 * 为目标属性写一个自定义的 TypeEvaluator
 * 使用 ofObject() 来创建 Animator，并把自定义的 TypeEvaluator 作为参数填入
 * <p>
 * 另外在 API 21 中，已经自带了 PointFEvaluator 这个类，所以如果你的 minSdk 大于或者等于 21（哈哈哈哈哈哈哈哈），上面这个类你就不用写了，直接用就行了。
 */
public class TestOfObjectEvaluatorLayout extends RelativeLayout {

    private TestOfObjectEvaluatorView view;
    private Button play;

    public TestOfObjectEvaluatorLayout(Context context) {
        super(context);
    }

    public TestOfObjectEvaluatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestOfObjectEvaluatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        view = findViewById(R.id.test_view);
        play = (Button) findViewById(R.id.test_btn_play);
        play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator color = ObjectAnimator.ofObject(view, "position", new PaintEvaluator(), new PointF(0, 0), new PointF(1, 1));
                //ObjectAnimator color = ObjectAnimator.ofArgb(view, "color", 0xffff0000, 0xff00ff00);
                color.setInterpolator(new LinearInterpolator());
                color.setDuration(2000);
                color.start();
            }
        });
    }

    private static class PaintEvaluator implements TypeEvaluator<PointF> {

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            PointF pointF = new PointF();
            pointF.x = startValue.x + (endValue.x - startValue.x) * fraction;
            pointF.y = startValue.y + (endValue.y - startValue.y) * fraction;
            return pointF;
        }
    }
}
