package com.zhyen.base.app_monitor;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 监控Activity的生命周期
 * 并实现App前后台监
 */
public class AppMonitor implements AppState {

    private static final String TAG = AppMonitor.class.getSimpleName();
    //注册监听器
    private List<MonitorCallback> mCallbacks;
    //存活的页面
    private Stack<Activity> mAliveActivities = new Stack<>();
    //活跃的页面
    private List<Activity> mActiveActivities = new ArrayList<>();

    //是否初始化
    private boolean isInit;

    //是否活跃，该标志位是为了过滤重复调用的问题
    private boolean isActive;


    private AppMonitor() {

    }

    public static AppMonitor get() {
        return SingleHolder.INSTANCE;
    }

    private static final class SingleHolder {
        private static final AppMonitor INSTANCE = new AppMonitor();
    }

    public void initialize(Context context) {
        if (isInit) {
            return;
        }
        mCallbacks = new CopyOnWriteArrayList<>();
        registerLifecycle(context);
        isInit = true;
    }


    private void registerLifecycle(Context context) {
        Application application = (Application) context.getApplicationContext();
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d(TAG, "onActivityCreated: " + activity);
                mAliveActivities.push(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d(TAG, "onActivityStarted: " + activity);
                mActiveActivities.add(activity);
                notifyChange();
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(TAG, "onActivityResumed: " + activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d(TAG, "onActivityPaused: " + activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.d(TAG, "onActivityStopped: " + activity);
                mActiveActivities.remove(activity);
                notifyChange();
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.d(TAG, "onActivitySaveInstanceState: " + activity);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                mAliveActivities.remove(activity);
                Log.d(TAG, "onActivityDestroyed: " + activity);
                notifyAppAliveChange(activity);
            }
        });
    }

    @Override
    public boolean isAppAlive() {
        return mAliveActivities.size() > 0;
    }

    @Override
    public boolean isAppForeground() {
        return mActiveActivities.size() > 0;
    }

    @Override
    public boolean isAppBackground() {
        return mActiveActivities.size() <= 0;
    }

    @Override
    public void notifyChange() {
        if (mActiveActivities.size() > 0) {
            if (!isActive) {
                for (MonitorCallback callback : mCallbacks) {
                    callback.onAppForeground();
                }
                isActive = true;
            }
        } else {
            if (isActive) {
                for (MonitorCallback callback : mCallbacks) {
                    callback.onAppBackground();
                }
                isActive = false;
            }
        }
    }

    @Override
    public void register(MonitorCallback callback) {
        if (mCallbacks.contains(callback))
            return;
        mCallbacks.add(callback);
    }

    @Override
    public void unRegister(MonitorCallback callback) {
        if (!mCallbacks.contains(callback))
            return;
        mCallbacks.remove(callback);
    }

    @Override
    public void notifyAppAliveChange(Activity activity) {
        if (mActiveActivities.size() == 0) {
            for (MonitorCallback callback : mCallbacks) {
                callback.onAppUIDestroyed(activity);
            }
        }
    }

    @Override
    public List<Activity> getAliveActivityList() {
        return mAliveActivities;
    }

}
