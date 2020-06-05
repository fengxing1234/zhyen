package com.zhyen.test;

import com.zhyen.test.class_loader.ClassLoader;

public class TestMain {

    public static void main(String[] arg) {
        testClassLoader();
    }

    private static void testClassLoader() {
        ClassLoader classLoader = new ClassLoader();
        try {
            String loadClass = classLoader.loadClass("冯星");
            log("最后的结果:" + loadClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void log(String msg) {
        System.out.println(msg);
    }
}
