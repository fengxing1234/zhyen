package com.zhyen.test.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * src表示的是矩形，也就是下一个准备绘制的像素颜色。而dsc则表示圆形，也就是准备绘制的目标像素上原有的颜色。
 * <p>
 * 1.PorterDuff.Mode.CLEAR 
 * <p>
 *   所绘制不会提交到画布上。
 * 2.PorterDuff.Mode.SRC
 * <p>
 *    显示上层绘制图片
 * 3.PorterDuff.Mode.DST
 * <p>
 *   显示下层绘制图片
 * 4.PorterDuff.Mode.SRC_OVER
 * <p>
 *   正常绘制显示，上下层绘制叠盖。
 * 5.PorterDuff.Mode.DST_OVER
 * <p>
 *   上下层都显示。下层居上显示。
 * 6.PorterDuff.Mode.SRC_IN
 * <p>
 *    取两层绘制交集。显示上层。
 * 7.PorterDuff.Mode.DST_IN
 * <p>
 *   取两层绘制交集。显示下层。
 * 8.PorterDuff.Mode.SRC_OUT
 * <p>
 *  取上层绘制非交集部分。
 * 9.PorterDuff.Mode.DST_OUT
 * <p>
 *  取下层绘制非交集部分。
 * 10.PorterDuff.Mode.SRC_ATOP
 * <p>
 *  取下层非交集部分与上层交集部分
 * 11.PorterDuff.Mode.DST_ATOP
 * <p>
 *  取上层非交集部分与下层交集部分
 * 12.PorterDuff.Mode.XOR
 * <p>
 *   异或：去除两图层交集部分
 * 13.PorterDuff.Mode.DARKEN
 * <p>
 *   取两图层全部区域，交集部分颜色加深
 * 14.PorterDuff.Mode.LIGHTEN
 * <p>
 *   取两图层全部，点亮交集部分颜色
 * 15.PorterDuff.Mode.MULTIPLY
 * <p>
 *   取两图层交集部分叠加后颜色
 * 16.PorterDuff.Mode.SCREEN
 * <p>
 *   取两图层全部区域，交集部分变为透明色
 * <p>
 * <p>
 */
public class TestXformodeView extends View {
    private static final String TAG = "TestXformodeView";
    // 问题1  ：PorterDuff.Mode.CLEAR 剪切部分 没有变成白色 是黑色
    // 已解决 ：PorterDuff.Mode.CLEAR不支持硬件加速

    //硬件加速分全局（Application）、Activity、Window、View 四个层级，
    //1. 全局  <application android:hardwareAccelerated="true" ...>
    //2. Activity <activity android:hardwareAccelerated="false" />
    //3. Window getWindow().setFlags(    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    //4. setLayerType(View.LAYER_TYPE_SOFTWARE, null);


    private Paint mPaint;
    private Xfermode mXfermode;

    public TestXformodeView(Context context) {
        super(context);
        init();
    }

    public TestXformodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestXformodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: ");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        Log.d(TAG, "onMeasure: ");
    }

    private void init() {
        Log.d(TAG, "init: ");
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //test1(canvas);
        canvas.drawARGB(255, 139, 197, 186);
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        Log.d(TAG, "onDraw: " + getWidth() + "::::" + getHeight());
        canvas.drawBitmap(makeDst(getWidth(), getWidth()), 0, 0, mPaint);
        mPaint.setXfermode(mXfermode);
        canvas.drawBitmap(makeSrc(getWidth(), getWidth()), 0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    private Bitmap makeDst(int w, int h) {
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //canvas.drawARGB(255, 25, 226, 25);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xFFFFCC44);
        canvas.drawOval(new RectF(0, 0, w * 3 / 4, h * 3 / 4), paint);
        return bitmap;
    }

    static Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        //c.drawARGB(125, 144, 25, 166);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFF66AAFF);
        c.drawRect(w / 3, h / 3, w * 19 / 20, h * 19 / 20, p);
        return bm;
    }

    public void setXfermode(Xfermode xfermode) {
        this.mXfermode = xfermode;
        invalidate();
    }
}
