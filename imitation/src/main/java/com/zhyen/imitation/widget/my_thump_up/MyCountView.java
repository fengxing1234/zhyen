package com.zhyen.imitation.widget.my_thump_up;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhyen.base.utils.UIUtils;
import com.zhyen.imitation.R;
import com.zhyen.imitation.widget.ThumbPoint;

public class MyCountView extends View {

    private static final String TAG = "MyCountView";

    private static final int COUNT_ANIM_DURING = 250;
    public static final float DEFAULT_TEXT_SIZE = 15f;
    public static final String DEFAULT_TEXT_COLOR = "#cccccc";

    private int mTextColor;

    private Paint mTextPaint;
    private float mTextSize;
    private int mCount;
    //mTexts[0]是不变的部分 ，(mTexts[1]和mTexts[1]是变换的部分) mTexts[1]原来的部分，mTexts[2]变化后的部分
    private String[] mTexts;
    private ThumbPoint[] mTextPoints;
    private boolean mCountToBigger;
    private float mMinOffsetY;
    private float mMaxOffsetY;
    private float mOldOffset;
    private float mNewOffset;
    private float mFraction;
    private int mEndTextColor;

    public MyCountView(Context context) {
        this(context, null);
    }

    public MyCountView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCountView);
        mCount = typedArray.getInt(R.styleable.MyCountView_mcv_count, 0);
        mTextColor = typedArray.getColor(R.styleable.MyCountView_mcv_text_color, Color.parseColor(DEFAULT_TEXT_COLOR));
        mTextSize = typedArray.getDimension(R.styleable.MyCountView_mcv_text_size, UIUtils.sp2px(context, DEFAULT_TEXT_SIZE));
        typedArray.recycle();
        init();
    }

    private void init() {
        mTextSize = UIUtils.sp2px(getContext(), 16);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(mTextSize);

        mTexts = new String[3];
        mTextPoints = new ThumbPoint[3];
        mTextPoints[0] = new ThumbPoint();
        mTextPoints[1] = new ThumbPoint();
        mTextPoints[2] = new ThumbPoint();

        mMinOffsetY = 0;
        mMaxOffsetY = mTextSize;

        mEndTextColor = Color.argb(0, Color.red(mTextColor), Color.green(mTextColor), Color.blue(mTextColor));

        calculateChangeNum(0);
    }

    private void calculateLocation() {
        String count = String.valueOf(mCount);
        float textWidth = mTextPaint.measureText(count) / count.length();
        float unChangeText = textWidth * mTexts[0].length();
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float y = getPaddingTop() + (getContentHeight() - fontMetrics.bottom - fontMetrics.top) / 2;
        //数字未改变 的位置
        mTextPoints[0].x = getPaddingLeft();
        mTextPoints[0].y = y;
        //变换部分 原来的数字位置
        mTextPoints[1].x = getPaddingLeft() + unChangeText;
        mTextPoints[1].y = y - mOldOffset;
        //变换部分 数字变换后的位置
        mTextPoints[2].x = getPaddingLeft() + unChangeText;
        mTextPoints[2].y = y - mNewOffset;
    }

    /**
     * mTexts[0]是不变的部分 ，(mTexts[1]和mTexts[1]是变换的部分) mTexts[1]原来的部分，mTexts[2]变化后的部分
     *
     * @param change
     */
    public void calculateChangeNum(int change) {
        if (change == 0) {
            mTexts[0] = String.valueOf(mCount);
            mTexts[1] = "";
            mTexts[2] = "";
            return;
        }
        String oldNum = String.valueOf(mCount);
        String newNum = String.valueOf(mCount + change);

        for (int i = 0; i < oldNum.length(); i++) {
            char oldChar = oldNum.charAt(i);
            char newChar = newNum.charAt(i);
            if (oldChar != newChar) {
                mTexts[0] = i == 0 ? "" : newNum.substring(0, i);
                mTexts[1] = oldNum.substring(i);
                mTexts[2] = newNum.substring(i);
                break;
            }
        }
        mCount += change;
        startAnim(change > 0);
    }

    private void startAnim(boolean isToBigger) {
        mCountToBigger = isToBigger;
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "offsetY", mMinOffsetY, mCountToBigger ? mMaxOffsetY : -mMaxOffsetY);
        animator.setDuration(COUNT_ANIM_DURING);
        animator.start();
    }

    private void setOffsetY(float offsetY) {
        //变大是从[0,1]，变小是[0,-1]
        mOldOffset = offsetY;
        //数字变大（0-1-2）动画属性offsetY 从0-textSize(48) 数字view 从屏幕下往上 移动 所以 对应的offsetY 应该是减小 反之亦反。
        if (mCountToBigger) {
            mNewOffset = offsetY - mMaxOffsetY;
        } else {
            mNewOffset = offsetY + mMaxOffsetY;
        }
        Log.d(TAG, "mOldOffset: " + mOldOffset + "  mNewOffset: " + mNewOffset);
        mFraction = (mMaxOffsetY - Math.abs(offsetY)) / (mMaxOffsetY - mMinOffsetY);
        calculateLocation();
        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: w = " + w + " h = " + h + "  oldw = " + oldw + " oldh = " + oldh);

        Log.d(TAG, "onSizeChanged: getX = " + getX() + "getY = " + getY());

        calculateLocation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                UIUtils.getDefaultSize(widthMeasureSpec, getContentWidth() + getPaddingLeft() + getPaddingRight()),
                UIUtils.getDefaultSize(heightMeasureSpec, getContentHeight() + getPaddingTop() + getPaddingBottom())
        );
    }

    private int getContentHeight() {
        return (int) mTextSize;
    }

    private int getContentWidth() {
        return (int) Math.ceil(mTextPaint.measureText(String.valueOf(mCount)));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint testPaint = new Paint();
        testPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, getWidth() - 4, getHeight() - 4, testPaint);

        //不变的部分
        mTextPaint.setColor(mTextColor);
        canvas.drawText(String.valueOf(mTexts[0]), mTextPoints[0].x, mTextPoints[0].y, mTextPaint);

        //变化前部分
        mTextPaint.setColor((Integer) UIUtils.evaluate(mFraction, mEndTextColor, mTextColor));
        canvas.drawText(String.valueOf(mTexts[1]), mTextPoints[1].x, mTextPoints[1].y, mTextPaint);

        //变化后部分
        mTextPaint.setColor((Integer) UIUtils.evaluate(mFraction, mTextColor, mEndTextColor));
        canvas.drawText(String.valueOf(mTexts[2]), mTextPoints[2].x, mTextPoints[2].y, mTextPaint);

    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int mCount) {
        this.mCount = mCount;
        calculateChangeNum(0);
        requestLayout();
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        mEndTextColor = Color.argb(0, Color.red(mTextColor), Color.green(mTextColor), Color.blue(mTextColor));
        postInvalidate();
    }

    public void setTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
        requestLayout();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle data = new Bundle();
        data.putParcelable("superData", super.onSaveInstanceState());
        data.putInt("count", mCount);
        data.putFloat("textSize", mTextSize);
        data.putInt("textColor", mTextColor);
        return data;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle data = (Bundle) state;
        Parcelable superData = data.getParcelable("superData");
        super.onRestoreInstanceState(superData);

        mCount = data.getInt("count", 0);
        mTextSize = data.getFloat("textSize", UIUtils.sp2px(getContext(), DEFAULT_TEXT_SIZE));
        mTextColor = data.getInt("textColor", Color.parseColor(DEFAULT_TEXT_COLOR));

        init();
    }

}
