package com.zhyen.base.design_mode.proxy;

public class RealSubject implements ISubject {
    @Override
    public void request() {
        System.out.println("访问真实主题方法...");
    }
}
