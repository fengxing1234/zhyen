package com.zhyen.base.design_mode.decorator_mode;

public class ConcreteComponent implements IComponent {

    public ConcreteComponent() {
        System.out.println("创建具体构件角色");
    }

    @Override
    public void operation() {
        System.out.println("调用具体构件角色的方法operation()");
    }
}
