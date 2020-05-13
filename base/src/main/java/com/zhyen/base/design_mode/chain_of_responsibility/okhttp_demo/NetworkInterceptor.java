package com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo;

public class NetworkInterceptor implements Interceptor {
    @Override
    public TestResponse intercept(Chain chain) {
        TestRequest request = chain.request();
        request.des += "NetworkInterceptor 给请求添加一个字段";
        TestResponse response = new TestResponse();
        response.des += "NetworkInterceptor 给返回添加了一个字段";
        return response;
    }
}
