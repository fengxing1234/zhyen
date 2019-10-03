package com.zhyen.test.widget.test_draw_text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 设置文字的对齐方式。一共有三个值：LEFT CETNER 和 RIGHT。默认值为 LEFT。
 */
public class Practice10SetTextAlignView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    String text = "Hello HenCoder";

    public Practice10SetTextAlignView(Context context) {
        super(context);
    }

    public Practice10SetTextAlignView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10SetTextAlignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setTextSize(60);

        // 使用 Paint.setTextAlign() 来调整文字对齐方式
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 Paint.setTextAlign() 来调整文字对齐方式

        // 第一处：使用 Paint.Align.LEFT
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, getWidth() / 2, 100, paint);

        // 第二处：使用 Paint.Align.CENTER
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, getWidth() / 2, 200, paint);

        // 第三处：使用 Paint.Align.RIGHT
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(text, getWidth() / 2, 300, paint);
    }
}
