package com.zhyen.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class ScreenUtils {

    private static final String TAG = ScreenUtils.class.getSimpleName();


    /**
     * 获取的则是真正原始的屏幕尺寸，纯天然无公害的屏幕尺寸，二者获的Width相同，Height不同。
     * <p>
     * 官网描述的信息 相等 分辨率 2280*1080
     * onePlus6
     * width: 1080 height = 2280 density = 2.625 densityDpi = 420
     *
     * @param context
     */
    public static Point getScreenRawPixels(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return null;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null) {
            return null;
        }
        DisplayMetrics metric = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(metric);
        int width = metric.widthPixels; // 宽度（PX）
        int height = metric.heightPixels; // 高度（PX）
        float density = metric.density; // 密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;
        Log.d(TAG, "width: " + width + " height = " + height + " density = " + density + " densityDpi = " + densityDpi);
        Point point = new Point();
        point.x = width;
        point.y = height;
        return point;
    }

    /**
     * 去除虚拟按键后的尺寸
     * onePlus6
     * width: 1080 height = 2075 density = 2.625 densityDpi = 420
     *
     * @param context
     */
    public static Point getScreenPixels(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return null;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null) {
            return null;
        }
        DisplayMetrics metric = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 宽度（PX）
        int height = metric.heightPixels; // 高度（PX）
        float density = metric.density; // 密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;
        Log.d(TAG, "width: " + width + " height = " + height + " density = " + density + " densityDpi = " + densityDpi);
        Point point = new Point();
        point.x = width;
        point.y = height;
        return point;
    }

    /**
     * 判断Activity是横屏还是竖屏
     *
     * @param context
     * @return
     */
    public static int getScreenOrientation(Context context) {

        Configuration configuration = context.getResources().getConfiguration(); //获取设置的配置信息
        return configuration.orientation; //获取屏幕方向
    }

    /**
     * 返回屏幕自然方向
     *
     * @param context
     * @return
     */
    public static int getRotation(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (manager == null) {
            return -1;
        }
        return manager.getDefaultDisplay().getRotation();
    }

    public static float getDensity(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        //Log.d(TAG, "getDensity: " + density);
        return density;
    }

    public static final float getHeightInPx(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    }

    public static final float getWidthInPx(Context context) {
        final float width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获得当前屏幕亮度的模式
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
     */
    public static int getScreenBrightnessMode(Context context) {
        int screenMode = -1;
        try {
            screenMode = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "screenMode = " + screenMode);
        return screenMode;
    }

    /**
     * 设置当前屏幕亮度的模式
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
     */
    public static void setScreenBrightnessMode(Context context, int value) {
        Log.d(TAG, "setScreenBrightnessMode: " + value);
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, value);
    }

    /**
     * 获得当前屏幕亮度值 0--255
     *
     * @param context
     * @return
     */
    public static int getScreenBrightness(Context context) {
        try {
            int brightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            Log.d(TAG, "getScreenBrightness: " + brightness);
            return brightness;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void setScreenBrightness(Context context, int value) {
        Log.d(TAG, "setScreenBrightness: " + value);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(context)) {
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, value);
            }
        }
    }

    public static Intent getWriteSettings(Context context) {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
        }
        return intent;
    }

    /**
     * 只改变当前Activity亮度 不改变系统亮度
     *
     * @param activity
     * @param value
     */
    public static void setAcitivityBrightness(Activity activity, int value) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        int brightness = value / 100;
        Log.d(TAG, "brightness: " + brightness);
        lp.screenBrightness = brightness;
        Log.d(TAG, "setScreenBrightness: " + lp.screenBrightness);
        activity.getWindow().setAttributes(lp);
    }
}
