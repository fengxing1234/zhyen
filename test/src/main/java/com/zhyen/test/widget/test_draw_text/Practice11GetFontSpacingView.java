package com.zhyen.test.widget.test_draw_text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 获取推荐的行距。
 * <p>
 * 即推荐的两行文字的 baseline 的距离。这个值是系统根据文字的字体和字号自动计算的。它的作用是当你要手动绘制多行文字（而不是使用 StaticLayout）的时候，可以在换行的时候给 y 坐标加上这个值来下移文字。
 * <p>
 * canvas.drawText(texts[0], 100, 150, paint);
 * canvas.drawText(texts[1], 100, 150 + paint.getFontSpacing, paint);
 * canvas.drawText(texts[2], 100, 150 + paint.getFontSpacing * 2, paint);
 */
public class Practice11GetFontSpacingView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    String text = "Hello HenCoder";

    public Practice11GetFontSpacingView(Context context) {
        super(context);
    }

    public Practice11GetFontSpacingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice11GetFontSpacingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setTextSize(60);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 Paint.getFontSpacing() 来获取推荐的行距
        float spacing = paint.getFontSpacing();

        canvas.drawText(text, 50, 100, paint);

        canvas.drawText(text, 50, 100 + spacing, paint);

        canvas.drawText(text, 50, 100 + spacing * 2, paint);
    }
}
