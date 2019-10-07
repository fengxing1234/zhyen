package com.zhyen.test.widget.test_evaluator;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.zhyen.test.R;

/**
 * TypeEvaluator 最经典的用法是使用 ArgbEvaluator 来做颜色渐变的动画。
 * <p>
 * 另外，在 Android 5.0 （API 21） 加入了新的方法 ofArgb()，所以如果你的 minSdk 大于或者等于 21（哈哈哈哈哈哈哈哈），你可以直接用下面这种方式：
 */
public class TestArgbEvaluatorLayout extends RelativeLayout {

    private Button play;
    private TestArgbEvaluatorView view;

    public TestArgbEvaluatorLayout(Context context) {
        super(context);
    }

    public TestArgbEvaluatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestArgbEvaluatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
                ObjectAnimator color = ObjectAnimator.ofInt(view, "color", 0xffff0000, 0xff00ff00);
                //ObjectAnimator color = ObjectAnimator.ofArgb(view, "color", 0xffff0000, 0xff00ff00);
                color.setEvaluator(new ArgbEvaluator());
                color.setInterpolator(new LinearInterpolator());
                color.setDuration(2000);
                color.start();
            }
        });
    }
}
