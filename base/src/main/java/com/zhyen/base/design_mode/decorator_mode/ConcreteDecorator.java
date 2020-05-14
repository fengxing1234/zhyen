package com.zhyen.base.design_mode.decorator_mode;

public class ConcreteDecorator extends Decorator {

    public ConcreteDecorator(IComponent component) {
        super(component);
    }

    public void operation() {
        super.operation();
        addedFunction();
    }

    public void addedFunction() {
        System.out.println("为具体构件角色增加额外的功能addedFunction()");
    }
}