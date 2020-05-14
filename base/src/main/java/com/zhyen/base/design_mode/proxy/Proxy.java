package com.zhyen.base.design_mode.proxy;

public class Proxy {

    private ISubject subject;

    public Proxy() {
        subject = new RealSubject();
    }

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
