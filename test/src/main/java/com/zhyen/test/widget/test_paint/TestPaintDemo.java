package com.zhyen.test.widget.test_paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Shader.TileMode 定义了平铺的3种模式：
 * static final Shader.TileMode CLAMP: 边缘拉伸.
 * <p>
 * <p>
 * static final Shader.TileMode MIRROR：在水平方向和垂直方向交替景象, 两个相邻图像间没有缝隙.
 * <p>
 * <p>
 * Static final Shader.TillMode REPETA：在水平方向和垂直方向重复摆放,两个相邻图像间有缝隙缝隙.
 */
public class TestPaintDemo extends View {

    private static final String TAG = TestPaintDemo.class.getSimpleName();

    private Shader.TileMode[] modes = {Shader.TileMode.CLAMP, Shader.TileMode.MIRROR, Shader.TileMode.REPEAT};

    private int[] colors = new int[]{
            Color.parseColor("#830A9B"),//紫色
            Color.parseColor("#8c8c8c"),//灰色
            Color.parseColor("#118575"),//绿色
            Color.parseColor("#1E80F0"),//蓝色
            Color.parseColor("#EE2B29"),//红色
            Color.parseColor("#FDB60D"),//黄色
            Color.parseColor("#E91E63"),//粉红
            Color.parseColor("#2196F3"),//深蓝色
    };
    private Paint paint;


    public TestPaintDemo(Context context) {
        super(context);
        init();
    }

    public TestPaintDemo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestPaintDemo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint = new Paint();
        paint.setStrokeWidth(40);
        paint.setStyle(Paint.Style.STROKE);
//        Shader shader = new LinearGradient(100, 100, 500, 500, Color.parseColor("#E91E63"),
//                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(100, 100);


        Path path = new Path();
//        path.rLineTo(200, 0);
//        path.rLineTo(-160, 120);
        path.moveTo(200, 200);
        path.lineTo(300, 500);
        canvas.drawPath(path, paint);

        canvas.translate(200, 200);
        canvas.drawPath(path, paint);

    }

    private void test2(Canvas canvas) {
        // TODO: 2019-09-30 Shader.TileMode 三种模式效果一样 REPEAT CLAMP MIRROR
        Shader shader = new LinearGradient(100, 100, 500, 500, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.REPEAT);
        paint.setShader(shader);
        canvas.drawCircle(300, 300, 200, paint);
    }

    private void test(Canvas canvas) {
        Log.d(TAG, "init: " + getWidth() + "=========" + getHeight());

        int itemHeight = getHeight() / modes.length;
        int rectHeight = 0;

        for (int i = 0; i < modes.length; i++) {
            RectF rectF = new RectF(0, rectHeight, getWidth(), itemHeight + rectHeight);


            Shader shader = new LinearGradient(0, rectHeight, getWidth(), itemHeight + rectHeight, Color.parseColor("#E91E63"),
                    Color.parseColor("#2196F3"), modes[i]);
            paint.setShader(shader);
            canvas.drawRect(rectF, paint);

            rectHeight += itemHeight;

        }
    }
}
