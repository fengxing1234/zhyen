package com.zhyen.base.design_mode.prototype_mode;

import java.util.ArrayList;

public class ConcretePrototype implements Cloneable {
    public String name;
    public int age;
    private ArrayList list = new ArrayList();

    public ConcretePrototype() {
        System.out.println("构造方法");
    }

    @Override
    public ConcretePrototype clone() throws CloneNotSupportedException {
        System.out.println("开始克隆");
        ConcretePrototype clone = (ConcretePrototype) super.clone();
        clone.list = (ArrayList) list.clone();
        System.out.println("克隆完毕 = " + clone);
        return clone;
    }
}
