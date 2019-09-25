package com.zhyen.android.picture_selected.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.zhyen.android.R;
import com.zhyen.android.utils.ScreenUtils;

public class CheckView extends View {

    private static final String TAG = "CheckView";

    public static final int UNCHECKED = Integer.MIN_VALUE;
    //单选框控件大小
    private static final int SIZE = 48;//dp
    //圆的半径
    private static final float STROKE_RADIUS = 11.5f; // dp
    //白色边线宽度
    private static final float STROKE_WIDTH = 3.0f;
    //阴影宽度
    private static final float SHADOW_WIDTH = 6.0f;
    //选中背景半径
    private static final float BG_RADIUS = 11.0f; // dp
    //
    private static final int CONTENT_SIZE = 16; // dp


    private Context mContext;
    private float mDensity;
    private Paint mStrokePaint;
    private Paint mShadowPaint;
    private Drawable mCheckDrawable;
    //显示数字还是对号开关
    private boolean mCountable;
    //是否选中
    private boolean mChecked;
    //显示数字时 数字
    private int mCheckedNum;
    private Paint mBackgroundPaint;
    private TextPaint mTextPaint;
    private Rect mCheckRect;
    private boolean mEnabled = true;

    public CheckView(Context context) {
        super(context);
        init(context);
    }

    public CheckView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CheckView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mDensity = ScreenUtils.getDensity(context);
        mStrokePaint = new Paint();
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        mStrokePaint.setStrokeWidth(STROKE_WIDTH * mDensity);
        TypedArray ta = mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.item_checkCircle_borderColor});
        int defaultColor = ResourcesCompat.getColor(getResources(), R.color.main, mContext.getTheme());
        int color = ta.getColor(0, defaultColor);
        ta.recycle();
        mStrokePaint.setColor(color);
        mCheckDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_check_white_18dp, mContext.getTheme());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // fixed size 48dp x 48dp
        int spec = MeasureSpec.makeMeasureSpec((int) (SIZE * mDensity), MeasureSpec.EXACTLY);
        super.onMeasure(spec, spec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制内 外阴影
        initShadowPaint();
        canvas.drawCircle((float) SIZE * mDensity / 2, (float) SIZE * mDensity / 2,
                (STROKE_RADIUS + STROKE_WIDTH / 2 + SHADOW_WIDTH) * mDensity, mShadowPaint);
        //绘制白色边线
        canvas.drawCircle(SIZE * mDensity / 2, SIZE * mDensity / 2, STROKE_RADIUS * mDensity, mStrokePaint);

        //绘制内容
        if (mCountable) {
            if (mCheckedNum != UNCHECKED) {
                initBackgroundPaint();
                canvas.drawCircle(SIZE * mDensity / 2, SIZE * mDensity / 2, BG_RADIUS * mDensity, mBackgroundPaint);
                //绘制数字
                initTextPaint();
                String text = String.valueOf(mCheckedNum);
                int baseX = (int) ((getWidth() - mTextPaint.measureText(text)) / 2);
                int baseY = (int) (getHeight() - mTextPaint.descent() - mTextPaint.ascent()) / 2;
                canvas.drawText(text, baseX, baseY, mTextPaint);
            }
        } else {
            if (mChecked) {
                initBackgroundPaint();
                canvas.drawCircle(SIZE * mDensity / 2, SIZE * mDensity / 2, BG_RADIUS * mDensity, mBackgroundPaint);
                mCheckDrawable.setBounds(getCheckRect());
                mCheckDrawable.draw(canvas);
            }
        }
        // enable hint
        setAlpha(mEnabled ? 1.0f : 0.5f);
    }

    private Rect getCheckRect() {
        if (mCheckRect == null) {
            int rectPadding = (int) (SIZE * mDensity / 2 - CONTENT_SIZE * mDensity / 2);
            mCheckRect = new Rect(rectPadding, rectPadding,
                    (int) (SIZE * mDensity - rectPadding), (int) (SIZE * mDensity - rectPadding));
        }

        return mCheckRect;
    }

    private void initTextPaint() {
        if (mTextPaint == null) {
            mTextPaint = new TextPaint();
            mTextPaint.setAntiAlias(true);
            mTextPaint.setColor(Color.WHITE);
            mTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            mTextPaint.setTextSize(12.0f * mDensity);
        }
    }

    private void initBackgroundPaint() {
        if (mBackgroundPaint == null) {
            mBackgroundPaint = new Paint();
            mBackgroundPaint.setAntiAlias(true);
            mBackgroundPaint.setStyle(Paint.Style.FILL);
            TypedArray ta = getContext().getTheme()
                    .obtainStyledAttributes(new int[]{R.attr.item_checkCircle_backgroundColor});
            int defaultColor = ResourcesCompat.getColor(
                    getResources(), R.color.sub_blue,
                    getContext().getTheme());
            int color = ta.getColor(0, defaultColor);
            ta.recycle();
            mBackgroundPaint.setColor(color);
        }
    }

    private void initShadowPaint() {
        if (mShadowPaint == null) {
            mShadowPaint = new Paint();
            mShadowPaint.setAntiAlias(true);
            float outerRadius = STROKE_RADIUS + STROKE_WIDTH / 2;
            float innerRadius = outerRadius - STROKE_WIDTH;
            float gradientRadius = outerRadius + SHADOW_WIDTH;
            float stop0 = (innerRadius - SHADOW_WIDTH) / gradientRadius;
            float stop1 = innerRadius / gradientRadius;
            float stop2 = outerRadius / gradientRadius;
            float stop3 = 1.0f;
            mShadowPaint.setShader(new RadialGradient(
                    (float) SIZE * mDensity / 2,
                    (float) SIZE * mDensity / 2,
                    gradientRadius * mDensity,
                    new int[]{Color.parseColor("#00000000"), Color.parseColor("#0D000000"),
                            Color.parseColor("#0D000000"), Color.parseColor("#00000000")},
                    new float[]{stop0, stop1, stop2, stop3},
                    Shader.TileMode.CLAMP)
            );
        }

    }

    public void setEnabled(boolean enabled) {
        if (mEnabled != enabled) {
            mEnabled = enabled;
            invalidate();
        }
    }

    public void setCountable(boolean countable) {
        mCountable = countable;
    }

    public void setCheckedNum(int checkedNum) {
        if (!mCountable) {
            throw new IllegalStateException("CheckView is not countable, call setChecked() instead.");
        }
        if (checkedNum != UNCHECKED && checkedNum <= 0) {
            throw new IllegalArgumentException("checked num can't be negative.");
        }
        mCheckedNum = checkedNum;
        invalidate();
    }

    public void setChecked(boolean checked) {
        if (mCountable) {
            throw new IllegalStateException("CheckView is countable, call setCheckedNum() instead.");
        }
        mChecked = checked;
        invalidate();
    }
}
