package com.zhyen.base.design_mode.visitor_mode;

/**
 * 抽象访问者
 */
public interface IVisitor {
    void visitor(ConcreteElementA concreteElementA);

    void visitor(ConcreteElementB concreteElementB);
}
