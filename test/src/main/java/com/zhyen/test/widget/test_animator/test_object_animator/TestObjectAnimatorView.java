package com.zhyen.test.widget.test_animator.test_object_animator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import static com.zhyen.base.utils.UIUtils.dpToPixel;

public class TestObjectAnimatorView extends View {

    private static final String TAG = TestObjectAnimatorView.class.getSimpleName();
    private float progress = 0;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float radius = dpToPixel(80);

    {
        paint.setTextSize(dpToPixel(40));
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public TestObjectAnimatorView(Context context) {
        super(context);
    }

    public TestObjectAnimatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestObjectAnimatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        Log.d(TAG, "setProgress: ");
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        paint.setColor(Color.parseColor("#E91E63"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dpToPixel(15));
        paint.setStrokeCap(Paint.Cap.ROUND);
        RectF rectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(rectF, 135, progress * 360, false, paint);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        String text = (int) (progress * 100) + "%";
        canvas.drawText(text, 0, text.length(), centerX, centerY - (paint.ascent() + paint.descent()) / 2, paint);
    }
}
