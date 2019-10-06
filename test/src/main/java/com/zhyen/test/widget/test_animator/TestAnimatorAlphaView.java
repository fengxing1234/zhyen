package com.zhyen.test.widget.test_animator;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.RelativeLayout;

import com.zhyen.test.R;

import static com.zhyen.base.utils.UIUtils.dpToPixel;

public class TestAnimatorAlphaView extends RelativeLayout {

    private View imageView;
    private View btnPlay;
    private int mAnimatorState;

    public TestAnimatorAlphaView(Context context) {
        super(context);
    }

    public TestAnimatorAlphaView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestAnimatorAlphaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        imageView = findViewById(R.id.test_iv);
        btnPlay = findViewById(R.id.test_btn_play);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    Path path = new Path();
                    path.moveTo(0, dpToPixel(10));
                    path.lineTo(dpToPixel(7), dpToPixel(2));
                    path.lineTo(dpToPixel(116), dpToPixel(58));
                    path.lineTo(dpToPixel(116), dpToPixel(70));
                    path.lineTo(dpToPixel(7), dpToPixel(128));
                    path.lineTo(0, dpToPixel(120));
                    path.close();
                }
            });
        }
        btnPlay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (mAnimatorState % 2) {
                    case 0:
                        imageView.animate().alpha(0f);
                        break;
                    case 1:
                        imageView.animate().alpha(1f);
                        break;
                }
                mAnimatorState++;
            }
        });
    }
}
