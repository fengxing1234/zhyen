package com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo;

/**
 * 应用到网络的桥梁
 */
public class BridgeInterceptor implements Interceptor {
    @Override
    public TestResponse intercept(Chain chain) {
        TestRequest request = chain.request();
        request.des += "BridgeInterceptor 给请求添加一个字段";
        TestResponse proceed = chain.proceed(request);
        proceed.des += "BridgeInterceptor 给返回添加了一个字段";
        return proceed;
    }
}
