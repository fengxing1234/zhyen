package com.zhyen.test.widget.test_animator.test_object_animator;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.view.animation.PathInterpolatorCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.zhyen.test.R;

public class TestObjectAnimatorLayout extends RelativeLayout {

    private Button testBtn;
    private AppCompatSpinner spinner;
    private TestObjectAnimatorView view;
    private Interpolator[] interpolators = new Interpolator[13];

    private Path interpolatorPath;

    {
        interpolatorPath = new Path();
        interpolatorPath.lineTo(0.25f, 0.25f);
        interpolatorPath.moveTo(0.25f, 1.5f);
        interpolatorPath.lineTo(1, 1);

        interpolators[0] = new AccelerateDecelerateInterpolator();
        interpolators[1] = new LinearInterpolator();
        interpolators[2] = new AccelerateInterpolator();
        interpolators[3] = new DecelerateInterpolator();
        interpolators[4] = new AnticipateInterpolator();
        interpolators[5] = new OvershootInterpolator();
        interpolators[6] = new AnticipateOvershootInterpolator();
        interpolators[7] = new BounceInterpolator();
        interpolators[8] = new CycleInterpolator(0.5f);
        interpolators[9] = PathInterpolatorCompat.create(interpolatorPath);
        interpolators[10] = new FastOutLinearInInterpolator();
        interpolators[11] = new FastOutSlowInInterpolator();
        interpolators[12] = new LinearOutSlowInInterpolator();
    }

    public TestObjectAnimatorLayout(Context context) {
        super(context);
    }

    public TestObjectAnimatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestObjectAnimatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("ObjectAnimatorBinding")
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        testBtn = (Button) findViewById(R.id.test_btn_play);
        spinner = findViewById(R.id.test_spinner);
        view = (TestObjectAnimatorView) findViewById(R.id.test_view);
        testBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "progress", 0, 0.99f);
                animator.setDuration(1000);
                animator.setInterpolator(interpolators[spinner.getSelectedItemPosition()]);
                animator.start();
            }
        });
    }
}
