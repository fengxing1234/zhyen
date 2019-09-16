package com.zhyen.android.widgets;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.zhyen.android.R;
import com.zhyen.android.utils.ScreenUtils;

import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static android.widget.ImageView.ScaleType.CENTER_INSIDE;

public class CircularImageView extends AppCompatImageView {

    private static final String TAG = CircularImageView.class.getSimpleName();

    private static final float DEFAULT_BORDER_WIDTH = 4f;
    private static final float DEFAULT_SHADOW_RADIUS = 8.0f;

    private Context mContext;
    private Paint mPaint;
    private Paint mPaintBorder;
    private Paint mPaintBackground;


    private int circleCenter = 0;
    private int heightCircle = 0;

    private int circleColor = Color.WHITE;
    private float borderWidth = 0f;
    private int borderColor = Color.BLACK;
    private float shadowRadius = 0f;
    private int shadowColor = Color.BLACK;
    private ShadowGravity shadowGravity = ShadowGravity.BOTTOM;
    private boolean shadowEnable = false;

    private ColorFilter civColorFilter;

    private Bitmap civImage;
    private Drawable civDrawable;

    public CircularImageView(Context context) {
        this(context, null);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mPaintBorder = new Paint();
        mPaintBorder.setAntiAlias(true);

        mPaintBackground = new Paint();
        mPaintBorder.setAntiAlias(true);

        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.CircularImageView, defStyleAttr, 0);
        circleColor = ta.getColor(R.styleable.CircularImageView_civ_circle_color, Color.WHITE);
        if (ta.getBoolean(R.styleable.CircularImageView_civ_border, true)) {
            double defaultBorderSize = DEFAULT_BORDER_WIDTH * ScreenUtils.getDensity(mContext);
            borderWidth = ta.getDimension(R.styleable.CircularImageView_civ_border_width, (float) defaultBorderSize);
            borderColor = ta.getColor(R.styleable.CircularImageView_civ_border_color, Color.WHITE);
        }

        if (ta.getBoolean(R.styleable.CircularImageView_civ_shadow, shadowEnable)) {
            int index = ta.getInteger(R.styleable.CircularImageView_civ_shadow_gravity, shadowGravity.index);
            shadowGravity = ShadowGravity.fromGravityIndex(index);
            shadowRadius = ta.getFloat(R.styleable.CircularImageView_civ_shadow_radius, DEFAULT_SHADOW_RADIUS);
            shadowColor = ta.getColor(R.styleable.CircularImageView_civ_shadow_color, shadowColor);
        }

        ta.recycle();
    }

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        mPaintBackground.setColor(circleColor);
        invalidate();
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        update();
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        update();
    }

    public double getShadowRadius() {
        return shadowRadius;
    }

    public void setShadowRadius(float shadowRadius) {
        this.shadowRadius = shadowRadius;
        shadowEnable = shadowRadius > 0f;
        invalidate();
    }

    public int getShadowColor() {
        return shadowColor;
    }

    public void setShadowColor(int shadowColor) {
        this.shadowColor = shadowColor;
        invalidate();
    }

    public ShadowGravity getShadowGravity() {
        return shadowGravity;
    }

    public void setShadowGravity(ShadowGravity shadowGravity) {
        this.shadowGravity = shadowGravity;
        invalidate();
    }

    public boolean isShadowEnable() {
        return shadowEnable;
    }

    public void setShadowEnable(boolean shadowEnable) {
        this.shadowEnable = shadowEnable;
        if (shadowEnable && shadowRadius == 0f)
            shadowRadius = DEFAULT_SHADOW_RADIUS;
        update();
    }

    public ColorFilter getCivColorFilter() {
        return civColorFilter;
    }

    public void setCivColorFilter(ColorFilter civColorFilter) {
        if (this.civColorFilter != civColorFilter) {
            this.civColorFilter = civColorFilter;
            if (civColorFilter != null) {
                civDrawable = null; // To force re-update shader
                invalidate();
            }
        }
    }

    public Bitmap getCivImage() {
        return civImage;
    }

    public void setCivImage(Bitmap civImage) {
        this.civImage = civImage;
    }

    public Drawable getCivDrawable() {
        return civDrawable;
    }

    public void setCivDrawable(Drawable civDrawable) {
        this.civDrawable = civDrawable;
    }

    private void update() {
        if (civImage != null) {
            updateShader();
        }
        int usableWidth = getWidth() - (getPaddingLeft() + getPaddingRight());
        int usableHeight = getHeight() - (getPaddingTop() + getPaddingBottom());
        heightCircle = Math.min(usableWidth, usableHeight);
        circleCenter = ((int) (heightCircle - borderWidth * 2)) / 2;
        mPaintBorder.setColor(borderWidth == 0f ? circleColor : borderColor);
        manageElevation();
        invalidate();
    }

    private void manageElevation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (!shadowEnable) {
                setOutlineProvider(new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        outline.setOval(0, 0, heightCircle, heightCircle);
                    }
                });
            }
        }
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        Log.d(TAG, "setColorFilter: " + cf);
        civColorFilter = cf;
    }

    @Override
    public ScaleType getScaleType() {
        ScaleType scaleType = super.getScaleType();
        if (scaleType == null || scaleType != CENTER_INSIDE) {
            scaleType = CENTER_CROP;
        }
        return scaleType;
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType != CENTER_CROP && scaleType != CENTER_INSIDE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported. " + "Just ScaleType.CENTER_CROP & ScaleType.CENTER_INSIDE are available for this library.", scaleType));
        } else {
            super.setScaleType(scaleType);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        update();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measure(widthMeasureSpec);
        int height = measure(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measure(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            return specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            return specSize;
        } else {
            return heightCircle;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Load the bitmap
        loadBitmap();

        if (civImage == null) {
            return;
        }

        float circleCenterWithBorder = circleCenter + borderWidth;
        float margeWithShadowRadius = (shadowEnable) ? shadowRadius * 2 : 0f;

        // Draw Shadow
        if (shadowEnable) {
            drawShadow();
        }
        // Draw Border
        canvas.drawCircle(circleCenterWithBorder, circleCenterWithBorder, circleCenterWithBorder - margeWithShadowRadius, mPaintBorder);
        // Draw Circle background
        canvas.drawCircle(circleCenterWithBorder, circleCenterWithBorder, circleCenter - margeWithShadowRadius, mPaintBackground);
        // Draw CircularImageView
        canvas.drawCircle(circleCenterWithBorder, circleCenterWithBorder, circleCenter - margeWithShadowRadius, mPaint);
    }

    private void drawShadow() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, mPaintBorder);
        }

        float dx = 0.0f;
        float dy = 0.0f;

        if (shadowGravity == ShadowGravity.CENTER) {
            /*dx, dy = 0.0f*/
        } else if (shadowGravity == ShadowGravity.TOP) {
            dy = -shadowRadius / 2;
        } else if (shadowGravity == ShadowGravity.BOTTOM) {
            dy = shadowRadius / 2;
        } else if (shadowGravity == ShadowGravity.START) {
            dx = -shadowRadius / 2;
        } else if (shadowGravity == ShadowGravity.END) {
            dx = shadowRadius / 2;
        }
        mPaintBorder.setShadowLayer(shadowRadius, dx, dy, shadowColor);
    }

    private void loadBitmap() {
        if (civDrawable == getDrawable()) {
            return;
        }
        civDrawable = getDrawable();

        civImage = drawableToBitmap(civDrawable);
        updateShader();
    }

    private void updateShader() {
        if (civImage == null) {
            return;
        }
        BitmapShader shader = new BitmapShader(civImage, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale;
        float dx;
        float dy;
        ScaleType scaleType = getScaleType();
        if (scaleType == CENTER_CROP) {
            if (civImage.getWidth() + getHeight() > getWidth() * civImage.getHeight()) {
                scale = getHeight() / civImage.getHeight();
                dx = (getWidth() - civImage.getWidth() * scale) * 0.5f;
                dy = 0f;

            } else {
                scale = getWidth() / civImage.getWidth();
                dx = 0f;
                dy = (getHeight() - civImage.getHeight() * scale) * 0.5f;
            }
        } else if (scaleType == CENTER_INSIDE) {
            if (civImage.getWidth() * getHeight() < getWidth() * civImage.getHeight()) {
                scale = getHeight() / civImage.getHeight();
                dx = (getWidth() - civImage.getWidth() * scale) * 0.5f;
                dy = 0f;
            } else {
                scale = getWidth() / civImage.getWidth();
                dx = 0f;
                dy = (getHeight() - civImage.getHeight() * scale) * 0.5f;
            }
        } else {
            scale = 0f;
            dx = 0f;
            dy = 0f;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        matrix.postTranslate(dx, dy);
        shader.setLocalMatrix(matrix);
        mPaint.setShader(shader);
        mPaint.setColorFilter(civColorFilter);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        try {
            if (drawable == null) {
                return null;
            } else if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            } else {
                Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    enum ShadowGravity {
        CENTER(1),
        TOP(2),
        BOTTOM(3),
        START(4),
        END(5);

        int index;

        ShadowGravity(int i) {
            this.index = i;
        }

        public static ShadowGravity fromGravityIndex(int index) {
            for (ShadowGravity gravity : ShadowGravity.values()) {
                if (gravity.index == index) {
                    return gravity;
                }
            }
            return null;
        }
    }

}
