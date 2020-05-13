package com.zhyen.base.design_mode.visitor_mode;

/**
 * 具体元素类A
 */
public class ConcreteElementA implements IElement {

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitor(this);
    }

    public String operation() {
        return " 访问元素 A";
    }
}
