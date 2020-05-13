package com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo;

import java.util.List;

public class RealInterceptorChain implements Interceptor.Chain {

    private List<Interceptor> list;
    private int index;
    private TestRequest request;

    public RealInterceptorChain(List<Interceptor> list, int i, TestRequest testRequest) {
        this.list = list;
        this.index = i;
        this.request = testRequest;
    }

    @Override
    public TestRequest request() {
        return request;
    }

    @Override
    public TestResponse proceed(TestRequest request) {
        System.out.println("当前的index = " + index);
        RealInterceptorChain next = new RealInterceptorChain(list, index + 1, request);
        Interceptor interceptor = list.get(index);
        return interceptor.intercept(next);
    }

    @Override
    public void connection() {

    }

    @Override
    public void call() {

    }

    @Override
    public int connectTimeoutMillis() {
        return 10;
    }

    @Override
    public Interceptor.Chain withConnectTimeout(int timeout) {
        return null;
    }

    @Override
    public int readTimeoutMillis() {
        return 10;
    }

    @Override
    public Interceptor.Chain withReadTimeout(int timeout) {
        return null;
    }

    @Override
    public int writeTimeoutMillis() {
        return 10;
    }

    @Override
    public Interceptor.Chain withWriteTimeout(int timeout) {
        return null;
    }

}
