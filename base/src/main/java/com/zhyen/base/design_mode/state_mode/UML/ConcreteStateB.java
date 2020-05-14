package com.zhyen.base.design_mode.state_mode.UML;

public class ConcreteStateB extends AbstractState {
    @Override
    public void handle(StateContext context) {
        System.out.println("当前状态是 B.");
        context.setState(new ConcreteStateA());
    }
}
