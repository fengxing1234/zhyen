package com.zhyen.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

public class UIUtils {

    private static final String TAG = UIUtils.class.getSimpleName();

    //根据传入的值 计算出遗一行能显示几个图片
    public static int spanCount(Context context, int gridExpectedSize) {
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        Log.d(TAG, "spanCount: " + screenWidth);
        int expected = screenWidth / gridExpectedSize;
        int count = Math.round(expected);
        if (count == 0) {
            count = 1;
        }
        return count;
    }

    public static float dpToPixel(int dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * metrics.density;
    }
}
