package com.zhyen.base.design_mode.observer_mode;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象被观察者
 */
public abstract class AbstractSubject {
    protected List<IObserver> list = new ArrayList();

    public void attach(IObserver observer) {
        list.add(observer);
    }

    public void detach(IObserver observer) {
        list.remove(observer);
    }

    protected void notifyObserver() {
        if (list.size() == 0) {
            System.out.println("没有更多的观察者了");
        }
        for (IObserver observer : list) {
            observer.update();
        }
    }

    public abstract void doSomething();
}
