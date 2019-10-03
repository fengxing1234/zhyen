package com.zhyen.test.widget.test_draw_text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 获取 Paint 的 FontMetrics。
 * <p>
 * FontMetrics 是个相对专业的工具类，它提供了几个文字排印方面的数值：ascent, descent, top,  bottom, leading。
 * <p>
 * 如图，图中有两行文字，每一行都有 5 条线：top, ascent, baseline, descent, bottom。（leading 并没有画出来，因为画不出来，下面会给出解释）
 * <p>
 * <p>
 * baseline: 上图中黑色的线。前面已经讲过了，它的作用是作为文字显示的基准线。
 * <p>
 * ascent / descent: 上图中绿色和橙色的线，它们的作用是限制普通字符的顶部和底部范围。
 * 普通的字符，上不会高过 ascent ，下不会低过 descent ，例如上图中大部分的字形都显示在  ascent 和 descent 两条线的范围内。具体到 Android 的绘制中， ascent 的值是图中绿线和  baseline 的相对位移，它的值为负（因为它在 baseline 的上方）； descent 的值是图中橙线和  baseline 相对位移，值为正（因为它在 baseline 的下方）。
 * <p>
 * top / bottom: 上图中蓝色和红色的线，它们的作用是限制所有字形（ glyph ）的顶部和底部范围。
 * 除了普通字符，有些字形的显示范围是会超过 ascent 和 descent 的，而 top 和 bottom 则限制的是所有字形的显示范围，包括这些特殊字形。例如上图的第二行文字里，就有两个泰文的字形分别超过了 ascent 和 descent 的限制，但它们都在 top 和 bottom 两条线的范围内。具体到 Android 的绘制中， top 的值是图中蓝线和 baseline 的相对位移，它的值为负（因为它在 baseline 的上方）；  bottom 的值是图中红线和 baseline 相对位移，值为正（因为它在 baseline 的下方）。
 * <p>
 * leading: 这个词在上图中没有标记出来，因为它并不是指的某条线和 baseline 的相对位移。  leading 指的是行的额外间距，即对于上下相邻的两行，上行的 bottom 线和下行的 top 线的距离，也就是上图中第一行的红线和第二行的蓝线的距离（对，就是那个小细缝）。
 * <p>
 * leading 这个词的本意其实并不是行的额外间距，而是行距，即两个相邻行的 baseline 之间的距离。不过对于很多非专业领域，leading 的意思被改变了，被大家当做行的额外间距来用；而 Android 里的 leading ，同样也是行的额外间距的意思。
 * <p>
 * <p>
 * <p>
 * FontMetrics 提供的就是 Paint 根据当前字体和字号，得出的这些值的推荐值。它把这些值以变量的形式存储，供开发者需要时使用。
 * <p>
 * FontMetrics.ascent：float 类型。
 * FontMetrics.descent：float 类型。
 * FontMetrics.top：float 类型。
 * FontMetrics.bottom：float 类型。
 * FontMetrics.leading：float 类型。
 * <p>
 * 另外，ascent 和 descent 这两个值还可以通过 Paint.ascent() 和 Paint.descent() 来快捷获取。
 * <p>
 * FontMetrics 和 getFontSpacing()：
 * <p>
 * 从定义可以看出，上图中两行文字的 font spacing (即相邻两行的 baseline 的距离) 可以通过  bottom - top + leading (top 的值为负，前面刚说过，记得吧？）来计算得出。
 * <p>
 * 但你真的运行一下会发现， bottom - top + leading 的结果是要大于 getFontSpacing() 的返回值的。
 * <p>
 * 两个方法计算得出的 font spacing 竟然不一样？
 * <p>
 * 这并不是 bug，而是因为 getFontSpacing() 的结果并不是通过 FontMetrics 的标准值计算出来的，而是另外计算出来的一个值，它能够做到在两行文字不显得拥挤的前提下缩短行距，以此来得到更好的显示效果。所以如果你要对文字手动换行绘制，多数时候应该选取 getFontSpacing() 来得到行距，不但使用更简单，显示效果也会更好。
 */
public class Practice14GetFontMetricsView extends View {
    Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    String[] texts = {"A", "a", "J", "j", "Â", "â"};
    int top = 200;
    int bottom = 400;

    public Practice14GetFontMetricsView(Context context) {
        super(context);
    }

    public Practice14GetFontMetricsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice14GetFontMetricsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(20);
        paint1.setColor(Color.parseColor("#E91E63"));
        paint2.setTextSize(160);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(50, top, getWidth() - 50, bottom, paint1);

        // 使用 Paint.getFontMetrics() 计算出文字的显示区域
        // 然后计算出文字的绘制位置，从而让文字上下居中
        // 这种居中算法的优点是，可以让不同的文字的 baseline 对齐
        Paint.FontMetrics fontMetrics = paint2.getFontMetrics();
        float a = (fontMetrics.descent + fontMetrics.ascent) / 2;
        int middle = (top + bottom) / 2 - (int) a;
        canvas.drawText(texts[0], 100, middle, paint2);
        canvas.drawText(texts[1], 200, middle, paint2);
        canvas.drawText(texts[2], 300, middle, paint2);
        canvas.drawText(texts[3], 400, middle, paint2);
        canvas.drawText(texts[4], 500, middle, paint2);
        canvas.drawText(texts[5], 600, middle, paint2);
    }
}
