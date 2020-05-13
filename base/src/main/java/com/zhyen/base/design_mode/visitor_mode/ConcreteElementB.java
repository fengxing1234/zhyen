package com.zhyen.base.design_mode.visitor_mode;

/**
 * 具体元素B
 */
public class ConcreteElementB implements IElement {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visitor(this);
    }

    public String operation() {
        return " 访问元素 B";
    }
}
