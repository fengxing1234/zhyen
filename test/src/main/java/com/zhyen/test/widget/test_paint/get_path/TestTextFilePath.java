package com.zhyen.test.widget.test_paint.get_path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TestTextFilePath extends View {
    public TestTextFilePath(Context context) {
        super(context);
    }

    public TestTextFilePath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTextFilePath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String text = "这是一条神奇的山路，这里的山路18弯";

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(50);
        paint.setColor(Color.BLACK);

        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);

        Path newPath = new Path();
        paint.getTextPath(text, 0, text.length(), getWidth() / 2 - rect.width() / 2, getHeight() * 2 / 3, newPath);

        canvas.drawPath(newPath, paint);

        //String text1 = "这是一条神奇的山路，这里的山路18弯";
        Paint paint1 = new Paint();
        paint1.setTextSize(50);
        paint1.setColor(Color.BLACK);
        Rect rect1 = new Rect();
        paint1.getTextBounds(text, 0, text.length(), rect1);
        canvas.drawText(text, getWidth() / 2 - rect1.width() / 2, getHeight() / 3, paint1);
    }

}
