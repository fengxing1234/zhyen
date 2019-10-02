package com.zhyen.android.test.test_widget.test_paint.shader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TestPaintLinearGradient extends View {

    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private Paint textPaint;

    public TestPaintLinearGradient(Context context) {
        super(context);
    }

    public TestPaintLinearGradient(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public TestPaintLinearGradient(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerW = getWidth() / 2;
        float centerH = getHeight() / 2;

        float halfCenterW = centerW / 2;
        float halfCenterH = centerH / 2;

        float r = halfCenterW;

        Shader shader1 = new LinearGradient(0, 0, centerW, 0, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        paint1.setShader(shader1);
        canvas.drawRect(0, 0, centerW, centerH, paint1);
        canvas.drawText("Clamp", halfCenterW - getTextSize("Clamp", textPaint).width() / 2, halfCenterH, textPaint);


        Shader shader2 = new LinearGradient(centerW, 0, getWidth(), 0, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.REPEAT);
        paint2.setShader(shader2);
        canvas.drawRect(centerW, 0, getWidth(), centerH, paint2);
        canvas.drawText("repeat", centerW + halfCenterW - getTextSize("repeat", textPaint).width() / 2, halfCenterH, textPaint);


        Shader shader3 = new LinearGradient(0, centerH, centerW, centerH, Color.parseColor("#E91E63"),
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
