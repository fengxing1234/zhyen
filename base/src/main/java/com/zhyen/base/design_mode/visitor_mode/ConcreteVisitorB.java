package com.zhyen.base.design_mode.visitor_mode;

/**
 * 具体访问者B
 */
public class ConcreteVisitorB implements IVisitor {
    @Override
    public void visitor(ConcreteElementA concreteElementA) {
        String operation = concreteElementA.operation();
        System.out.println("具体访问者 B" + operation);
    }

    @Override
    public void visitor(ConcreteElementB concreteElementB) {
        String operation = concreteElementB.operation();
        System.out.println("具体访问者 B" + operation);
    }
}
