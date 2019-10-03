package com.zhyen.test.widget.test_draw_text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * text 是文字内容，x 和 y 是文字的坐标。
 * 但需要注意：这个坐标并不是文字的左上角，而是一个与左下角比较接近的位置。
 * 其它的 Canvas.drawXXX() 方法，都是以左上角作为基准点的，而 drawText() 却是文字左下方
 * <p>
 * drawText() 参数中的 y ，指的是文字的基线（ baseline ） 的位置。也就是这条线：
 * <p>
 * 众所周知，不同的语言和文字，每个字符的高度和上下位置都是不一样的。要让不同的文字并排显示的时候整体看起来稳当，需要让它们上下对齐。但这个对齐的方式，不能是简单的「底部对齐」或「顶部对齐」或「中间对齐」，而应该是一种类似于「重心对齐」的方式。
 *
 * 而这个用来让所有文字互相对齐的基准线，就是基线( baseline )。 drawText() 方法参数中的 y 值，就是指定的基线的位置。
 */
public class Practice01DrawTextView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    String text = "Hello HenCoder";

    public Practice01DrawTextView(Context context) {
        super(context);
    }

    public Practice01DrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice01DrawTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setTextSize(60);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 drawText() 来绘制文字
        // 文字坐标： (50, 100)
        canvas.drawText(text, 50, 100, paint);
    }
}
