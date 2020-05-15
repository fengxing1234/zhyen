package com.zhyen.base.design_mode.flyweight_mode;

public class ConcreteFlyweightA implements IFlyweight {
    private String key;

    public ConcreteFlyweightA(String key) {
        this.key = key;
        System.out.println("具体享元 A " + key + "被创建！");
    }

    @Override
    public void operation(UnsharedConcreteFlyweight flyweight) {
        System.out.print("具体享元" + key + "被调用，");
        System.out.println("非享元信息是:" + flyweight.getInfo());
    }
}
