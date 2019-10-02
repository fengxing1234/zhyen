package com.zhyen.test.widget.test_paint.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.SumPathEffect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Practice12PathEffectView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path();

    public Practice12PathEffectView(Context context) {
        super(context);
    }

    public Practice12PathEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12PathEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setStyle(Paint.Style.STROKE);

        path.moveTo(50, 100);
        path.rLineTo(50, 100);
        path.rLineTo(80, -150);
        path.rLineTo(100, 100);
        path.rLineTo(70, -120);
        path.rLineTo(150, 80);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 Paint.setPathEffect() 来设置不同的 PathEffect

        // 第一处：CornerPathEffect
        paint.setPathEffect(new CornerPathEffect(30));
        canvas.drawPath(path, paint);

        canvas.save();
        canvas.translate(500, 0);
        // 第二处：DiscretePathEffect
        paint.setPathEffect(new DiscretePathEffect(20, 5));
        canvas.drawPath(path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 200);
        // 第三处：DashPathEffect
        paint.setPathEffect(new DashPathEffect(new float[]{10, 5, 10, 5}, 0));
        canvas.drawPath(path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(500, 200);
        // 第四处：PathDashPathEffect
        Path pathDash = new Path();
        pathDash.moveTo(20, 20);
        pathDash.lineTo(30, 30);
        pathDash.lineTo(30, 10);
        pathDash.close();
        paint.setPathEffect(new PathDashPathEffect(pathDash, 20, 0, PathDashPathEffect.Style.TRANSLATE));
        canvas.drawPath(this.path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 400);
        // 第五处：SumPathEffect
        DiscretePathEffect discretePathEffect = new DiscretePathEffect(20, 5);
        DashPathEffect pathEffect = new DashPathEffect(new float[]{10, 5, 10, 5}, 0);
        paint.setPathEffect(new SumPathEffect(discretePathEffect, pathEffect));
        canvas.drawPath(this.path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(500, 400);
        // 第六处：ComposePathEffect
        DiscretePathEffect discreteEffect = new DiscretePathEffect(10, 5);
        DashPathEffect dashEffect = new DashPathEffect(new float[]{10, 5, 10, 5}, 5);
        ComposePathEffect composePathEffect = new ComposePathEffect(dashEffect, discreteEffect);
        paint.setPathEffect(composePathEffect);
        canvas.drawPath(this.path, paint);
        canvas.restore();
    }
}
