package com.zhyen.android.test.test_interview;

public class Base<T> {

    private T t;

    public void setT(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public <E, K> K print(E e, K k) {

        return k;
    }
}
