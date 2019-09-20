package com.zhyen.android.test.test_widget;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class testShadow extends AppCompatImageView {

    private Context context;
    private int width;
    private int height;
    private Paint paint;

    public testShadow(Context context) {
        this(context, null);
    }

    public testShadow(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public testShadow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setMaskFilter(new BlurMaskFilter(25, BlurMaskFilter.Blur.NORMAL));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Bitmap bitmap = Bitmap.createBitmap(300, 300,
//                Bitmap.Config.ARGB_8888);
//        bitmap.eraseColor(Color.parseColor("#66000000"));
//        Drawable drawable = getResources().getDrawable(R.drawable.test_image);
//        //Bitmap bitmap = BitmapUtils.drawableToBitmap(drawable);
//        Bitmap blur = BlurUtils.blur(context, bitmap);
//        canvas.drawBitmap(blur, 0, 0, paint);
        paint.setColor(Color.parseColor("#66000000"));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, width, height, paint);
    }
}
