package com.zhyen.base.design_mode.decorator_mode;

public class Decorator implements IComponent {
    private IComponent component;

    public Decorator(IComponent component) {
        this.component = component;
    }

    @Override
    public void operation() {
        component.operation();
    }
}
