package com.zhyen.test.class_loader;

public class Launcher {

    private static Launcher launcher = new Launcher();
    private ClassLoader loader;

    public Launcher() {
        ExtClassLoader extClassLoader = new ExtClassLoader();
        loader = new AppClassLoader(extClassLoader);
    }

    public static Launcher getLauncher() {
        return launcher;
    }

    public static class ExtClassLoader extends ClassLoader {

        public ExtClassLoader() {
            super(null);
        }
    }

    public static class AppClassLoader extends ClassLoader {
        public AppClassLoader(ClassLoader parent) {
            super(parent);
        }
    }

    public ClassLoader getClassLoader() {
        return this.loader;
    }

}
