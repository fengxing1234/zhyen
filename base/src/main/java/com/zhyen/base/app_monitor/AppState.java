package com.zhyen.base.app_monitor;

import android.app.Activity;
import android.content.Context;

import java.util.List;

/**
 * 监控app对外提供的方法
 * 面向接口编程
 */
public interface AppState {

    void initialize(Context context);

    /**
     * 判断App是否活着
     */
    boolean isAppAlive();

    /**
     * 判断App是否在前台
     */
    boolean isAppForeground();

    /**
     * 判断App是否在后台
     */
    boolean isAppBackground();

    /**
     * 通知监听者
     */
    void notifyChange();

    /**
     * 注册回调
     */
    void register(MonitorCallback callback);

    /**
     * 注销回调
     */
    void unRegister(MonitorCallback callback);

    void notifyAppAliveChange(Activity activity);

    /**
     * 获取任务栈中所有的Activity
     *
     * @return
     */
    List<Activity> getAliveActivityList();


}
