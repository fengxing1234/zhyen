package com.zhyen.test.widget.test_draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhyen.base.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class TestDrawHistogram extends View {

    private static final String TAG = TestDrawHistogram.class.getSimpleName();

    public TestDrawHistogram(Context context) {
        super(context);
        init(context);
    }

    public TestDrawHistogram(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestDrawHistogram(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Paint paint;
    private int width;
    private int height;
    private Context context;
    private int centerWidth;
    private int centerHeight;
    private int halfCenterWidth;
    private int halfCenterHeight;

    private RectF rectF = new RectF();

    private int space = 8;
    private List<Item> items;
    private float itemWidth = 50;


    private void init(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        float density = ScreenUtils.getDensity(context);
        Log.d(TAG, "init: " + density);
        space = (int) (space * density);
        itemWidth = itemWidth * density;
        ArrayList<Item> list = new ArrayList<>();
        list.add(new Item(0));
        list.add(new Item(0.22));
        list.add(new Item(0.45));
        list.add(new Item(0.86));
        list.add(new Item(0.16));
        setItemList(list);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        centerWidth = width / 2;
        centerHeight = height / 2;
        halfCenterWidth = centerWidth / 2;
        halfCenterHeight = centerHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (items == null || items.isEmpty()) {
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            float left = rectF.right + space;
            float top = (float) (height * (1 - items.get(i).percent));
            float right = left + itemWidth;
            float bottom = height - getPaddingBottom();
            if (items.get(i).percent == 0) {
                rectF.set(left, bottom + 3, right, bottom);
                paint.setColor(Color.GRAY);
                canvas.drawLine(left, bottom + 3, right, bottom, paint);
            } else {
                paint.setColor(Color.GREEN);
                rectF.set(left, top, right, bottom);
                canvas.drawRect(rectF, paint);
            }
        }
    }

    public void setItemList(List<Item> items) {
        this.items = items;
    }

    private class Item {
        public String name;
        public int itemWidth;
        public double percent;

        private Item(double percent) {
            this.percent = percent;
        }
    }

}
