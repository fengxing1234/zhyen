package com.zhyen.test.widget.test_evaluator;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhyen.test.R;

/**
 * 很多时候，你在同一个动画中会需要改变多个属性，例如在改变透明度的同时改变尺寸。如果使用  ViewPropertyAnimator，你可以直接用连写的方式来在一个动画中同时改变多个属性：
 * <p>
 * view.animate()
 * .scaleX(1)
 * .scaleY(1)
 * .alpha(1);
 * <p>
 * 而对于 ObjectAnimator，是不能这么用的。不过你可以使用 PropertyValuesHolder 来同时在一个动画中改变多个属性。
 * <p>
 * PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX", 1);
 * PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY", 1);
 * PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha", 1);
 * <p>
 * ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, holder1, holder2, holder3)
 * animator.start();
 *
 * PropertyValuesHolder 的意思从名字可以看出来，它是一个属性值的批量存放地。所以你如果有多个属性需要修改，可以把它们放在不同的 PropertyValuesHolder 中，然后使用  ofPropertyValuesHolder() 统一放进 Animator。这样你就不用为每个属性单独创建一个 Animator 分别执行了。
 */
public class TestPropertyValuesHolderLayout extends RelativeLayout {
    public TestPropertyValuesHolderLayout(Context context) {
        super(context);
    }

    public TestPropertyValuesHolderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestPropertyValuesHolderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        View btn = findViewById(R.id.test_btn_play);
        final View view = findViewById(R.id.test_iv);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0, 1);
                PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0, 1);
                PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0, 1);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY, alpha);
                animator.start();
            }
        });
    }
}
