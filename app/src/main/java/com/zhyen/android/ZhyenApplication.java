package com.zhyen.android;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import java.util.List;

public class ZhyenApplication extends Application {
    private static final String TAG = "ZhyenApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, getProcessName(getApplicationContext()));
    }

    /**
     * 当终止应用程序对象时调用，不保证一定被调用，当程序被内核终止以便为其他应用程序释放资源时将不会提醒，
     * 并且不调用应用程序对象的onTerminate方法而直接终止进程
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate: ");
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
     * 用于注册App内所有Activity的生命周期监听。
     * @param callback
     */
    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);

    }

    /**
     * 用于注销App内所有Activity的生命周期监听。
     * @param callback
     */
    @Override
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.unregisterActivityLifecycleCallbacks(callback);
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