package com.zhyen.base.design_mode.flyweight_mode;

import java.util.HashMap;

/**
 * 享元工厂（Flyweight Factory）角色：
 * 负责创建和管理享元角色。
 * 当客户对象请求一个享元对象时，享元工厂检査系统中是否存在符合要求的享元对象，
 * 如果存在则提供给客户；如果不存在的话，则创建一个新的享元对象。
 */
public class FlyweightFactory {
    private HashMap<String, IFlyweight> flyweights = new HashMap<String, IFlyweight>();

    public IFlyweight getFlyweight(String key) {
        IFlyweight flyweight = (IFlyweight) flyweights.get(key);
        if (flyweight != null) {
            System.out.println("具体享元" + key + "已经存在，被成功获取！");
        } else {
            flyweight = new ConcreteFlyweightA(key);
            flyweights.put(key, flyweight);
        }
        return flyweight;
    }
}
