package com.zhyen.test.widget.test_evaluator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhyen.test.R;

/**
 * 有的时候，你不止需要在一个动画中改变多个属性，还会需要多个动画配合工作，比如，在内容的大小从 0 放大到 100% 大小后开始移动。这种情况使用 PropertyValuesHolder 是不行的，因为这些属性如果放在同一个动画中，需要共享动画的开始时间、结束时间、Interpolator 等等一系列的设定，这样就不能有先后次序地执行动画了。
 * <p>
 * 这就需要用到 AnimatorSet 了。
 * <p>
 * ObjectAnimator animator1 = ObjectAnimator.ofFloat(...);
 * animator1.setInterpolator(new LinearInterpolator());
 * ObjectAnimator animator2 = ObjectAnimator.ofInt(...);
 * animator2.setInterpolator(new DecelerateInterpolator());
 * <p>
 * AnimatorSet animatorSet = new AnimatorSet();
 * // 两个动画依次执行
 * animatorSet.playSequentially(animator1, animator2);
 * animatorSet.start();
 * <p>
 * 使用 playSequentially()，就可以让两个动画依次播放，而不用为它们设置监听器来手动为他们监管协作。
 * <p>
 * // 两个动画同时执行
 * animatorSet.playTogether(animator1, animator2);
 * animatorSet.start();
 * <p>
 * // 使用 AnimatorSet.play(animatorA).with/before/after(animatorB)
 * // 的方式来精确配置各个 Animator 之间的关系
 * animatorSet.play(animator1).with(animator2);
 * animatorSet.play(animator1).before(animator2);
 * animatorSet.play(animator1).after(animator2);
 * animatorSet.start();
 */
public class TestAnimatorSetLayout extends RelativeLayout {
    public TestAnimatorSetLayout(Context context) {
        super(context);
    }

    public TestAnimatorSetLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestAnimatorSetLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        final View view = findViewById(R.id.test_iv);
        findViewById(R.id.test_btn_play).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setTranslationX(-200);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "translationX", -200, 200);
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "rotation", 0, 1080);
                animator3.setDuration(1000);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(animator1).before(animator2);
                animatorSet.playTogether(animator2, animator3);

                animatorSet.start();
            }
        });
    }
}
