package com.zhyen.android;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.nfc.Tag;
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