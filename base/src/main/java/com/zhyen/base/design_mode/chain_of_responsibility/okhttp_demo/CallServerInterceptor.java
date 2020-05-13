package com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo;

public class CallServerInterceptor implements Interceptor {
    @Override
    public TestResponse intercept(Chain chain) {
        TestRequest request = chain.request();
        request.des += "CallServerInterceptor 给请求添加一个字段";
        TestResponse proceed = chain.proceed(request);
        proceed.des += "CallServerInterceptor 给返回添加了一个字段";
        return proceed;
    }
}
