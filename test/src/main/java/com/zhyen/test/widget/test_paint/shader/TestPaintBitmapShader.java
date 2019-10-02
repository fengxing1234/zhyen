package com.zhyen.test.widget.test_paint.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhyen.test.R;


public class TestPaintBitmapShader extends View {

    private Paint paint;

    public TestPaintBitmapShader(Context context) {
        super(context);
    }

    public TestPaintBitmapShader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
    }

    public TestPaintBitmapShader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerW = getWidth() / 2;
        int centerH = getHeight() / 2;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.batman);
        Shader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
        paint.setShader(shader);


        paint.setShader(shader);
        //canvas.drawCircle(200, 200, 200, paint);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

    }
}
