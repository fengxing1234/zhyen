package com.zhyen.android.test.test_widget.test_draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class TestDrawPieChart extends View {

    private static final String TAG = "TestDrawPieChart";
    private static final int LINE_LENGTH = 50;// 线的长度

    private Paint paint;
    private Paint paintLine;


    private float[] percents = new float[]{0.04f, 0.04f, 0.14f, 0.28f, 0.33f, 0.17f};
    private int[] colors = new int[]{
            Color.parseColor("#830A9B"),//紫色
            Color.parseColor("#8c8c8c"),//灰色
            Color.parseColor("#118575"),//绿色
            Color.parseColor("#1E80F0"),//蓝色
            Color.parseColor("#EE2B29"),//红色
            Color.parseColor("#FDB60D"),//黄色
    };

    private String[] names = {"张一", "李二", "关三", "赵四", "王老五", "铜六", "韩七",};

    //背景色
    private int bgColor = Color.parseColor("#506E7A");
    //间距
    private int spec = 2;
    private Paint textPaint;

    public TestDrawPieChart(Context context) {
        super(context);
        init(context);
    }

    public TestDrawPieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestDrawPieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setAntiAlias(true);

        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setStrokeWidth(10);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setColor(Color.WHITE);
        paintLine.setStrokeJoin(Paint.Join.MITER);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(40);
        textPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景色
        canvas.drawColor(bgColor);
        //移动原点
        canvas.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        //计算半径
        float r = canvas.getWidth() / 2 * 0.6f;
        //获取矩形位置
        RectF rectF = new RectF(-r, -r, r, r);
        float lineEndX;

        //起始角度
        float startAngle = 0;
        for (int i = 0; i < percents.length; i++) {
            //扇形角度
            float sweepAngle = 360 * percents[i];
            //设置颜色
            paint.setColor(colors[i]);
            Log.d(TAG, "onDraw: startAngle = " + startAngle + " sweepAngle = " + sweepAngle);

            //画扇形
            if (i == 4) {
                RectF rectF1 = new RectF(-r - 20, -r - 20, r, r);
                canvas.drawArc(rectF1, startAngle, sweepAngle, true, paint);
            } else {
                RectF rectF1 = new RectF(-r - 10, -r - 10, r, r);
                canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);
            }


            //弧度？
            double theta = (startAngle + sweepAngle / 2) * Math.PI / 180;

            Log.d(TAG, "onDraw: theta = " + theta);
            float lineStartX = (float) (0 + r * Math.cos(theta));
            float lineStartY = (float) (0 + r * Math.sin(theta));
            float lineStopX = (float) (0 + (r + LINE_LENGTH) * Math.cos(theta));
            float lineStopY = (float) (0 + (r + LINE_LENGTH) * Math.sin(theta));

            canvas.drawLine(lineStartX, lineStartY, lineStopX, lineStopY, paintLine);

            Rect rect = textSize(names[i], textPaint);
            if (theta > Math.PI / 2 && theta < 3 * Math.PI / 2) {//左边
                lineEndX = lineStopX - LINE_LENGTH;
                canvas.drawLine(lineStopX, lineStopY, lineEndX, lineStopY, paintLine);
                canvas.drawText(names[i], lineEndX - rect.width() - 10, lineStopY + rect.height() / 2, textPaint);
            } else {//右边
                lineEndX = lineStopX + LINE_LENGTH;
                canvas.drawLine(lineStopX, lineStopY, lineEndX, lineStopY, paintLine);
                canvas.drawText(names[i], lineEndX + 10, lineStopY + rect.height() / 2, textPaint);

            }


            //计算下次起始位置
            startAngle += sweepAngle;
        }

    }

    private Rect textSize(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }
}
