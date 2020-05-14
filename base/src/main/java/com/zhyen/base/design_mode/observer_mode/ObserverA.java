package com.zhyen.base.design_mode.observer_mode;

public class ObserverA implements IObserver {
    @Override
    public void update() {
        System.out.println("A 收到了更新请求");
    }
}
