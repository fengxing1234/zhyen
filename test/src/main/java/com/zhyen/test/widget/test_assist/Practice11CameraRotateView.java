package com.zhyen.test.widget.test_assist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhyen.test.R;


/**
 * Camera 和 Canvas 一样也需要保存和恢复状态才能正常绘制，不然在界面刷新之后绘制就会出现问题。
 * <p>
 * 如果你需要图形左右对称，需要配合上 Canvas.translate()，在三维旋转之前把绘制内容的中心点移动到原点，即旋转的轴心，然后在三维旋转后再把投影移动回来：
 * <p>
 * canvas.save();//保存canvas状态
 * camera.save(); // 保存 Camera 的状态
 * camera.rotateX(30); // 旋转 Camera 的三维空间
 * canvas.translate(centerX, centerY); // 旋转之后把投影移动回来
 * camera.applyToCanvas(canvas); // 把旋转投影到 Canvas
 * canvas.translate(-centerX, -centerY); // 旋转之前把绘制内容移动到轴心（原点）
 * camera.restore(); // 恢复 Camera 的状态
 * canvas.drawBitmap(bitmap, point1.x, point1.y, paint);//画图像
 * canvas.restore();//回复canvas状态
 * <p>
 * Canvas 的几何变换顺序是反的，所以要把移动到中心的代码写在下面，把从中心移动回来的代码写在上面。
 *
 * Camera 旋转方向 xy顺时针 z逆时针
 */
public class Practice11CameraRotateView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 100);
    Point point2 = new Point(600, 200);
    Camera camera = new Camera();

    public Practice11CameraRotateView(Context context) {
        super(context);
    }

    public Practice11CameraRotateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice11CameraRotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        camera.save();
        camera.rotateX(30);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();

        canvas.save();
        camera.save();
        camera.rotateY(30);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}
