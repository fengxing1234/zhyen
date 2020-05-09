package com.zhyen.android.test.test_interview;

public class TestAnnotationClass {

    @TestAnnotation(value = "汪汪汪", name = "dog", index = 100)
    public String dog;
    @TestAnnotation(value = "喵喵喵", name = "cat", index = 200)
    public String cat;

    @TestAnnotation(value = "爱爆炸", index = 10, name = "三星")
    public void phone() {

    }

    @TestAnnotation(value = "细", index =20, name = "ARM")
    public void CPU() {

    }
}
