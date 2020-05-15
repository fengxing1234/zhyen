package com.zhyen.base.design_mode.flyweight_mode;

public class ConcreteFlyweightB implements IFlyweight {
    private String key;

    public ConcreteFlyweightB(String key) {
        this.key = key;
        System.out.println("具体享元 B " + key + "被创建！");

    }

    @Override
    public void operation(UnsharedConcreteFlyweight flyweight) {

        System.out.print("具体享元" + key + "被调用，");
        System.out.println("非享元信息是:" + flyweight.getInfo());
    }
}
