package com.zhyen.test.widget.test_draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TestDrawHistogram3 extends View {

    private Paint paint;
    private Paint origin = new Paint();
    private int[] heights = {0, 50, 70, 250, 400, 450, 200};
    private String[] names = {"张一", "李二", "关三", "赵四", "王老五", "铜六", "韩七",};
    private int originHeight = 600;
    private int originWidth = 900;
    private int itemWidth = 100;
    private int spec = 20;
    private int textTop = 50;

    public TestDrawHistogram3(Context context) {
        super(context);
        init();
    }

    public TestDrawHistogram3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestDrawHistogram3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10);
        paint.setTextSize(40);

        origin.setAntiAlias(true);
        origin.setStrokeWidth(4);
        origin.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景
        canvas.drawARGB(255, 139, 197, 186);

        //移动原点坐标
        canvas.translate(canvas.getWidth() * 0.15f, canvas.getHeight() * 0.6f);

        //画坐标系
        canvas.drawLine(0, 0, 0, -originHeight, origin);
        canvas.drawLine(0, 0, originWidth, 0, origin);
        float left = spec;
        for (int i = 0; i < heights.length; i++) {
            canvas.drawRect(new RectF(left, -heights[i], left + itemWidth, 0), paint);
            float textLeft = left + (itemWidth - textSize(names[i], paint).width()) / 2;
            canvas.drawText(names[i], textLeft, textTop, paint);
            left += itemWidth + spec;
        }

    }

    private Rect textSize(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }
}
