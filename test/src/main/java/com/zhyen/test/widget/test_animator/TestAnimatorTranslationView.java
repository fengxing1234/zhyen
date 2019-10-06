package com.zhyen.test.widget.test_animator;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zhyen.test.R;

import static com.zhyen.base.utils.UIUtils.dpToPixel;

/**
 * 使用方式：View.animate() 后跟 translationX() 等方法，动画会自动执行。
 * <p>
 * View 的每个方法都对应了 ViewPropertyAnimator 的两个方法，其中一个是带有  -By 后缀的，例如，View.setTranslationX() 对应了 ViewPropertyAnimator.translationX() 和  ViewPropertyAnimator.translationXBy() 这两个方法。其中带有 -By() 后缀的是增量版本的方法，例如，translationX(100) 表示用动画把 View 的 translationX 值渐变为 100，而  translationXBy(100) 则表示用动画把 View 的 translationX 值渐变地增加 100。l
 */
public class TestAnimatorTranslationView extends RelativeLayout {

    private Button btnPlay;
    private ImageView imageView;

    private int mAnimatorState;

    public TestAnimatorTranslationView(Context context) {
        super(context);
    }

    public TestAnimatorTranslationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestAnimatorTranslationView(Context context, AttributeSet attrs, int defStyleAttr) {
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
                switch (mAnimatorState % 6) {
                    case 0:
                        imageView.animate().translationX(500);
                        break;
                    case 1:
                        imageView.animate().translationX(0);
                        break;
                    case 2:
                        imageView.animate().translationY(1000);
                        break;
                    case 3:
                        imageView.animate().translationY(0);
                        break;
                    case 4:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            imageView.animate().translationZ(500);
                        }
                        break;
                    case 5:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            imageView.animate().translationZ(0);
                        }
                        break;
                }
                mAnimatorState++;
            }
        });
    }
}
