package com.zhyen.test.widget.test_draw;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.Nullable;

public class TestDrawHistogramView extends View {
    private Paint mAxisPaint;

    private Paint mPaint;

    // 需要绘制的值
    private float[] mValues = new float[7];

    // 坐标原点
    private float[] mAxisZero = new float[2];

    // 文字的坐标
    private float[] mTextXCoordinator = new float[7];

    // 每一个柱状的宽度
    private float mItemWidth = 0;

    private int mScreenWidth;

    private int mItemSpace;

    private Interpolator mInterpolator = new BounceInterpolator();

    private float mAnimatorValue;

    public TestDrawHistogramView(Context context) {
        this(context, null);
    }

    public TestDrawHistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        mAxisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAxisPaint.setStyle(Paint.Style.STROKE);
        mAxisPaint.setColor(Color.GREEN);
        mAxisPaint.setTextAlign(Paint.Align.CENTER);
        mAxisPaint.setTextSize(30);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);

        // 动画
        ValueAnimator animator = new ValueAnimator();
        animator.setFloatValues(0, 1);
        animator.setDuration(3000);
        animator.setInterpolator(mInterpolator);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        animator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mScreenWidth = w - oldw;

        // 总的是11个
        mItemWidth = mScreenWidth / 11 + 0.5f;

        mPaint.setStrokeWidth(mItemWidth);

        mItemSpace = (int) (mItemWidth / 4 + 0.5f);

        mValues[0] = 50;
        mValues[1] = 100;
        mValues[2] = 100;
        mValues[3] = 300;
        mValues[4] = 400;
        mValues[5] = 450;
        mValues[6] = 400;

        mAxisZero[0] = mItemWidth;
        mAxisZero[1] = 500 + 50;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图

        drawAxis(canvas);
        drawValues(canvas);


    }

    private void drawValues(Canvas canvas) {

        canvas.save();

        for (int i = 0; i < mValues.length; i++) {
            // 加入动画的动态变换值
            float yValue = mAxisZero[1] - mValues[i] * mAnimatorValue;
            canvas.drawLine(mTextXCoordinator[i], mAxisZero[1], mTextXCoordinator[i], yValue, mPaint);
        }

        canvas.restore();

    }

    private void drawAxis(Canvas canvas) {
        canvas.save();

        // x轴
        canvas.drawLine(mAxisZero[0], mAxisZero[1], mScreenWidth - mItemWidth + 30, mAxisZero[1], mAxisPaint);
        // y轴
        canvas.drawLine(mAxisZero[0], mAxisZero[1], mAxisZero[0], 20, mAxisPaint);

        // 文字
        for (int i = 0; i < mValues.length; i++) {
            // 文字的X坐标
            mTextXCoordinator[i] = mAxisZero[0] + mItemSpace * (i + 1) + (i + 1) * mItemWidth - mItemWidth * 0.5f;
        }

        drawAxisText(canvas);

        canvas.restore();
    }

    private void drawAxisText(Canvas canvas) {

        float textYCoordinator = mAxisZero[1] + 30;

        canvas.drawText("Froyo", mTextXCoordinator[0], textYCoordinator, mAxisPaint);
        canvas.drawText("GB", mTextXCoordinator[1], textYCoordinator, mAxisPaint);
        canvas.drawText("IC S", mTextXCoordinator[2], textYCoordinator, mAxisPaint);
        canvas.drawText("JB", mTextXCoordinator[3], textYCoordinator, mAxisPaint);
        canvas.drawText("KitKat", mTextXCoordinator[4], textYCoordinator, mAxisPaint);
        canvas.drawText("L", mTextXCoordinator[5], textYCoordinator, mAxisPaint);
        canvas.drawText("M", mTextXCoordinator[6], textYCoordinator, mAxisPaint);
    }
}
