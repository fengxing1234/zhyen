package com.zhyen.base.design_mode.observer;

public class ObserverA implements IObserver {
    @Override
    public void update() {
        System.out.println("A 收到了更新请求");
    }
}
