package com.zhyen.test.class_loader;

public class URLClassLoader extends ClassLoader {

    public URLClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String findClass(String name) {
//        if (this instanceof Launcher.ExtClassLoader) {
//            return "冯星.class";
//        }
        return null;
    }
}
