package com.zhyen.test.widget.test_assist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhyen.test.R;

/**
 * 其实和 clipRect() 用法完全一样，只是把参数换成了 Path ，所以能裁切的形状更多一些：
 * <p>
 * 这里有个知识点：
 * <p>
 * path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
 * 设置填充方式
 * <p>
 * 是用来设置图形自相交时的填充算法的：
 * <p>
 * 方法中填入不同的 FillType 值，就会有不同的填充效果。FillType 的取值有四个：
 * <p>
 * EVEN_ODD
 * WINDING （默认值）
 * INVERSE_EVEN_ODD
 * INVERSE_WINDING
 * <p>
 * 其中后面的两个带有 INVERSE_ 前缀的，只是前两个的反色版本，所以只要把前两个，即 EVEN_ODD 和  WINDING，搞明白就可以了。
 * <p>
 * WINDING 是「全填充」，
 * 而 EVEN_ODD 是「交叉填充」
 * <p>
 * EVEN_ODD 和 WINDING 的原理
 * <p>
 * EVEN_ODD
 * 即 even-odd rule （奇偶原则）：对于平面中的任意一点，向任意方向射出一条射线，这条射线和图形相交的次数（相交才算，相切不算哦）
 * 如果是奇数，则这个点被认为在图形内部，是要被涂色的区域；如果是偶数，则这个点被认为在图形外部，是不被涂色的区域。
 * <p>
 * WINDING
 * 即 non-zero winding rule （非零环绕数原则）：首先，它需要你图形中的所有线条都是有绘制方向的：
 * <p>
 * 然后，同样是从平面中的点向任意方向射出一条射线，但计算规则不一样：
 * 以 0 为初始值，对于射线和图形的所有交点，遇到每个顺时针的交点（图形从射线的左边向右穿过）把结果加 1，
 * 遇到每个逆时针的交点（图形从射线的右边向左穿过）把结果减 1，最终把所有的交点都算上，得到的结果如果不是 0，则认为这个点在图形内部，是要被涂色的区域；
 * 如果是 0，则认为这个点在图形外部，是不被涂色的区域。
 *
 * 图形的方向：对于添加子图形类方法（如 Path.addCircle() Path.addRect()）的方向，由方法的  dir 参数来控制，这个在前面已经讲过了；而对于画线类的方法（如 Path.lineTo() Path.arcTo()）就更简单了，线的方向就是图形的方向。
 */
public class Practice02ClipPathView extends View {
    Paint paint = new Paint();
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(600, 200);

    public Practice02ClipPathView(Context context) {
        super(context);
    }

    public Practice02ClipPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice02ClipPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setColor(Color.BLACK);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        Path p1 = new Path();
        p1.addCircle(point1.x + bitmap.getWidth() / 2, point1.y + bitmap.getHeight() / 2, 130, Path.Direction.CCW);
        canvas.clipPath(p1);
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();

        canvas.save();
        Path path = new Path();
        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        path.addCircle(point2.x + bitmap.getWidth() / 2, point2.y + bitmap.getHeight() / 2, 130, Path.Direction.CCW);
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}
