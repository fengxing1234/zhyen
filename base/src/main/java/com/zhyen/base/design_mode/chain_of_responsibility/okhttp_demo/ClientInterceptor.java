package com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo;

/**
 * 客户端拦截器
 */
public class ClientInterceptor implements Interceptor {
    @Override
    public TestResponse intercept(Chain chain) {
        TestRequest request = chain.request();
        request.des += "ClientInterceptor 给请求添加一个字段";
        TestResponse proceed = chain.proceed(request);
        proceed.des += "ClientInterceptor 给返回添加了一个字段";
        return proceed;
    }
}
