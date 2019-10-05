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


public class Practice12CameraRotateFixedView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(600, 200);
    private Camera camera = new Camera();

    public Practice12CameraRotateFixedView(Context context) {
        super(context);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = point1.x + bitmap.getWidth() / 2;
        float centerY = point1.y + bitmap.getHeight() / 2;

        canvas.save();//保存canvas状态
        camera.save();//保存camera状态
        camera.rotateX(30);//旋转 Camera 的三维空间
        canvas.translate(centerX, centerY);//旋转之后把投影移动回来
        camera.applyToCanvas(canvas);//把旋转投影到 Canvas
        camera.restore();//恢复 Camera 的状态
        canvas.translate(-centerX, -centerY);//旋转之前把绘制内容移动到轴心（原点）
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);//画图
        canvas.restore();//恢复canvas状态

        canvas.save();
        camera.save();
        camera.rotateY(30);
        int center2Y = point2.y + bitmap.getHeight() / 2;
        int center2X = point2.x + bitmap.getWidth() / 2;
        canvas.translate(center2X, center2Y);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.translate(-center2X, -center2Y);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}
