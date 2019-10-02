package com.zhyen.test.widget.test_paint.shader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class TestPaintRadialGradient extends View {

    private static final String TAG = TestPaintRadialGradient.class.getSimpleName();
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private Paint textPaint;

    public TestPaintRadialGradient(Context context) {
        super(context);
    }

    public TestPaintRadialGradient(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public TestPaintRadialGradient(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaint() {
        paint1 = new Paint();
        paint1.setAntiAlias(true);

        paint2 = new Paint();
        paint2.setAntiAlias(true);

        paint3 = new Paint();
        paint3.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(40);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: " + w + "====" + h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Log.d(TAG, "onMeasure: " + width + " === " + height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: " + getWidth() + "===" + getHeight());
        float centerW = canvas.getWidth() / 2;
        float centerH = canvas.getHeight() / 2;

        float halfCenterW = centerW / 2;
        float halfCenterH = centerH / 2;

        float r = halfCenterW;


        RadialGradient shader1 = new RadialGradient(halfCenterW, halfCenterH, halfCenterW, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        paint1.setShader(shader1);
        canvas.drawRect(0, 0, centerW, centerH, paint1);
        canvas.drawText("Clamp", halfCenterW - getTextSize("Clamp", textPaint).width() / 2, halfCenterH, textPaint);


        RadialGradient shader2 = new RadialGradient(halfCenterW + centerW, halfCenterH, halfCenterW, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.REPEAT);
        paint2.setShader(shader2);
        canvas.drawRect(centerW, 0, getWidth(), centerH, paint2);
        canvas.drawText("repeat", centerW + halfCenterW - getTextSize("repeat", textPaint).width() / 2, halfCenterH, textPaint);


        RadialGradient shader3 = new RadialGradient(halfCenterW, halfCenterH + centerH, halfCenterW, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.MIRROR);
        paint3.setShader(shader3);
        canvas.drawRect(0, centerH, centerW, getHeight(), paint3);
        canvas.drawText("mirror", halfCenterW - getTextSize("repeat", textPaint).width() / 2, halfCenterH + centerH, textPaint);

    }

    private Rect getTextSize(String text, Paint paint) {
        Rect rectF = new Rect();
        paint.getTextBounds(text, 0, text.length(), rectF);
        return rectF;
    }
}
