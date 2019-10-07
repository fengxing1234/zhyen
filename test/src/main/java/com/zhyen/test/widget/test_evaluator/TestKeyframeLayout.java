package com.zhyen.test.widget.test_evaluator;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.zhyen.test.R;
import com.zhyen.test.widget.test_animator.test_object_animator.TestObjectAnimatorView;

/**
 * 把同一个属性拆分
 * 除了合并多个属性和调配多个动画，你还可以在 PropertyValuesHolder 的基础上更进一步，通过设置  Keyframe （关键帧），把同一个动画属性拆分成多个阶段。例如，你可以让一个进度增加到 100% 后再「反弹」回来。
 * <p>
 * 第二部分，「关于复杂的属性关系来做动画」，就这么三种：
 * <p>
 * 使用 PropertyValuesHolder 来对多个属性同时做动画；
 * 使用 AnimatorSet 来同时管理调配多个动画；
 * PropertyValuesHolder 的进阶使用：使用 PropertyValuesHolder.ofKeyframe() 来把一个属性拆分成多段，执行更加精细的属性动画。
 */
public class TestKeyframeLayout extends RelativeLayout {

    private TestObjectAnimatorView view;

    public TestKeyframeLayout(Context context) {
        super(context);
    }

    public TestKeyframeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestKeyframeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        view = findViewById(R.id.test_iv);
        findViewById(R.id.test_btn_play).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Keyframe keyframe = Keyframe.ofFloat(0, 0);//开始：progress 为 0
                Keyframe keyframe1 = Keyframe.ofFloat(0.5f, 1);//进行到一半是，progres 为 100
                Keyframe keyframe3 = Keyframe.ofFloat(1, 0.8f); // 结束时倒回到 80
                PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("progress", keyframe, keyframe1, keyframe3);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, holder);
                animator.setDuration(3000);
                animator.setInterpolator(new FastOutSlowInInterpolator());
                animator.start();
            }
        });
    }
}
