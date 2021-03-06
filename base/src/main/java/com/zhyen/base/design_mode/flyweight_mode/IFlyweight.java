package com.zhyen.base.design_mode.flyweight_mode;

/**
 * 抽象享元角色（Flyweight）:
 * 是所有的具体享元类的基类，为具体享元规范需要实现的公共接口，非享元的外部状态以参数的形式通过方法传入
 */
public interface IFlyweight {

    void operation(UnsharedConcreteFlyweight flyweight);
}
