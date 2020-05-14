package com.zhyen.base.design_mode.state_mode.UML;

public class ConcreteStateA extends AbstractState {
    @Override
    public void handle(StateContext context) {
        System.out.println("当前状态是 A.");
        context.setState(new ConcreteStateB());
    }
}
