package com.zhyen.imitation.widget.my_thump_up;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

import com.zhyen.base.utils.UIUtils;
import com.zhyen.imitation.R;
import com.zhyen.imitation.widget.ThumbPoint;

public class MyThumbView extends View {

    private static final String TAG = "New MyThumbView";

    //圆圈颜色
    private static final int START_COLOR = Color.parseColor("#00e24d3d");
    private static final int END_COLOR = Color.parseColor("#88e24d3d");
    //缩放动画的时间
    private static final int SCALE_DURING = 150;
    //圆圈扩散动画的时间
    private static final int RADIUS_DURING = 100;

    private static final float SCALE_MIN = 0.9f;
    private static final float SCALE_MAX = 1f;

    private float dp_2;
    private float dp_8;

    private Paint mPaint;
    private Bitmap mThumbNormal;
    private Bitmap mThumbUp;
    private Bitmap mShining;
    private int mThumbWidth;
    private int mThumbHeight;
    private int mShiningWidth;
    private int mShiningHeight;
    private ThumbPoint mThumbPoint;
    private ThumbPoint mShiningPoint;

    //是否点赞
    private boolean mIsThumbUp;
    private ThumbPoint mCirclePoint;
    private float mRadiusMax;
    private float mRadiusMin;
    private Path mClipPath;
    private float mRadius;
    private Paint mCirclePaint;
    private ThumbUpClickListener mThumbUpClickListener;
    private AnimatorSet mThumbUpAnim;
    //被点击的次数，未点击时，未点赞是0，点赞是1，所以点完之后的次数是偶数则就是未点赞，奇数就是点赞
    private long mClickCount;
    private double mLastStartTime;
    private long mEndCount;

    public MyThumbView(Context context) {
        this(context, null);
    }

    public MyThumbView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyThumbView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dp_2 = UIUtils.dip2px(getContext(), 2);
        dp_8 = UIUtils.dip2px(getContext(), 8);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(dp_2);

        mThumbNormal = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_unselected);
        mThumbUp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);
        mShining = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected_shining);

        mThumbWidth = mThumbNormal.getWidth();
        mThumbHeight = mThumbNormal.getHeight();
        Log.d(TAG, "init: mThumbWidth = " + mThumbWidth + "  mThumbHeight = " + mThumbHeight);

        mShiningWidth = mShining.getWidth();
        mShiningHeight = mShining.getHeight();
        Log.d(TAG, "init: mShiningWidth = " + mShiningWidth + "  mShiningHeight = " + mShiningHeight);

        mThumbPoint = new ThumbPoint();
        mShiningPoint = new ThumbPoint();
        mCirclePoint = new ThumbPoint();

        mThumbPoint.x = getPaddingLeft();
        mThumbPoint.y = getPaddingTop() + dp_8;

        mShiningPoint.x = getPaddingLeft() + dp_2;
        mShiningPoint.y = getPaddingTop();

        mCirclePoint.x = mThumbPoint.x + mThumbWidth / 2;
        mCirclePoint.y = mThumbPoint.y + mThumbHeight / 2;

        mRadiusMax = Math.max(mCirclePoint.x - getPaddingLeft(), mCirclePoint.y - getPaddingTop());
        mRadiusMin = dp_8;

        mClipPath = new Path();
        mClipPath.addCircle(mCirclePoint.x, mCirclePoint.y, mRadiusMax, Path.Direction.CW);
    }

    public void setThumbUpClickListener(ThumbUpClickListener thumbUpClickListener) {
        this.mThumbUpClickListener = thumbUpClickListener;
    }

    public ThumbPoint getCirclePoint() {
        return mCirclePoint;
    }

    public void setIsThumbUp(boolean isThumbUp) {
        this.mIsThumbUp = isThumbUp;
        mClickCount = mIsThumbUp ? 1 : 0;
        mEndCount = mClickCount;
        postInvalidate();
    }

    public boolean isThumbUp() {
        return mIsThumbUp;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                UIUtils.getDefaultSize(widthMeasureSpec, getContentWidth()),
                UIUtils.getDefaultSize(heightMeasureSpec, getContentHeight())
        );
    }

    private int getContentHeight() {
        float minHeight = Math.min(mShiningPoint.y, mThumbPoint.y);
        float maxHeight = Math.max(mShiningPoint.y + mShiningHeight, mThumbPoint.y + mThumbHeight);
        return (int) (maxHeight - minHeight);
    }

    private int getContentWidth() {
        float minWidth = Math.min(mShiningPoint.x, mThumbPoint.x);
        float maxWidth = Math.max(mShiningPoint.x + mShiningWidth, mThumbPoint.x + mThumbWidth);
        return (int) (maxWidth - minWidth);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawARGB(154, 233, 45, 145);
        if (mIsThumbUp) {
            if (mClipPath != null) {
                canvas.save();
                canvas.clipPath(mClipPath);
                canvas.drawBitmap(mShining, mShiningPoint.x, mShiningPoint.y, mPaint);
                canvas.restore();
                canvas.drawCircle(mCirclePoint.x, mCirclePoint.y, mRadius, mCirclePaint);
            }
            canvas.drawBitmap(mThumbUp, mThumbPoint.x, mThumbPoint.y, mPaint);
        } else {
            canvas.drawBitmap(mThumbNormal, mThumbPoint.x, mThumbPoint.y, mPaint);
        }

        Paint testPaint = new Paint();
        testPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, getWidth() - 4, getHeight() - 4, testPaint);
    }

    private void setNotThumbUpScale(float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        //如果不重新获取bitmap 图片的缩放是在 上一次bitmap基础上进行缩放 会越来越小哦
        mThumbNormal = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_unselected);
        mThumbNormal = Bitmap.createBitmap(mThumbNormal, 0, 0, mThumbNormal.getWidth(), mThumbNormal.getHeight(), matrix, true);
        postInvalidate();
    }

    private void setThumbUpScale(float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        //如果不重新获取bitmap 图片的缩放是在 上一次bitmap基础上进行缩放 会越来越小哦
        mThumbUp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);
        mThumbUp = Bitmap.createBitmap(mThumbUp, 0, 0, mThumbUp.getWidth(), mThumbUp.getHeight(), matrix, true);
        postInvalidate();
    }

    private void setCircleScale(float radius) {
        Log.d(TAG, "setCircleScale: " + radius);
        mRadius = radius;
        mClipPath = new Path();
        mClipPath.addCircle(mCirclePoint.x, mCirclePoint.y, mRadius, Path.Direction.CW);
        float fraction = (mRadiusMax - radius) / (mRadiusMax - mRadiusMin);
        mCirclePaint.setColor((Integer) UIUtils.evaluate(fraction, START_COLOR, END_COLOR));
        postInvalidate();
    }

    public void startAnim() {
        mClickCount++;
        boolean isFastAnim = false;
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - mLastStartTime < 300) {
            isFastAnim = true;
        }
        mLastStartTime = currentTimeMillis;

        if (mIsThumbUp) {
            if (isFastAnim) {
                startFastAnim();
                return;
            }
            startThumbDownAnim();
            mClickCount = 0;
        } else {
            if (mThumbUpAnim != null) {
                mClickCount = 0;
            } else {
                startThumbUpAnim();
                mClickCount = 1;
            }
        }
        mEndCount = mClickCount;
    }

    private void startFastAnim() {
        ObjectAnimator thumbUpScale = ObjectAnimator.ofFloat(this, "thumbUpScale", SCALE_MIN, SCALE_MAX);
        thumbUpScale.setDuration(SCALE_DURING);
        thumbUpScale.setInterpolator(new OvershootInterpolator());

        ObjectAnimator circleScale = ObjectAnimator.ofFloat(this, "circleScale", mRadiusMin, mRadiusMax);
        circleScale.setDuration(RADIUS_DURING);

        AnimatorSet set = new AnimatorSet();
        set.play(thumbUpScale).with(circleScale);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mEndCount++;
                if (mClickCount != mEndCount) {
                    return;
                }
                if (mClickCount % 2 == 0) {
                    startThumbDownAnim();
                } else {
                    if (mThumbUpClickListener != null) {
                        mThumbUpClickListener.thumbUpFinish();
                    }
                }
            }
        });
        set.start();
    }

    private void startThumbUpAnim() {
        ObjectAnimator notThumbUpScale = ObjectAnimator.ofFloat(this, "notThumbUpScale", SCALE_MAX, SCALE_MIN);
        notThumbUpScale.setDuration(SCALE_DURING);
        notThumbUpScale.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mIsThumbUp = true;
            }
        });

        ObjectAnimator thumbUpScale = ObjectAnimator.ofFloat(this, "thumbUpScale", SCALE_MIN, SCALE_MAX);
        thumbUpScale.setInterpolator(new OvershootInterpolator());
        thumbUpScale.setDuration(SCALE_DURING);

        ObjectAnimator circleScale = ObjectAnimator.ofFloat(this, "circleScale", mRadiusMin, mRadiusMax);
        circleScale.setDuration(RADIUS_DURING);

        mThumbUpAnim = new AnimatorSet();
        mThumbUpAnim.play(thumbUpScale).with(circleScale);
        mThumbUpAnim.play(thumbUpScale).after(notThumbUpScale);
        mThumbUpAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mThumbUpAnim = null;
                if (mThumbUpClickListener != null) {
                    mThumbUpClickListener.thumbUpFinish();
                }
            }
        });
        mThumbUpAnim.start();
    }

    private void startThumbDownAnim() {
        ObjectAnimator thumbUpScale = ObjectAnimator.ofFloat(this, "thumbUpScale", SCALE_MIN, SCALE_MAX);
        thumbUpScale.setDuration(SCALE_DURING);
        thumbUpScale.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mIsThumbUp = false;
                setNotThumbUpScale(SCALE_MAX);
                if (mThumbUpClickListener != null) {
                    mThumbUpClickListener.thumbDownFinish();
                }

            }
        });
        thumbUpScale.start();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle data = new Bundle();
        data.putParcelable("superData", super.onSaveInstanceState());
        data.putBoolean("isThumbUp", mIsThumbUp);
        return data;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle data = (Bundle) state;
        Parcelable superData = data.getParcelable("superData");
        super.onRestoreInstanceState(superData);

        mIsThumbUp = data.getBoolean("isThumbUp", false);

        init();
    }

    public interface ThumbUpClickListener {
        //点赞回调
        void thumbUpFinish();

        //取消回调
        void thumbDownFinish();
    }

}
