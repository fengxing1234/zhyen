package com.zhyen.base.design_mode.observer;

/**
 * 具体的观察者
 */
public class ObserverB implements IObserver {
    @Override
    public void update() {
        System.out.println("B 收到了更新请求");
    }
}
