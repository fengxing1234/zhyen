package com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo;

public interface Interceptor {

    TestResponse intercept(Chain chain);

    public interface Chain {
        TestRequest request();

        TestResponse proceed(TestRequest request);

        void connection();

        void call();

        int connectTimeoutMillis();

        Chain withConnectTimeout(int timeout);

        int readTimeoutMillis();

        Chain withReadTimeout(int timeout);

        int writeTimeoutMillis();

        Chain withWriteTimeout(int timeout);
    }
}
