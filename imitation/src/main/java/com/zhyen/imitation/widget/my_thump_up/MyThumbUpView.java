package com.zhyen.imitation.widget.my_thump_up;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.zhyen.base.utils.UIUtils;
import com.zhyen.imitation.R;
import com.zhyen.imitation.widget.ThumbPoint;
import com.zhyen.imitation.widget.thumb_up.CountView;

public class MyThumbUpView extends LinearLayout implements View.OnClickListener {

    public static final float DEFAULT_DRAWABLE_PADDING = 4f;

    private boolean mNeedChangeChildView;
    private MyCountView mCountView;
    private MyThumbView mThumbView;
    private boolean mIsThumbUp;
    private float mDrawablePadding;
    private int mCount;
    private int mTextColor;
    private float mTextSize;
    private int mTopMargin;
    private long lastClickTime;

    public MyThumbUpView(Context context) {
        this(context, null);
    }

    public MyThumbUpView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyThumbUpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyThumbUpView);
        mDrawablePadding = typedArray.getDimension(R.styleable.MyThumbUpView_mtuv_drawable_padding, UIUtils.dip2px(context, DEFAULT_DRAWABLE_PADDING));
        mCount = typedArray.getInt(R.styleable.MyThumbUpView_mtuv_count, 0);
        mTextColor = typedArray.getColor(R.styleable.MyThumbUpView_mtuv_text_color, Color.parseColor(CountView.DEFAULT_TEXT_COLOR));
        mTextSize = typedArray.getDimension(R.styleable.MyThumbUpView_mtuv_drawable_padding, UIUtils.sp2px(context, CountView.DEFAULT_TEXT_SIZE));
        mIsThumbUp = typedArray.getBoolean(R.styleable.MyThumbUpView_mtuv_isThumbUp, false);
        typedArray.recycle();
        init();
    }

    private void init() {
        removeAllViews();
        setClipChildren(false);
        setOrientation(HORIZONTAL);
        addThumbUpView();
        addCountView();
        //把设置的padding分解到子view，否则对超出view范围的动画显示不全
        setPadding(0, 0, 0, 0, false);
        setOnClickListener(this);
    }

    public void setPadding(int left, int top, int right, int bottom, boolean needChange) {
        this.mNeedChangeChildView = needChange;
        setPadding(left, top, right, bottom);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        if (mNeedChangeChildView) {
            resetThumbParams();
            resetCountViewParams();
            mNeedChangeChildView = false;
        } else {
            super.setPadding(left, top, right, bottom);
        }
    }

    private void resetThumbParams() {
        LayoutParams params = (LayoutParams) mThumbView.getLayoutParams();
        if (mTopMargin < 0) {
            params.topMargin = mTopMargin;//设置这个距离是为了文字与拇指居中显示
        }
        params.leftMargin = getPaddingLeft();
        params.topMargin += getPaddingTop();
        params.bottomMargin = getPaddingBottom();
        mThumbView.setLayoutParams(params);
    }

    private void resetCountViewParams() {
        LayoutParams params = (LayoutParams) mCountView.getLayoutParams();
        if (mTopMargin > 0) {
            params.topMargin = mTopMargin;//设置这个距离是为了文字与拇指居中显示
        }
        params.leftMargin = (int) mDrawablePadding;
        params.topMargin += getPaddingTop();
        params.bottomMargin = getPaddingBottom();
        params.rightMargin = getPaddingRight();
        mCountView.setLayoutParams(params);
    }


    private void addCountView() {
        mCountView = new MyCountView(getContext());
        mCountView.setTextColor(mTextColor);
        mCountView.setTextSize(mTextSize);
        mCountView.setCount(mCount);
        addView(mCountView, getCountViewParams());
    }

    private LayoutParams getCountViewParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (mTopMargin > 0) {
            params.topMargin = mTopMargin;//设置这个距离是为了文字与拇指居中显示
        }
        params.leftMargin = (int) mDrawablePadding;
        params.topMargin += getPaddingTop();
        params.bottomMargin = getPaddingBottom();
        params.rightMargin = getPaddingRight();
        return params;
    }

    private void addThumbUpView() {
        mThumbView = new MyThumbView(getContext());
        mThumbView.setIsThumbUp(mIsThumbUp);
        ThumbPoint circlePoint = mThumbView.getCirclePoint();
        mTopMargin = (int) (circlePoint.y - mTextSize / 2);
        addView(mThumbView, getThumbViewParams());
    }

    private LayoutParams getThumbViewParams() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if (mTopMargin < 0) {
            params.topMargin = mTopMargin;//设置这个距离是为了文字与拇指居中显示
        }
        params.leftMargin = getPaddingLeft();
        params.topMargin += getPaddingTop();
        params.bottomMargin = getPaddingBottom();
        params.rightMargin = getPaddingRight();
        return params;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Paint testPaint = new Paint();
        testPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, getWidth() - 5, getHeight() - 5, testPaint);
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - lastClickTime < 310) {
            return;
        }
        lastClickTime = System.currentTimeMillis();
        mIsThumbUp = !mIsThumbUp;
        if (mIsThumbUp) {
            mCountView.calculateChangeNum(1);
        } else {
            mCountView.calculateChangeNum(-1);
        }
        mThumbView.startAnim();
    }

    public MyThumbUpView setCount(int mCount) {
        this.mCount = mCount;
        mCountView.setCount(mCount);
        return this;
    }

    public MyThumbUpView setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        mCountView.setTextColor(mCount);
        return this;
    }

    public MyThumbUpView setTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
        mCountView.setTextSize(mCount);
        return this;
    }

    public MyThumbUpView setThumbUp(boolean isThumbUp) {
        this.mIsThumbUp = isThumbUp;
        mThumbView.setIsThumbUp(mIsThumbUp);
        return this;
    }

    public void setThumbUpClickListener(MyThumbView.ThumbUpClickListener listener) {
        mThumbView.setThumbUpClickListener(listener);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle data = new Bundle();
        data.putParcelable("superData", super.onSaveInstanceState());
        data.putInt("count", mCount);
        data.putFloat("textSize", mTextSize);
        data.putInt("textColor", mTextColor);
        data.putBoolean("isThumbUp", mIsThumbUp);
        data.putFloat("drawablePadding", mDrawablePadding);
        return data;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle data = (Bundle) state;
        Parcelable superData = data.getParcelable("superData");
        super.onRestoreInstanceState(superData);
        mCount = data.getInt("count");
        mTextSize = data.getFloat("textSize", UIUtils.sp2px(getContext(), CountView.DEFAULT_TEXT_SIZE));
        mTextColor = data.getInt("textColor", Color.parseColor(CountView.DEFAULT_TEXT_COLOR));
        mIsThumbUp = data.getBoolean("isThumbUp", false);
        mDrawablePadding = data.getFloat("drawablePadding", UIUtils.sp2px(getContext(), DEFAULT_DRAWABLE_PADDING));
        init();
    }
}
