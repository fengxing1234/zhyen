package com.zhyen.test.class_loader;

import com.zhyen.test.TestMain;

public class ClassLoader {

    private ClassLoader parent;
    private static ClassLoader classLoader;

    public ClassLoader(ClassLoader parent) {
        this.parent = parent;
    }

    public ClassLoader() {
        this(getSystemClassLoader());
    }

    private static ClassLoader getSystemClassLoader() {
        initSystemClassLoader();
        if (classLoader == null) {
            return null;
        }
        return classLoader;
    }

    private static void initSystemClassLoader() {
        Launcher launcher = Launcher.getLauncher();
        if (launcher != null) {
            classLoader = launcher.getClassLoader();
        }
    }

    public String loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }

    private String loadClass(String name, boolean b) {
        TestMain.log("loadClass: " + this.getClass().getSimpleName());
        //检查该类是否已经加载过
        String loadedClass = findLoadedClass(name);
        if (loadedClass == null) {
            if (parent != null) {
                //当父类的加载器不为空，则通过父类的loadClass来加载该类
                loadedClass = parent.loadClass(name, false);
            } else {
                //当父类的加载器为空，则调用启动类加载器来加载该类
                loadedClass = findBootstrapClassOrNull(name);
            }
            if (loadedClass == null) {
                loadedClass = findClass(name); //用户可通过覆写该方法，来自定义类加载器
            }
        }
        return loadedClass;
    }

    public String findClass(String name) {
        if(this instanceof Launcher.AppClassLoader){
            TestMain.log("findClass: 被" + this.getClass().getSimpleName()+"处理了");
            return "冯星.class";
        }
        return null;
    }

    private String findBootstrapClassOrNull(String name) {
        TestMain.log("findBootstrapClassOrNull: Bootstrap无法处理" + name + "这个类，交给子类吧");
        return null;
    }

    private String findLoadedClass(String name) {
        TestMain.log("findLoadedClass: 没有加载过" + name + "这个类,使用类加载器去加载");
        return null;
    }

}
