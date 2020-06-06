package com.zhyen.android;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.zhyen.base.app_monitor.AppMonitor;

import java.util.List;

public class ZhyenApplication extends Application {
    private static final String TAG = "ZhyenApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, getProcessName(getApplicationContext()));
        AppMonitor.get().initialize(this);
    }

    /**
     * onLowMemory——当后台程序已经终止且资源还匮乏时会调用这个方法。
     * 好的应用程序会在此释放资源。
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory: ");
    }

    /**
     * 通知 应用程序 当前内存使用情况（以内存级别进行识别）
     *
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(TAG, "onTrimMemory: " + level);
        if (level > ComponentCallbacks2.TRIM_MEMORY_MODERATE) {

        }
    }

    /**
     * 用于注册App内所有Activity的生命周期监听。
     *
     * @param callback
     */
    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);

    }

    /**
     * 用于注销App内所有Activity的生命周期监听。
     *
     * @param callback
     */
    @Override
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.unregisterActivityLifecycleCallbacks(callback);
    }

    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        super.registerComponentCallbacks(callback);
        Log.d(TAG, "registerComponentCallbacks: ");
    }

    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        super.unregisterComponentCallbacks(callback);
        Log.d(TAG, "unregisterComponentCallbacks: ");
    }

    //取得进程名
    public static String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == Process.myPid()) {
                return procInfo.processName;
            }
        }
        return null;
    }
}