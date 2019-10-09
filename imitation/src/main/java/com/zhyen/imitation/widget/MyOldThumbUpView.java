package com.zhyen.imitation.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

import com.zhyen.base.utils.UIUtils;
import com.zhyen.imitation.R;

public class MyOldThumbUpView extends View implements View.OnClickListener {


    private static final String TAG = MyOldThumbUpView.class.getSimpleName();
    //图标大小 单位dp
    private static final float BITMAP_WIDTH = 20f;
    private static final float BITMAP_HEIGHT = 20f;
    private static final float SHINING_WIDTH = 16f;
    private static final float SHINING_HEIGHT = 16f;

    //大拇指和文字的间距 单位dp
    private static final float BITMAP_TEXT_SPACE = 4;
    //默认文字大小
    private static final float TEXT_DEFAULT_SIZE = 16;
    //默认文字颜色
    private static final int TEXT_DEFAULT_COLOR = Color.parseColor("#cccccc");
    private static final int TEXT_DEFAULT_END_COLOR = Color.parseColor("#00cccccc");
    //圆圈颜色
    private static final int START_COLOR = Color.parseColor("#00e24d3d");
    private static final int END_COLOR = Color.parseColor("#88e24d3d");

    private static final float SCALE_MIN = 0.9f;
    private static final float SCALE_MAX = 1f;

    //缩放动画的时间
    private static final int SCALE_DURING = 150;
    //圆圈扩散动画的时间
    private static final int RADIUS_DURING = 100;
    private float OFFSET_MIN;
    private float OFFSET_MAX;

    private Bitmap mUnselectedBitmap;
    private Bitmap mShiningBitmap;
    private Bitmap mSelectedBitmap;
    private Paint mPaint;

    //当前按钮状态 选中还是未选中
    private boolean isSelect;

    //点赞数量 也是需要绘制的文本
    private int mNumber = 99;
    //绘制文本字体大小
    private float textSize = TEXT_DEFAULT_SIZE;
    private Paint mTextPaint;
    //绘制的起始点 为了保证居中绘制，这是绘制的起点坐标，减去这个值则为以原点为坐标开始绘制的
    private float startX;
    private float startY;

    private int dp_2;
    private int dp_8;
    //文字的起始位置
    private float textStartX;

    //圆的中心点
    private float mCircleX;
    private float mCircleY;

    //圆的半径
    private int RADIUS_MIN;
    private int RADIUS_MAX;
    private Path mClipPath;

    private float mRadius;
    private Paint mCirclePaint;

    private float mScale;

    private boolean toBigger;
    private float mOldOffsetY;
    private float mNewOffsetY;

    private boolean isCanceled;

    private String[] nums;//num[0]是不变的部分，nums[1]原来的部分，nums[2]变化后的部分

    //点击的回调
    private ThumbUpClickListener thumbUpClickListener;
    private AnimatorSet currentAnim;
    private long lastClickTime;

    public MyOldThumbUpView(Context context) {
        super(context);
        init(null);
    }

    public MyOldThumbUpView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyOldThumbUpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MyOldThumbUpView);
        mNumber = ta.getInteger(R.styleable.MyOldThumbUpView_count, 0);
        ta.recycle();

        OFFSET_MIN = 0;
        OFFSET_MAX = 1.5f * UIUtils.dip2px(getContext(), textSize);

        dp_2 = UIUtils.dip2px(getContext(), 2);
        dp_8 = UIUtils.dip2px(getContext(), 8);

        RADIUS_MIN = dp_8;//圆扩散的最小半径
        RADIUS_MAX = UIUtils.dip2px(getContext(), 16);//为了包住拇指和点，以中点为中心的最小半径，即扩散的最大半径

        mCircleX = UIUtils.dip2px(getContext(), BITMAP_HEIGHT) / 2;
        mCircleY = UIUtils.dip2px(getContext(), BITMAP_HEIGHT + SHINING_HEIGHT) / 2;

        textStartX = UIUtils.dip2px(getContext(), BITMAP_HEIGHT) + UIUtils.dip2px(getContext(), BITMAP_TEXT_SPACE);

        mUnselectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_unselected);
        mShiningBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected_shining);
        mSelectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);

        mClipPath = new Path();
        mClipPath.addCircle(startX + mCircleX, startY + mCircleY, RADIUS_MAX, Path.Direction.CW);
        initPaint();
        setOnClickListener(this);

        nums = new String[]{String.valueOf(mNumber), "", ""};
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(UIUtils.sp2px(getContext(), textSize));
        mTextPaint.setColor(TEXT_DEFAULT_COLOR);


        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(dp_2);
        mCirclePaint.setColor(START_COLOR);
    }

    public MyOldThumbUpView setCount(int count) {
        this.mNumber = count;
        calculateChangeNum(0);
        requestLayout();
        return this;
    }

    public void setThumbUpClickListener(ThumbUpClickListener thumbUpClickListener) {
        this.thumbUpClickListener = thumbUpClickListener;
    }


    public float getBitmapSelectScale() {
        return mScale;
    }

    public void setBitmapSelectScale(float scale) {
        this.mScale = scale;
        Matrix matrix = new Matrix();
        matrix.postScale(mScale, mScale);
        mSelectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);
        mSelectedBitmap = Bitmap.createBitmap(mSelectedBitmap, 0, 0, mSelectedBitmap.getWidth(), mSelectedBitmap.getHeight(), matrix, true);
        postInvalidate();
    }

    public float getBitmapUnSelectScale() {
        return mScale;
    }

    public void setBitmapUnSelectScale(float scale) {
        this.mScale = scale;
        Matrix matrix = new Matrix();
        matrix.postScale(mScale, mScale);
        mUnselectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_unselected);
        mUnselectedBitmap = Bitmap.createBitmap(mUnselectedBitmap, 0, 0, mUnselectedBitmap.getWidth(), mUnselectedBitmap.getHeight(), matrix, true);
        postInvalidate();
    }

    public float getCircleScale() {
        return mRadius;
    }

    public void setCircleScale(float radius) {
        mRadius = radius;
        mClipPath = new Path();
        mClipPath.addCircle(startX + mCircleX, startY + mCircleY, mRadius, Path.Direction.CW);

        float fraction = (RADIUS_MAX - radius) / (RADIUS_MAX - RADIUS_MIN);
        mCirclePaint.setColor((int) evaluate(fraction, START_COLOR, END_COLOR));
        postInvalidate();
    }

    public float getTextOffsetY() {
        return OFFSET_MIN;
    }

    public void setTextOffsetY(float offsetY) {
        //数字变大是从[0,1]，变小是[0,-1]
        this.mOldOffsetY = offsetY;
        if (toBigger) {//从下到上[-1,0]
            this.mNewOffsetY = offsetY - OFFSET_MAX;
        } else {//从上到下[1,0]
            this.mNewOffsetY = OFFSET_MAX + offsetY;
        }
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");
        setMeasuredDimension(getMeasuredWidth(widthMeasureSpec), getMeasuredHeight(heightMeasureSpec));
    }

    private int getMeasuredWidth(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.UNSPECIFIED) {
            return size;
        } else if (mode == MeasureSpec.AT_MOST) {
            return getContentWidth();
        } else if (mode == MeasureSpec.EXACTLY) {
            return Math.max(size, getContentWidth());
        }
        return result;
    }

    private int getContentWidth() {
        Log.d(TAG, "getContentWidth: mNumberW = " + mTextPaint.measureText(String.valueOf(mNumber)));
        int contentWidth = UIUtils.dip2px(getContext(), BITMAP_WIDTH)
                + UIUtils.dip2px(getContext(), BITMAP_TEXT_SPACE)
                + ((int) mTextPaint.measureText(String.valueOf(mNumber)));
        contentWidth += getPaddingLeft() + getPaddingRight();
        Log.d(TAG, "getContentWidth: " + contentWidth);
        return contentWidth;
    }


    private int getMeasuredHeight(int spec) {
        int result = 0;
        int mode = MeasureSpec.getMode(spec);
        int size = MeasureSpec.getSize(spec);
        if (mode == MeasureSpec.UNSPECIFIED) {
            result = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            result = getContentHeight();
        } else if (mode == MeasureSpec.EXACTLY) {
            result = Math.max(getContentHeight(), result);
        }
        return result;
    }

    private int getContentHeight() {
        Log.d(TAG, "getContentHeight1: " + textSize);
        Log.d(TAG, "getContentHeight2: " + UIUtils.sp2px(getContext(), textSize));
        int result = 0;
        result = UIUtils.dip2px(getContext(), BITMAP_HEIGHT + SHINING_HEIGHT);
        result = Math.max(result, UIUtils.sp2px(getContext(), textSize));
        result += getPaddingTop() + getPaddingBottom();
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: w = " + w + "   h = " + h);
        Log.d(TAG, "onSizeChanged: mNumber = " + mTextPaint.measureText(String.valueOf(mNumber)));

        startX = (w -
                (UIUtils.dip2px(getContext(), BITMAP_WIDTH)
                        + UIUtils.dip2px(getContext(), BITMAP_TEXT_SPACE)
                        + mTextPaint.measureText(String.valueOf(mNumber)))
        ) / 2;

        startY = (h - (
                Math.max(UIUtils.dip2px(getContext(), BITMAP_HEIGHT + SHINING_HEIGHT), UIUtils.sp2px(getContext(), textSize))
        )
        ) / 2;

        mClipPath = new Path();
        mClipPath.addCircle(startX + mCircleX, startY + mCircleY, RADIUS_MAX, Path.Direction.CW);

        Log.d(TAG, "onSizeChanged: startX = " + startX + "  startY = " + startY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawARGB(255, 139, 197, 186);
        Log.d(TAG, "onDraw: ");
        if (isSelect) {
            if (mClipPath != null) {
                canvas.save();
                canvas.clipPath(mClipPath);
                canvas.drawBitmap(mShiningBitmap, startX + dp_2, startY, mPaint);
                canvas.restore();
                canvas.drawCircle(startX + mCircleX, startY + mCircleY, mRadius, mCirclePaint);
            } else {
                canvas.drawBitmap(mShiningBitmap, startX + dp_2, startY, mPaint);
            }
            canvas.drawBitmap(mSelectedBitmap, startX, startY + dp_8, mPaint);
        } else {
            canvas.drawBitmap(mUnselectedBitmap, startX, startY + dp_8, mPaint);
        }

        mTextPaint.setColor(TEXT_DEFAULT_COLOR);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        float y = (UIUtils.dip2px(getContext(), BITMAP_HEIGHT + SHINING_HEIGHT) - (fontMetrics.bottom + fontMetrics.top)) / 2;
        canvas.drawText(nums[0], startX + textStartX, startY + y, mTextPaint);

        String text = String.valueOf(mNumber);
        float textWidth = mTextPaint.measureText(text) / text.length();
        float fraction = (OFFSET_MAX - Math.abs(mOldOffsetY)) / (OFFSET_MAX - OFFSET_MIN);

        mTextPaint.setColor((Integer) evaluate(fraction, TEXT_DEFAULT_END_COLOR, TEXT_DEFAULT_COLOR));
        canvas.drawText(String.valueOf(nums[1]), startX + textStartX + textWidth * nums[0].length(), startY + y - mOldOffsetY, mTextPaint);

        mTextPaint.setColor((Integer) evaluate(fraction, TEXT_DEFAULT_COLOR, TEXT_DEFAULT_END_COLOR));
        canvas.drawText(String.valueOf(nums[2]), startX + textStartX + textWidth * nums[0].length(), startY + y - mNewOffsetY, mTextPaint);
    }


    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - lastClickTime < SCALE_DURING + RADIUS_DURING) {
            return;
        }
        lastClickTime = System.currentTimeMillis();
        cancelAnim();

        if (isSelect) {
            calculateChangeNum(-1);
            mNumber--;
            startUnSelectAnim();
        } else {
            calculateChangeNum(1);
            mNumber++;
            startSelectAnim();
        }
    }

    private void cancelAnim() {
        isCanceled = true;
        if (currentAnim != null) {
            currentAnim.cancel();
            currentAnim = null;
        }
    }

    private void startUnSelectAnim() {
        ObjectAnimator bitmapSelectScale = ObjectAnimator.ofFloat(this, "bitmapSelectScale", SCALE_MAX, SCALE_MIN);
        bitmapSelectScale.setDuration(SCALE_DURING);
        bitmapSelectScale.addListener(new ClickAnimatorListener() {
            @Override
            public void onAnimRealEnd(Animator animation) {
                isSelect = false;
            }
        });

        ObjectAnimator textOffsetY = ObjectAnimator.ofFloat(this, "textOffsetY", OFFSET_MIN, -OFFSET_MAX);


        ObjectAnimator bitmapUnSelectScale = ObjectAnimator.ofFloat(this, "bitmapUnSelectScale", SCALE_MIN, SCALE_MAX);
        bitmapUnSelectScale.setDuration(SCALE_DURING);

        AnimatorSet set = new AnimatorSet();
        set.play(bitmapSelectScale).with(textOffsetY);
        set.play(bitmapUnSelectScale).after(bitmapSelectScale);

        set.addListener(new ClickAnimatorListener() {
            @Override
            public void onAnimRealEnd(Animator animation) {
                Log.d("scaleOk", "thumbDown" + mNumber);
                if (thumbUpClickListener != null) {
                    thumbUpClickListener.thumbDownFinish();
                }
            }
        });

        set.start();
        currentAnim = set;
    }

    private void startSelectAnim() {

        ObjectAnimator scaleUnSelectAnim = ObjectAnimator.ofFloat(this, "bitmapUnSelectScale", SCALE_MAX, SCALE_MIN);
        scaleUnSelectAnim.setDuration(SCALE_DURING);
        scaleUnSelectAnim.addListener(new ClickAnimatorListener() {
            @Override
            public void onAnimRealEnd(Animator animation) {
                isSelect = true;
            }
        });


        ObjectAnimator scaleSelectAnim = ObjectAnimator.ofFloat(this, "bitmapSelectScale", SCALE_MIN, SCALE_MAX);
        scaleSelectAnim.setDuration(SCALE_DURING);
        scaleSelectAnim.setInterpolator(new OvershootInterpolator());

        ObjectAnimator circleScale = ObjectAnimator.ofFloat(this, "circleScale", RADIUS_MIN, RADIUS_MAX);

        ObjectAnimator textOffsetY = ObjectAnimator.ofFloat(this, "textOffsetY", OFFSET_MIN, OFFSET_MAX);


        AnimatorSet set = new AnimatorSet();
        set.play(scaleSelectAnim).with(circleScale);
        set.play(textOffsetY).with(scaleUnSelectAnim);
        set.play(scaleSelectAnim).after(scaleUnSelectAnim);
        set.addListener(new ClickAnimatorListener() {
            @Override
            public void onAnimRealEnd(Animator animation) {
                Log.d("scaleOk", "thumbUp" + mNumber);
                if (thumbUpClickListener != null) {
                    thumbUpClickListener.thumbUpFinish();
                }
            }

        });
        set.start();
        currentAnim = set;
    }

    /**
     * 计算不变，原来，和改变后各部分的数字
     * 这里是只针对加一和减一去计算的算法，因为直接设置的时候没有动画
     */
    private void calculateChangeNum(int change) {
        if (change == 0) {
            nums[0] = String.valueOf(mNumber);
            nums[1] = "";
            nums[2] = "";
            return;
        }
        toBigger = change > 0;
        String oldNum = String.valueOf(mNumber);
        String newNum = String.valueOf(mNumber + change);

        int oldNumLen = oldNum.length();
        int newNumLen = newNum.length();

        if (oldNumLen != newNumLen) {
            nums[0] = "";
            nums[1] = oldNum;
            nums[2] = newNum;
        } else {
            for (int i = 0; i < oldNumLen; i++) {
                char oldC1 = oldNum.charAt(i);
                char newC1 = newNum.charAt(i);
                if (oldC1 != newC1) {
                    if (i == 0) {
                        nums[0] = "";
                    } else {
                        nums[0] = newNum.substring(0, i);
                    }
                    nums[1] = oldNum.substring(i);
                    nums[2] = newNum.substring(i);
                    break;
                }
            }
        }
    }


    public Object evaluate(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        float startA = ((startInt >> 24) & 0xff) / 255.0f;
        float startR = ((startInt >> 16) & 0xff) / 255.0f;
        float startG = ((startInt >> 8) & 0xff) / 255.0f;
        float startB = (startInt & 0xff) / 255.0f;

        int endInt = (Integer) endValue;
        float endA = ((endInt >> 24) & 0xff) / 255.0f;
        float endR = ((endInt >> 16) & 0xff) / 255.0f;
        float endG = ((endInt >> 8) & 0xff) / 255.0f;
        float endB = (endInt & 0xff) / 255.0f;

        // convert from sRGB to linear
        startR = (float) Math.pow(startR, 2.2);
        startG = (float) Math.pow(startG, 2.2);
        startB = (float) Math.pow(startB, 2.2);

        endR = (float) Math.pow(endR, 2.2);
        endG = (float) Math.pow(endG, 2.2);
        endB = (float) Math.pow(endB, 2.2);

        // compute the interpolated color in linear space
        float a = startA + fraction * (endA - startA);
        float r = startR + fraction * (endR - startR);
        float g = startG + fraction * (endG - startG);
        float b = startB + fraction * (endB - startB);

        // convert back to sRGB in the [0..255] range
        a = a * 255.0f;
        r = (float) Math.pow(r, 1.0 / 2.2) * 255.0f;
        g = (float) Math.pow(g, 1.0 / 2.2) * 255.0f;
        b = (float) Math.pow(b, 1.0 / 2.2) * 255.0f;

        return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
    }

    public interface ThumbUpClickListener {
        //点赞回调
        void thumbUpFinish();

        //取消回调
        void thumbDownFinish();
    }

    private abstract class ClickAnimatorListener extends AnimatorListenerAdapter {
        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            isCanceled = false;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            if (!isCanceled) {
                onAnimRealEnd(animation);
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            super.onAnimationCancel(animation);
            isCanceled = true;
        }

        public abstract void onAnimRealEnd(Animator animation);
    }
}
