package com.zhyen.base.design_mode.observer_mode;

/**
 * 具体被观察者
 */
public class ConcreteSubject extends AbstractSubject {

    @Override
    public void doSomething() {
        System.out.println("处理一些事情 处理完毕 需要告诉 观察者更新数据");
        notifyObserver();
    }
}
