package com.zhyen.android.test.test_interview;

import java.util.Collection;

interface InterfaceA{
    public static void getA(){

    }
    void a();
}

interface InterfaceB{
    void b();
}
public interface TestInterface extends InterfaceA,InterfaceB , Collection {
    String a = "a";
    void a();
    void b();
}
