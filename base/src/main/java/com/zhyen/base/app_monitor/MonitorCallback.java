package com.zhyen.base.app_monitor;

import android.app.Activity;

/**
 * App监控回调接口 提供相应的接口
 */
public interface MonitorCallback {
    /**
     * 当App切换到前台时回调
     */
    void onAppForeground();


    /**
     * App切换到后台时回调
     */
    void onAppBackground();


    /**
     * App所有界面都销毁了
     */
    void onAppUIDestroyed(Activity activity);
}
