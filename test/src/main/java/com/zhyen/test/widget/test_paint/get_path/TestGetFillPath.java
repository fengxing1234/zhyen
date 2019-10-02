package com.zhyen.test.widget.test_paint.get_path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 通过 getFillPath(src, dst) 方法就能获取这个实际 Path。
 * 方法的参数里，src 是原 Path ，而 dst 就是实际 Path 的保存位置。
 * getFillPath(src, dst) 会计算出实际 Path，然后把结果保存在 dst 里。
 */
public class TestGetFillPath extends View {

    private static final String TAG = "TestGetFillPath";

    public TestGetFillPath(Context context) {
        super(context);
    }

    public TestGetFillPath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestGetFillPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);


        float itemHeight = getHeight() / 3;
        float itemWidth = getWidth() / 2;
        Log.d(TAG, "onDraw: itemWidth = " + itemWidth + " itemHeight = " + itemHeight);
        float x = 0;
        float y = 0;
        Path path = new Path();
        float h = itemHeight / 2 * 0.6f;

        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                x = 0;
                y = 0;
            }
            if (i == 1) {
                x = itemWidth;
                y = 0;
            }
            if (i == 2) {
                x = 0;
                y = itemHeight;
            }
            if (i == 3) {
                x = itemWidth;
                y = itemHeight;
            }
            if (i == 4) {
                x = 0;
                y = 2 * itemHeight;
            }
            if (i == 5) {
                x = itemWidth;
                y = 2 * itemHeight;
            }


            path.moveTo(30 + x, itemHeight / 2 + y);
            path.lineTo(120 + x, itemHeight / 2 + h + y);
            path.lineTo(230 + x, itemHeight / 2 - h + y);
            path.lineTo(330 + x, itemHeight / 2 + h + y);
            path.lineTo(400 + x, itemHeight / 2 - h + y);
            path.lineTo(530 + x, itemHeight / 2 + y);
            if (i == 0) {
                canvas.drawPath(path, paint);
            }
            if (i == 1) {
                Path desPath = new Path();
                paint.getFillPath(path, desPath);
                canvas.drawPath(desPath, paint);
                path.reset();
            }

            if (i == 2) {
                paint.setStrokeWidth(20);
                canvas.drawPath(path, paint);
            }


        }

        Paint paint1 = new Paint();
        paint1.setStyle(Paint.Style.STROKE);

        Path tPath = new Path();
        x = itemWidth;
        y = itemHeight;

        tPath.moveTo(30 + x, itemHeight / 2 + y);
        tPath.lineTo(120 + x, itemHeight / 2 + h + y);
        tPath.lineTo(230 + x, itemHeight / 2 - h + y);
        tPath.lineTo(330 + x, itemHeight / 2 + h + y);
        tPath.lineTo(400 + x, itemHeight / 2 - h + y);
        tPath.lineTo(530 + x, itemHeight / 2 + y);
        Path sPath = new Path();
        paint.getFillPath(tPath, sPath);
        canvas.drawPath(sPath, paint1);

        x = 0;
        y = 2 * itemHeight;
        Paint paint2 = new Paint();
        paint2.setStrokeWidth(20);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(Color.BLACK);
        Path aPath = new Path();
        aPath.moveTo(30 + x, itemHeight / 2 + y);
        aPath.lineTo(120 + x, itemHeight / 2 + h + y);
        aPath.lineTo(230 + x, itemHeight / 2 - h + y);
        aPath.lineTo(330 + x, itemHeight / 2 + h + y);
        aPath.lineTo(400 + x, itemHeight / 2 - h + y);
        aPath.lineTo(530 + x, itemHeight / 2 + y);
        paint2.setPathEffect(new DashPathEffect(new float[]{10, 2, 10, 2}, 0));
        canvas.drawPath(aPath, paint2);

        x = itemWidth;
        y = 2 * itemHeight;

        Path bPath = new Path();
        bPath.moveTo(30 + x, itemHeight / 2 + y);
        bPath.lineTo(120 + x, itemHeight / 2 + h + y);
        bPath.lineTo(230 + x, itemHeight / 2 - h + y);
        bPath.lineTo(330 + x, itemHeight / 2 + h + y);
        bPath.lineTo(400 + x, itemHeight / 2 - h + y);
        bPath.lineTo(530 + x, itemHeight / 2 + y);
        Path nPath = new Path();
        paint2.getFillPath(bPath, nPath);
        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.BLACK);
        canvas.drawPath(nPath, p);

    }
}
