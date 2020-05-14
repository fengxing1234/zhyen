package com.zhyen.base.design_mode.proxy.UML;

public class Proxy implements ISubject {

    private ISubject subject;

    public Proxy() {
        subject = new RealSubject();
    }

    @Override
    public void request() {
        preRequest();
        subject.request();
        postRequest();
    }

    private void preRequest() {
        System.out.println("访问真实主题之后的预处理。");
    }

    private void postRequest() {
        System.out.println("访问真实主题之后的后续处理。");
    }
}
